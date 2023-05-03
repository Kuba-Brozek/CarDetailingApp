package kamil.degree.cardetailingapp.detailing.two

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kamil.degree.cardetailingapp.databinding.FragmentTwoBinding
import kamil.degree.cardetailingapp.detailing.one.TwoViewModel

class TwoFragment : Fragment() {


    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TwoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[TwoViewModel::class.java]


        return view
    }


}