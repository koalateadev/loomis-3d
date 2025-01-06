package koala.tea.loomis.ui

import android.app.Application
import android.graphics.Color.*
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import dagger.hilt.android.lifecycle.HiltViewModel
import koala.tea.loomis.Model
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application),
    Listener {

    val state = ViewState()

    init {
        setModel(state.shape.value!!, state.color.value!!)
    }

    fun toggleLock() {
        state._locked.postValue(state.locked.value?.not())
    }

    fun disableControls() {
        state._controlVisibility.postValue(false)
    }

    fun enableControls() {
        state._controlVisibility.postValue(true)
    }

    fun controlCamera() {
        state._controller.postValue(Controller.CAMERA)
    }

    fun controlShapeSelection() {
        state._controller.postValue(Controller.MODEL_SELECTION)
    }

    fun controlShapeTransformation() {
        state._controller.postValue(Controller.MODEL_TRANSFORMATION)
    }

    fun controlShapeScale() {
        state._controller.postValue(Controller.MODEL_SCALE)
    }

    fun controlShapeRotation() {
        state._controller.postValue(Controller.MODEL_ROTATION)
    }

    fun setShape(shape: Model) {
        state._shape.postValue(shape)
        setModel(shape, state.color.value!!)
    }

    fun setModelTransparency(alpha: Float) {
        val prevColor = state.color.value!!
        prevColor.a = alpha

        state._color.postValue(prevColor)
        setModel(state.shape.value!!, prevColor)
    }

    fun moveUp() {
        val prev = state._position.value
        prev?.y = prev?.y?.plus(.1f)
        state._position.value = prev
    }

    fun moveDown() {
        val prev = state._position.value
        prev?.y = prev?.y?.minus(.05f)
        state._position.value = prev
    }

    fun moveLeft() {
        val prev = state._position.value
        prev?.x = prev?.x?.minus(.1f)
        state._position.value = prev
    }

    fun moveRight() {
        val prev = state._position.value
        prev?.x = prev?.x?.plus(.05f)
        state._position.value = prev
    }

    fun moveForward() {
        val prev = state._position.value
        prev?.z = prev?.z?.plus(.1f)
        state._position.value = prev
    }

    fun moveBackward() {
        val prev = state._position.value
        prev?.z = prev?.z?.minus(.05f)
        state._position.value = prev
    }

    private fun setModel(shape: Model, color: Color) {
        MaterialFactory.makeTransparentWithColor(
            getApplication(),
            color
        )
            .thenAccept { material ->
                ModelRenderable
                    .builder()
                    .setIsFilamentGltf(true)
                    .setSource(getApplication(), shape.resource)
                    .build()
                    .thenAccept {
                        Log.d("Loomis", it.submeshCount.toString())
                        state._model.postValue(it)
                    }
                    .exceptionally {
                        Log.e("Loomis", "Failed to load model", it)

                        null
                    }
            }
    }

    inner class ViewState {
        internal val _controller = MutableLiveData(Controller.MODEL_SELECTION)
        val controller: LiveData<Controller> = _controller
        internal val _controlVisibility = MutableLiveData(true)
        val controlVisibility: LiveData<Boolean> = _controlVisibility

        internal val _locked = MutableLiveData(false)
        val locked: LiveData<Boolean> = _locked

        internal val _model: MutableLiveData<ModelRenderable> = MutableLiveData()
        val model: LiveData<ModelRenderable> = _model
        internal val _shape = MutableLiveData(Model.FIRST)
        val shape: LiveData<Model> = _shape
        internal val _color = MutableLiveData(Color(WHITE))
        val color: LiveData<Color> = _color
        internal val _position: MutableLiveData<Vector3> = MutableLiveData(Vector3(0f, 0f, -5f))
        val position: LiveData<Vector3> = _position
        internal val _rotation: MutableLiveData<Quaternion> = MutableLiveData()
        val rotation: LiveData<Quaternion> = _rotation
        internal val _scale: MutableLiveData<Vector3> = MutableLiveData()
        val scale: LiveData<Vector3> = _scale
    }

    override fun onRotationChanged(rotation: Quaternion) {
        state._rotation.postValue(rotation)
    }
}

interface Listener {

    fun onRotationChanged(rotation: Quaternion)
}

enum class Controller {
    NONE,
    MODEL_SELECTION,
    MODEL_TRANSFORMATION,
    MODEL_SCALE,
    MODEL_ROTATION,
    CAMERA,
}