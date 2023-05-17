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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kamil.degree.cardetailingapp.databinding.FragmentBusinessSearchBinding
import kamil.degree.cardetailingapp.detailing.adapters.BusinessSearchAdapter
import kamil.degree.cardetailingapp.extentions.Extentions.shortToast
import kamil.degree.cardetailingapp.model.Business
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


        binding.businessSearchContainer.layoutManager = LinearLayoutManager(requireContext())
        adapter = BusinessSearchAdapter(businessList)
        binding.businessSearchContainer.adapter = adapter
        adapter.setOnItemClickListener(object : BusinessSearchAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                shortToast("asdasdasd ${businessList[position]}")
            }
        })

        viewModel.getBusinesses2 {
            lifecycleScope.launch {
                businessList = it
                adapter.businessList = it
                adapter.notifyDataSetChanged()
            }
        }

        binding.searchWithFilter1CV.setOnClickListener {
            filterButtonOnClick(0, binding.one)
        }

        binding.searchWithFilter2CV.setOnClickListener {
            filterButtonOnClick(1, binding.two)
        }

        binding.searchWithFilter3CV.setOnClickListener {
            filterButtonOnClick(2, binding.three)
        }

        binding.searchWithFilter4CV.setOnClickListener {
            filterButtonOnClick(3, binding.four)
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

    private fun filterButtonOnClick(predicate: Int, consLayout: ConstraintLayout){
        if(clickedFlagList[predicate]){
            consLayout.background.alpha = 255
            clickedFlagList = clickedFlagList.map { false }.toMutableList()
            restoreUnfilteredAdapter()
        } else {
            consLayout.background.alpha = 100
            clickedFlagList = clickedFlagList.map { false }.toMutableList()
            clickedFlagList[predicate] = true
            filterBusinessListAndUpdateAdapterAccordingly(predicateList[predicate])
        }
    }

}
