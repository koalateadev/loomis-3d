package koala.tea.loomis.ui.control

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import koala.tea.loomis.ui.MainViewModel
import koala.tea.loomis.databinding.FragmentModelScaleBinding

class ModelScaleFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var binding: FragmentModelScaleBinding? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModelScaleBinding.inflate(inflater)

        return binding?.root
    }
}