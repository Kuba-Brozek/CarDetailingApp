package kamil.degree.cardetailingapp.detailing.managebusiness.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kamil.degree.cardetailingapp.databinding.FragmentBusinessIntroductionBinding
import kamil.degree.cardetailingapp.detailing.managebusiness.BusinessViewModel


class BusinessIntroductionFragment : Fragment() {
    private var _binding: FragmentBusinessIntroductionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BusinessViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessIntroductionBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[BusinessViewModel::class.java]

        return view
    }

}