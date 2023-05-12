package kamil.degree.cardetailingapp.detailing.searchbusiness

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

class BusinessSearchFragment : Fragment() {


    private var _binding: FragmentBusinessSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BusinessSearchViewModel
    private var businessList = listOf<Pair<Business, String>>()
    private lateinit var adapter: BusinessSearchAdapter

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
                adapter = BusinessSearchAdapter(it)
                binding.businessSearchContainer.adapter = adapter
                adapter.setOnItemClickListener(object : BusinessSearchAdapter.OnItemClickListener{
                    override fun onItemClick(position: Int) {
                        shortToast("asdasdasd ${businessList[position]}")
                    }
                })
            }
        }


        binding.searchET.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {

            }
        })

        return view
    }


}