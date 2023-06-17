package kamil.degree.cardetailingapp.detailing.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kamil.degree.cardetailingapp.databinding.FragmentSettingsBinding
import kamil.degree.cardetailingapp.extentions.Extentions.longToast
import kamil.degree.cardetailingapp.extentions.Extentions.shortToast
import kamil.degree.cardetailingapp.extentions.Extentions.useText


class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        initListeners()

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.changeEmailBTN.setOnClickListener {
            viewModel.getUserData {
                if (it.email == binding.currentInputEmailET.useText()) {
                    val newEmail = binding.newInputEmailET.useText()
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
                        viewModel.changeEmail(newEmail)
                    } else {
                        longToast(""" Email needs to have proper format "example@example.com" """ )
                    }
                } else {
                    longToast("Current Email is not the one You have linked to account.")
                }
            }
        }
        binding.changePasswordBTN.setOnClickListener {
            longToast("${binding.newInputPasswordET.useText()}, ${binding.newInputRepeatPasswordET.useText()}")
            if (binding.newInputPasswordET.useText() == binding.newInputRepeatPasswordET.useText()) {
                if (binding.newInputPasswordET.useText().length > 7){
                    viewModel.changePassword(binding.newInputRepeatPasswordET.useText())
                } else {
                    longToast("Passwords needs to be 8 characters or more")
                }
            } else {
                longToast("Passwords are not the same.")
            }

        }
        binding.changePhoneNumberBTN.setOnClickListener {
            if (binding.phoneNumberET.useText().isEmpty()) {
                shortToast("Please provide proper number")
            } else {
                viewModel.setPhoneNumber(binding.phoneNumberET.useText())
            }
        }
        binding.deleteAccountBTN.setOnClickListener {
            viewModel.deleteAccount {
                if(it) viewModel.logOut()
                else shortToast("Account not deleted. Try again later.")
            }
        }
        binding.logOutBTN.setOnClickListener {
            viewModel.logOut()
        }
        binding.deleteBusinessBTN.setOnClickListener {
            viewModel.deleteBusiness()
        }
    }

}