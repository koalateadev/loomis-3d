package koala.tea.loomis.ui.display

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.Light
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer
import com.google.ar.sceneform.ux.TransformationSystem
import koala.tea.loomis.ui.MainViewModel
import koala.tea.loomis.DragTransformableNode
import koala.tea.loomis.databinding.FragmentModelBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ModelFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var binding: FragmentModelBinding? = null
    private var modelNode: Node? = null
    private lateinit var ts: TransformationSystem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModelBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ts = TransformationSystem(
            resources.displayMetrics,
            FootprintSelectionVisualizer()
        )

        binding?.scene?.let {
            loadScene(it)
        }
        viewModel.state.model.observe(viewLifecycleOwner) {
            modelNode?.setRenderable(it)
        }
    }

    override fun onResume() {
        super.onResume()

        binding?.scene?.resume()
    }

    override fun onPause() {
        super.onPause()

        binding?.scene?.pause()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun loadScene(scene: SceneView) {
        scene.setTransparent(true)
        scene.renderer?.setClearColor(Color(0f, 1f, 1f, 0f))
        scene.setZOrderMediaOverlay(true)

//        scene.scene.renderer?.environment?.indirectLight?.let {
//            it.intensity = 1000f
//            scene.scene.renderer?.setIndirectLight(it)
//        }

//        scene.scene.view.alpha = .5f

//        scene.renderer?.setIndirectLight(null)
//        scene.renderer?.setMainLight(null)
//        val light = Light.builder(Light.Type.DIRECTIONAL)
//            .setColor(Color(android.graphics.Color.WHITE))
//            .setFalloffRadius(5f)
//            .setShadowCastingEnabled(false)
//            .setIntensity(30_000f)
//            .build()
//
//        val lightNode = Node()
//        lightNode.parent = scene.scene
//        lightNode.localPosition = Vector3(0f, 0f, 0f)
//        lightNode.light = light
//
//        val lightNode2 = Node()
//        lightNode2.parent = scene.scene
//        lightNode2.localPosition = Vector3(0f, 2f, -1f)
//        lightNode2.light = light

        // isVisible == false + short press -> show controls
        // isVisible == true + short press -> hide controls

        // isVisible == false + long press -> hide controls + don't reshow
        // isVisible == true + long press -> hide controls + reshow

        var wasVisible = true
        var longPressed = false
        var timer: Job? = null
        scene.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                wasVisible = viewModel.state.controlVisibility.value == true
                longPressed = false
                viewModel.disableControls()

                timer = lifecycleScope.launch {
                    delay(150)
                    longPressed = true
                }
            } else if (event.action == MotionEvent.ACTION_UP) {
                timer?.cancel()

                if (wasVisible && longPressed) {
                    viewModel.enableControls()
                } else if (!wasVisible && !longPressed) {
                    viewModel.enableControls()
                }
            }

            false
        }

        val transformNode = DragTransformableNode(ts, viewModel)
        ts.selectNode(transformNode)
        transformNode.parent = scene.scene
        transformNode.worldPosition = Vector3(0f, 0f, -30f)
        transformNode.select()
        viewModel.state.locked.observe(viewLifecycleOwner) {
            val listener =
                Scene.OnPeekTouchListener { hitTestResult, motionEvent ->
                    try {
                        ts.onTouch(hitTestResult, motionEvent)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            binding?.scene?.scene?.addOnPeekTouchListener(listener)
            transformNode.setOnTapListener { hitTestResult, motionEvent -> }
            transformNode.setOnTouchListener { hitTestResult, motionEvent -> true }
            if (it) {
                ts.selectNode(null)
            } else {
                ts.selectNode(transformNode)
            }
        }
        val listener =
            Scene.OnPeekTouchListener { hitTestResult, motionEvent ->
                try {
                    ts.onTouch(hitTestResult, motionEvent)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        scene.scene.addOnPeekTouchListener(listener)

        modelNode = Node()
        modelNode?.parent = transformNode

        viewModel.state.position.observe(viewLifecycleOwner) {
            transformNode.localPosition = it
        }
        viewModel.state.rotation.observe(viewLifecycleOwner) {
            transformNode.localRotation = it
        }
        viewModel.state.scale.observe(viewLifecycleOwner) {
            transformNode.localScale = it
        }
    }
}