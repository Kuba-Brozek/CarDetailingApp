package kamil.degree.cardetailingapp.detailing.managebusiness

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.FragmentBusinessBucketBinding
import kamil.degree.cardetailingapp.detailing.managebusiness.details.AddBusinessFragment
import kamil.degree.cardetailingapp.detailing.managebusiness.details.BusinessIntroductionFragment
import kamil.degree.cardetailingapp.detailing.searchbusiness.BusinessSearchFragment
import kamil.degree.cardetailingapp.model.User

class BusinessBucketFragment : Fragment() {


    private var _binding: FragmentBusinessBucketBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BusinessViewModel
    private val addBusinessFragment = AddBusinessFragment()
    private val businessIntroductionFragment = BusinessIntroductionFragment()
    private val twoFragment = BusinessSearchFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessBucketBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[BusinessViewModel::class.java]
        var user = User("a", "a", "a", "false", false)

        viewModel.getUserData {
            user = it
            if (user.hasBusiness == true) {
                loadFragment(addBusinessFragment)
                binding.addBusinessFAB.visibility = View.GONE
            } else {
                loadFragment(businessIntroductionFragment)
            }
        }

        binding.addBusinessFAB.setOnClickListener {
            if (!user.hasBusiness!!) {
                binding.addBusinessFAB.visibility = View.GONE
                viewModel.changeBusinessFlag{
                    loadFragment(addBusinessFragment)
                }

            }
        }

        binding.searchBusinessFAB.setOnClickListener {
            loadFragment(twoFragment)
        }


        return view
    }


    fun loadFragment(fragment: Fragment){
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.business_container,fragment)
        transaction.commit()
    }

}