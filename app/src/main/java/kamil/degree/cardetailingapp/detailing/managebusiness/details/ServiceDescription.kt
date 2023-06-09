package kamil.degree.cardetailingapp.detailing.managebusiness.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kamil.degree.cardetailingapp.databinding.FragmentServiceDescriptionBinding
import kamil.degree.cardetailingapp.detailing.DrawerActivity
import kamil.degree.cardetailingapp.detailing.managebusiness.BusinessBucketFragment
import kamil.degree.cardetailingapp.detailing.managebusiness.BusinessViewModel
import kamil.degree.cardetailingapp.extentions.Extentions.useText
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.model.Service


class ServiceDescription(private val service: Service, private val index: Int) : Fragment() {


    private var _binding: FragmentServiceDescriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BusinessViewModel
    private var business = Business()
    private val businessBucketFragment = BusinessBucketFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[BusinessViewModel::class.java]

        binding.serviceNameServiceDescriptionET.setText(service.name)
        binding.serviceDescriptionServiceDescriptionET.setText(service.description)
        binding.servicePriceServiceDescriptionET.setText( if (service.price == null) "" else service.price.toString() )

        binding.backIB.setOnClickListener {
            (activity as DrawerActivity).loadFragment(businessBucketFragment)
        }

        binding.saveBusinessChangesServiceDescriptionFAB.setOnClickListener {
            viewModel.getBusinessData {
                business = it
                business.services[index].name = binding.serviceNameServiceDescriptionET.useText()
                business.services[index].description = binding.serviceDescriptionServiceDescriptionET.useText()
                business.services[index].price = binding.servicePriceServiceDescriptionET.useText().toInt()
                viewModel.saveService(business)
            }
        }

        return view
    }
}