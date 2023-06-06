package kamil.degree.cardetailingapp.detailing.managebusiness.details

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kamil.degree.cardetailingapp.databinding.FragmentBusinessDetailsBinding
import kamil.degree.cardetailingapp.detailing.adapters.BusinessDetailsImagesAdapter
import kamil.degree.cardetailingapp.detailing.adapters.BusinessServiceOverviewAdapter
import kamil.degree.cardetailingapp.detailing.adapters.ServicesOverviewAdapter
import kamil.degree.cardetailingapp.detailing.managebusiness.BusinessViewModel
import kamil.degree.cardetailingapp.model.Business
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BusinessDetailsFragment(private val uid: String, business: Business) : Fragment() {


    private lateinit var viewModel: BusinessViewModel
    private var _binding: FragmentBusinessDetailsBinding? = null
    private val binding get() = _binding!!
    private var businessDetails = business
    private val imageUrls = mutableListOf<String>()
    private val imageAdapter = BusinessDetailsImagesAdapter(imageUrls)
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val imageRef = Firebase.storage.reference
    val serviceAdapter = BusinessServiceOverviewAdapter(business.services)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[BusinessViewModel::class.java]
        _binding = FragmentBusinessDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.servicesAdapter.layoutManager = LinearLayoutManager(requireContext())
        binding.servicesAdapter.adapter = serviceAdapter
        listFiles()
        binding.businessNameTV.text = businessDetails.name
        binding.businessAddressTV.text = businessDetails.address
        binding.businessDescriptionTV.text = businessDetails.description

        return view
    }


    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            binding.businessImagesAdapter.adapter = imageAdapter
            binding.businessImagesAdapter.layoutManager = GridLayoutManager(requireContext(), 2)
            imageAdapter.setOnItemClickListener(object: BusinessDetailsImagesAdapter.OnImage2ClickListener{
                override fun onItemClick(position: Int) {}
                override fun onItemLongClick(position: Int) {}
            })
        }
        try {
            val images = imageRef.child("${uid}/").listAll().await()
            Log.d("asd", images.toString())
            for(image in images.items) {
                Log.d("asd", image.toString())
                CoroutineScope(Dispatchers.IO).launch {
                    val url = image.downloadUrl.await()
                    withContext(Dispatchers.Main) {
                        imageUrls.add(url.toString())
                        imageAdapter.urls = imageUrls
                        imageAdapter.notifyItemInserted(imageAdapter.urls.lastIndex)
                    }
                }
            }

        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Log.w("", e.message?: "")
            }
        }
    }

}