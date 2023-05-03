package kamil.degree.cardetailingapp.detailing.one

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kamil.degree.cardetailingapp.databinding.FragmentOneBinding

class OneFragment : Fragment() {


    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TwoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[TwoViewModel::class.java]


        return view
    }


}