package kamil.degree.cardetailingapp.detailing.one.childs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kamil.degree.cardetailingapp.databinding.FragmentAddBusinessBinding
import kamil.degree.cardetailingapp.detailing.one.OneViewModel
import kamil.degree.cardetailingapp.detailing.rvadapters.ServicesOverviewAdapter
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
            viewModel.getBusinessData {
                val business = Business(binding.addBusinessNameET.useText(),
                    it.services,
                    binding.addBusinessDescriptionET.useText())
                viewModel.modifyBusiness(business)
            }
        }
        updateAdapter()

        return view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateAdapter() {
        viewModel.getBusinessData {
            binding.addBusinessNameET.setText(it.name)
            binding.addBusinessDescriptionET.setText(it.description)
            Log.d("TAAAAAAAAAAAG", it.services.toString())
            binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
            val adapter = ServicesOverviewAdapter(it.services)
            binding.recyclerview.adapter = adapter
        }
    }
}