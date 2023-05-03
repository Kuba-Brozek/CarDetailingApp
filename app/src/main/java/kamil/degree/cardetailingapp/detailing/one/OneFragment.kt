package kamil.degree.cardetailingapp.detailing.one

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.FragmentOneBinding
import kamil.degree.cardetailingapp.detailing.one.childs.AddBusinessFragment
import kamil.degree.cardetailingapp.detailing.one.childs.BusinessIntroductionFragment
import kamil.degree.cardetailingapp.model.User
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class OneFragment : Fragment() {


    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OneViewModel
    private val addBusinessFragment = AddBusinessFragment()
    private val businessIntroductionFragment = BusinessIntroductionFragment()
    private val firebaseRepository = FirebaseRepository()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[OneViewModel::class.java]
        var user = User("a", "a", "a", "false", false)

        loadFragment(businessIntroductionFragment)

        firebaseRepository.getUserData {
            user = it
            if (user.hasBusiness == true) {
                loadFragment(addBusinessFragment)
                binding.addBusinessFAB.visibility = View.GONE
            }
        }


        binding.addBusinessFAB.setOnClickListener {
            if (!user.hasBusiness!!) {
                binding.addBusinessFAB.visibility = View.GONE
                viewModel.changeBusinessFlag()
                loadFragment(addBusinessFragment)
            }
        }


        return view
    }


    private fun loadFragment(fragment: Fragment){
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.business_container,fragment)
        transaction.commit()
    }

}