package koala.tea.loomis

import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.ux.*
import koala.tea.loomis.ui.Listener

class DragTransformableNode(transformationSystem: TransformationSystem, listener: Listener) :
    TransformableNode(transformationSystem) {

    init {
        translationController.isEnabled = false
        rotationController.isEnabled = false
        scaleController.isEnabled = false
        removeTransformationController(translationController)
        removeTransformationController(rotationController)
        removeTransformationController(scaleController)

        addTransformationController(RotationController(
            listener,
            this,
            transformationSystem.dragRecognizer
        ))
        addTransformationController(TwistController(
            listener,
            this,
            transformationSystem.twistRecognizer
        ))
        addTransformationController(ScaleController(
            this,
            transformationSystem.pinchRecognizer
        ))
    }
}

class ZoomController(
    private val listener: Listener,
    transformableNode: BaseTransformableNode,
    pinchRecognizer: PinchGestureRecognizer
) :
    BaseTransformationController<PinchGesture>(transformableNode, pinchRecognizer) {

    // Rate that the node rotates in degrees per degree of twisting.
    private var rotationRateDegrees = 2f

    public override fun canStartTransformation(gesture: PinchGesture): Boolean {
        return transformableNode.isSelected
    }

    public override fun onContinueTransformation(gesture: PinchGesture) {
        transformableNode.localScale = transformableNode.localScale.scaled(1 + gesture.gapDelta)
    }

    public override fun onEndTransformation(gesture: PinchGesture) {}
}

class TwistController(
    private val listener: Listener,
    transformableNode: BaseTransformableNode,
    twistRecognizer: TwistGestureRecognizer
) :
    BaseTransformationController<TwistGesture>(transformableNode, twistRecognizer) {

    // Rate that the node rotates in degrees per degree of twisting.
    private var rotationRateDegrees = 2f

    public override fun canStartTransformation(gesture: TwistGesture): Boolean {
        return transformableNode.isSelected
    }

    public override fun onContinueTransformation(gesture: TwistGesture) {
        val rotationAmount = gesture.deltaRotationDegrees * rotationRateDegrees
        val forward = transformableNode.worldToLocalDirection(Vector3.forward())
        val rotationDelta = Quaternion.axisAngle(forward, rotationAmount)
        var localrotation = transformableNode.localRotation
        localrotation = Quaternion.multiply(localrotation, rotationDelta)
        transformableNode.localRotation = localrotation
    }

    public override fun onEndTransformation(gesture: TwistGesture) {}
}

class RotationController(
    private val listener: Listener,
    transformableNode: BaseTransformableNode,
    rotationRecognizer: DragGestureRecognizer
) :
    BaseTransformationController<DragGesture>(transformableNode, rotationRecognizer) {

    // Rate that the node rotates in degrees per degree of twisting.
    private var rotationRateDegrees = 0.5f

    public override fun canStartTransformation(gesture: DragGesture): Boolean {
        return transformableNode.isSelected
    }

    public override fun onContinueTransformation(gesture: DragGesture) {
        var localRotation = transformableNode.localRotation

        val rotationAmountX = gesture.delta.x * rotationRateDegrees
        val up = transformableNode.worldToLocalDirection(Vector3.up())
        val rotationDeltaX = Quaternion.axisAngle(up, rotationAmountX)

        val rotationAmountY = gesture.delta.y * rotationRateDegrees
        val right = transformableNode.worldToLocalDirection(Vector3.right())
        val rotationDeltaY = Quaternion.axisAngle(right, rotationAmountY)

        val change = Quaternion.multiply(rotationDeltaX, rotationDeltaY)
        localRotation = Quaternion.multiply(localRotation, change)

        listener.onRotationChanged(localRotation)
    }

    public override fun onEndTransformation(gesture: DragGesture) {}
}