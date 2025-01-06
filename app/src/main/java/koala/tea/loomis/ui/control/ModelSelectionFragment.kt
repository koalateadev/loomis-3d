package koala.tea.loomis.ui.control

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import koala.tea.loomis.Model
import koala.tea.loomis.ui.MainViewModel
import koala.tea.loomis.databinding.FragmentModelSelectionBinding

class ModelSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var binding: FragmentModelSelectionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModelSelectionBinding.inflate(inflater)

        binding?.carousel?.setAdapter(object : Carousel.Adapter {

            override fun count() = Model.values().size

            override fun populate(view: View, index: Int) {
                Log.d("Index", index.toString())
//                (view as ImageView).setImageResource(Model.values()[index].drawableId)
            }

            override fun onNewItem(index: Int) {
                Log.d("New Index", index.toString())
                viewModel.setShape(Model.values()[(index + 2) % Model.values().size])
            }
        })

        viewModel.state.controlVisibility.observe(viewLifecycleOwner) {
            if (it) {
                binding!!.bottomSheetContainer.visibility = View.VISIBLE
            } else {
                binding!!.bottomSheetContainer.visibility = View.GONE
            }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentIndex = Model.values().indexOf(viewModel.state.shape.value)
        if (currentIndex != -1) {
            val shapeIndex = (currentIndex + (Model.values().size - 2)) % Model.values().size
//            binding?.carousel?.jumpToIndex(shapeIndex) // FIXME
        }
    }
}