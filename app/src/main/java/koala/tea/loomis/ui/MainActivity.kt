package koala.tea.loomis.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commitNow
import koala.tea.loomis.ui.display.CameraFragment
import koala.tea.loomis.ui.display.ModelFragment
import koala.tea.loomis.R
import koala.tea.loomis.databinding.ActivityMainBinding
import koala.tea.loomis.ui.control.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val controllerFragmentTag = "CONTROLLER_FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                add(R.id.fragmentContainer, CameraFragment())
            }

            supportFragmentManager.commitNow {
                add(R.id.fragmentContainer, ModelFragment())
            }

            supportFragmentManager.commitNow {
                add(R.id.fragmentContainer, OptionsFragment())
            }
        }

        viewModel.state.controller.observe(this) {
            supportFragmentManager.findFragmentByTag(controllerFragmentTag)?.let {
                supportFragmentManager.commitNow {
                    remove(it)
                }
            }

            when (it) {
                Controller.CAMERA -> supportFragmentManager.commitNow {
                    add(
                        R.id.fragmentContainer,
                        CameraControllerFragment(),
                        controllerFragmentTag
                    )
                }
                Controller.MODEL_SELECTION -> supportFragmentManager.commitNow {
                    add(R.id.fragmentContainer, ModelSelectionFragment(), controllerFragmentTag)
                }
                Controller.MODEL_SCALE -> supportFragmentManager.commitNow {
                    add(R.id.fragmentContainer, ModelScaleFragment(), controllerFragmentTag)
                }
                Controller.MODEL_ROTATION -> supportFragmentManager.commitNow {
                    add(R.id.fragmentContainer, ModelRotationFragment(), controllerFragmentTag)
                }
                Controller.MODEL_TRANSFORMATION -> supportFragmentManager.commitNow {
                    add(R.id.fragmentContainer, ModelPositionFragment(), controllerFragmentTag)
                }
                else -> {}
            }
        }

        setContentView(binding.root)
    }
}