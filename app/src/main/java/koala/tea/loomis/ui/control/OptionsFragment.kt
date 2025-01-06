package koala.tea.loomis.ui.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import koala.tea.loomis.ui.MainViewModel
import koala.tea.loomis.*
import koala.tea.loomis.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOptionsBinding.inflate(inflater)

        binding.shapeSelectionButton.setOnClickListener {
            viewModel.controlShapeSelection()
        }

        binding.shapeTransformationButton.setOnClickListener {
            viewModel.controlShapeTransformation()
        }

        binding.shapeRotateButton.setOnClickListener {
            viewModel.controlShapeRotation()
        }

        binding.shapeResizeButton.setOnClickListener {
            viewModel.controlShapeScale()
        }

        binding.cameraButton.setOnClickListener {
            viewModel.controlCamera()
        }

        binding.lockButton.setOnClickListener {
            viewModel.toggleLock()
        }

        binding.floatingActionButton.setOnClickListener {
            if (binding.container.visibility == View.VISIBLE) {
                it.rotate()
                binding.container.collapse()
            } else {
                it.rotateDown()
                binding.container.expand()
            }
        }

        viewModel.state.locked.observe(viewLifecycleOwner) {
            if (it) {
                binding.lockButton.setImageResource(R.drawable.lock)
            } else {
                binding.lockButton.setImageResource(R.drawable.lock_open_outline)
            }
        }

        viewModel.state.controlVisibility.observe(viewLifecycleOwner) {
            if (it) {
                binding.lockButton.visibility = View.VISIBLE
                binding.optionsContainer.visibility = View.VISIBLE
            } else {
                binding.lockButton.visibility = View.GONE
                binding.optionsContainer.visibility = View.GONE
            }
        }

        return binding.root
    }
}