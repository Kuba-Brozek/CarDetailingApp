package kamil.degree.cardetailingapp.detailing.one.childs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kamil.degree.cardetailingapp.databinding.FragmentAddBusinessBinding


class AddBusinessFragment : Fragment() {


    private var _binding: FragmentAddBusinessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBusinessBinding.inflate(inflater, container, false)
        val view = binding.root



        return view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}