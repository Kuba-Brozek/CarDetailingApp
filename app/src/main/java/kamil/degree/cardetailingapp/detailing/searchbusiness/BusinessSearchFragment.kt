package kamil.degree.cardetailingapp.detailing.searchbusiness

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kamil.degree.cardetailingapp.databinding.FragmentBusinessSearchBinding
import kamil.degree.cardetailingapp.detailing.adapters.BusinessSearchAdapter
import kamil.degree.cardetailingapp.extentions.Extentions.shortToast
import kamil.degree.cardetailingapp.model.Business
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("NotifyDataSetChanged")
class BusinessSearchFragment : Fragment() {


    private var _binding: FragmentBusinessSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BusinessSearchViewModel
    private var businessList = listOf<Pair<Business, String>>()
    private lateinit var adapter: BusinessSearchAdapter
    private val predicateList = listOf("myju myju", "Predicate 2", "Predicate 3", "Predicate 4")
    private var clickedFlagList = mutableListOf(false, false, false, false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[BusinessSearchViewModel::class.java]

        viewModel.getBusinesses {
            CoroutineScope(Dispatchers.Main).launch {
                businessList = it
                println(it[0].toString())
                binding.businessSearchContainer.layoutManager = LinearLayoutManager(requireContext())
                adapter = BusinessSearchAdapter(businessList)
                binding.businessSearchContainer.adapter = adapter
                adapter.setOnItemClickListener(object : BusinessSearchAdapter.OnItemClickListener{
                    override fun onItemClick(position: Int) {
                        shortToast("asdasdasd ${businessList[position]}")
                    }
                })
            }
        }

        binding.searchWithFilter1CV.setOnClickListener {
            filterButtonOnClick(0)
        }

        binding.searchWithFilter2CV.setOnClickListener {
            filterButtonOnClick(1)
        }

        binding.searchWithFilter3CV.setOnClickListener {
            filterButtonOnClick(2)
        }

        binding.searchWithFilter4CV.setOnClickListener {
            filterButtonOnClick(3)
        }


        binding.searchET.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()) {
                    filterBusinessListAndUpdateAdapterAccordingly(s.toString())
                    clickedFlagList = clickedFlagList.map { false }.toMutableList()
                } else {
                    restoreUnfilteredAdapter()
                }
            }
        })

        return view
    }


    private fun filterBusinessListAndUpdateAdapterAccordingly(predicate: String) {

        Log.d("asdasdsdadsa", predicate)
        val filteredList = businessList.filter {
                pair -> pair.first.services
            .map { it.name }.any { string -> string?.contains(predicate) ?: false }
        }
        Log.d("", filteredList.size.toString())
        Log.d("", businessList.size.toString())
        adapter.businessList = filteredList
        adapter.notifyDataSetChanged()
    }

    private fun restoreUnfilteredAdapter() {
        adapter.businessList = businessList
        adapter.notifyDataSetChanged()
    }

    private fun filterButtonOnClick(predicate: Int){
        clickedFlagList = clickedFlagList.map { false }.toMutableList()
        if(clickedFlagList[predicate]){
            clickedFlagList[predicate] = false
            restoreUnfilteredAdapter()
        } else {
            clickedFlagList[predicate] = true
            filterBusinessListAndUpdateAdapterAccordingly(predicateList[predicate])
        }
    }

}
