package kamil.degree.cardetailingapp.detailing.one.childs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kamil.degree.cardetailingapp.databinding.FragmentAddBusinessBinding
import kamil.degree.cardetailingapp.detailing.one.OneViewModel
import kamil.degree.cardetailingapp.extentions.Extentions.useText
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.model.Service


class AddBusinessFragment : Fragment() {

    private lateinit var viewModel: OneViewModel
    private var _binding: FragmentAddBusinessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[OneViewModel::class.java]
        _binding = FragmentAddBusinessBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.addBusinessConfirmFAB.setOnClickListener {
            val business = Business(binding.addBusinessNameET.useText(),
                listOf(Service(binding.addBusinessServiceET.useText(), 199)),
                binding.addBusinessDescriptionET.useText())
            viewModel.addBusiness(business)
        }


        return view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}