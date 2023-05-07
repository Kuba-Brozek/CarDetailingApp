package kamil.degree.cardetailingapp.detailing.one.childs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.FragmentAddBusinessBinding
import kamil.degree.cardetailingapp.detailing.DrawerActivity
import kamil.degree.cardetailingapp.detailing.one.OneFragment
import kamil.degree.cardetailingapp.detailing.one.OneViewModel
import kamil.degree.cardetailingapp.detailing.rvadapters.ServicesOverviewAdapter
import kamil.degree.cardetailingapp.extentions.Extentions.useText
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.model.Service


class AddBusinessFragment : Fragment() {

    private lateinit var viewModel: OneViewModel
    private var _binding: FragmentAddBusinessBinding? = null
    private val binding get() = _binding!!
    private var business = Business()
    private lateinit var adapter: ServicesOverviewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[OneViewModel::class.java]
        _binding = FragmentAddBusinessBinding.inflate(inflater, container, false)
        val view = binding.root
        initAdapter()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        binding.saveBusinessChangesFAB.setOnClickListener {
            business.name = binding.addBusinessNameET.useText()
            business.description = binding.addBusinessDescriptionET.useText()
            business.services = adapter.serviceList
            viewModel.modifyBusiness(business)
        }

        binding.addServiceFAB.setOnClickListener {
            adapter.serviceList.add(insertEmptyService())
            business.services = adapter.serviceList
            adapter.notifyItemInserted(adapter.itemCount-1)
            viewModel.modifyBusiness(business)
        }

        return view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        viewModel.getBusinessData {
            binding.addBusinessNameET.setText(it.name)
            binding.addBusinessDescriptionET.setText(it.description)
            Log.d("TAAAAAAAAAAAG", it.services.toString())
            binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
            adapter = ServicesOverviewAdapter(it.services.toMutableList())
            binding.recyclerview.adapter = adapter
            adapter.setOnItemClickListener(object: ServicesOverviewAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) {
                    (activity as DrawerActivity).loadFragment(ServiceDescription(adapter.serviceList[position], position))
                }

                override fun onSettingsClick(position: Int) {
                    (activity as DrawerActivity).loadFragment(ServiceDescription(adapter.serviceList[position], position))
                }

                override fun onDeleteClick(position: Int) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to delete ${adapter.serviceList[position].name}?")
                        .setPositiveButton("YES") { _, _ ->
                            val services = adapter.serviceList.filterIndexed {index, _ -> index != position }.toMutableList()
                            business.services = services
                            adapter.serviceList = services
                            viewModel.modifyBusiness(business)
                            adapter.notifyItemRemoved(position)
                    }.setNegativeButton("NO") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
                }
            })
        }
    }

    private fun insertEmptyService(): Service {
        return Service()
    }

}