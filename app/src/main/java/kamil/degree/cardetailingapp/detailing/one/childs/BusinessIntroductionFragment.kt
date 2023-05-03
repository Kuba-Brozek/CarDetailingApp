package kamil.degree.cardetailingapp.detailing.one.childs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.FragmentAddBusinessBinding
import kamil.degree.cardetailingapp.databinding.FragmentBusinessIntroductionBinding


class BusinessIntroductionFragment : Fragment() {
    private var _binding: FragmentBusinessIntroductionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessIntroductionBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

}