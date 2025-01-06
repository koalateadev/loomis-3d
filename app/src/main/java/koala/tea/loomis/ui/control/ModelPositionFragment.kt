package koala.tea.loomis.ui.control

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
import koala.tea.loomis.ui.MainViewModel
import koala.tea.loomis.databinding.FragmentModelPositionBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ModelPositionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var binding: FragmentModelPositionBinding? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModelPositionBinding.inflate(inflater)

//        binding?.upButton?.setOnClickListener {
//            viewModel.moveUp()
//        }
//        var job: Job? = null
//        binding?.upButton?.setOnTouchListener { v, event ->
//            when (event.action and MotionEvent.ACTION_MASK) {
//                MotionEvent.ACTION_DOWN -> {
//                    v.isPressed = true
//
//                    job = lifecycleScope.launch {
//                        while (isActive) {
//                            Log.d("MaxBuster", "down")
//                            viewModel.moveUp()
//                            delay(500)
//                        }
//                    }
//                }
//                MotionEvent.ACTION_UP, MotionEvent.ACTION_OUTSIDE, MotionEvent.ACTION_CANCEL -> {
//                    v.isPressed = false
//
//                    job?.cancel()
//                    Log.d("MaxBuster", "up")
//                }
//            }
//
//            return@setOnTouchListener true
//        }
//        binding?.downButton?.setOnClickListener {
//            viewModel.moveDown()
//        }
//        binding?.upButton?.setOnClickListener {
//            viewModel.moveUp()
//        }
//        binding?.leftButton?.setOnClickListener {
//            viewModel.moveLeft()
//        }
//        binding?.rightButton?.setOnClickListener {
//            viewModel.moveRight()
//        }
//        binding?.inButton?.setOnClickListener {
//            viewModel.moveForward()
//        }
//        binding?.outButton?.setOnClickListener {
//            viewModel.moveBackward()
//        }

        return binding?.root
    }
}