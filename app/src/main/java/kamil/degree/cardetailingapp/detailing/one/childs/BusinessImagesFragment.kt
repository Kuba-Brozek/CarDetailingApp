package kamil.degree.cardetailingapp.detailing.one.childs

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.FragmentAddBusinessBinding
import kamil.degree.cardetailingapp.databinding.FragmentBusinessImagesBinding
import kamil.degree.cardetailingapp.databinding.FragmentServiceDescriptionBinding
import kamil.degree.cardetailingapp.detailing.one.OneViewModel
import kamil.degree.cardetailingapp.detailing.rvadapters.ImageAdapter
import kamil.degree.cardetailingapp.extentions.Extentions.longToast
import kamil.degree.cardetailingapp.extentions.Extentions.shortToast
import kamil.degree.cardetailingapp.extentions.Extentions.useText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val REQUEST_CODE_IMAGE_PICK = 0

class BusinessImagesFragment : Fragment() {

    private lateinit var viewModel: OneViewModel
    private var _binding: FragmentBusinessImagesBinding? = null
    private val binding get() = _binding!!
    var curFile: Uri? = null
    private val imageUrls = mutableListOf<String>()
    private val imageAdapter = ImageAdapter(imageUrls)
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val imageRef = Firebase.storage.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessImagesBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[OneViewModel::class.java]

        listFiles()
        binding.ivImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        binding.btnUploadImage.setOnClickListener {
            uploadImageToStorage(binding.imageNameET.useText())
        }




        return view
    }


    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            binding.rvImages.adapter = imageAdapter
            binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 2)
            imageAdapter.setOnItemClickListener(object: ImageAdapter.OnImageClickListener{
                override fun onItemClick(position: Int) {
                    Glide.with(requireContext()).load(imageUrls[position]).into(binding.ivImage)
                }
                override fun onItemLongClick(position: Int) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to delete this image?")
                        .setPositiveButton("YES") { _, _ ->
                            deleteImage(imageUrls[position])
                            imageUrls.removeAt(position)
                            imageAdapter.urls = imageUrls
                            imageAdapter.notifyItemRemoved(position)
                        }.setNegativeButton("NO") { dialog, _ ->
                            dialog.dismiss()
                        }.create().show()
                }
            })
        }
        try {
            val images = imageRef.child("${firebaseAuth.currentUser!!.uid}/").listAll().await()

            for(image in images.items) {
                CoroutineScope(Dispatchers.IO).launch {
                    val url = image.downloadUrl.await()
                    withContext(Dispatchers.Main) {
                        imageUrls.add(url.toString())
                        imageAdapter.urls = imageAdapter.urls.plus(url.toString())
                        imageAdapter.notifyItemInserted(imageAdapter.urls.size-1)
                    }
                }
            }

        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Log.w("", e.message?: "")
            }
        }
    }

    private fun deleteImage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val stref = FirebaseStorage.getInstance()
            stref.getReferenceFromUrl(filename).delete().await()
            withContext(Dispatchers.Main) {
                longToast("Successfully deleted image.")
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Log.w("", e.message?: "")
            }
        }
    }


    private fun uploadImageToStorage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        if(filename.isEmpty()) {
            withContext(Dispatchers.Main){
                shortToast("Please provide picture name.")
            }
        } else {
            withContext(Dispatchers.Main){
                shortToast("Please wait.")
            }
            val images = imageRef
                .child("${firebaseAuth.currentUser!!.uid}/").listAll().await().items
            if (images.map { it.name }.contains(filename) && images.size != 9) {
                withContext(Dispatchers.Main){
                    shortToast("Name of image is taken")
                }
            } else {
                try {
                    curFile?.let {
                        imageRef.child("${firebaseAuth.currentUser!!.uid}/$filename").putFile(it).await()
                        withContext(Dispatchers.Main) {
                            longToast("Successfully uploaded image.")
                            imageUrls.add(it.toString())
                            imageAdapter.urls = imageUrls
                            imageAdapter.notifyItemInserted(imageAdapter.itemCount-1)
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.w("", e.message?: "")
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK) {
            data?.data?.let {
                curFile = it
                binding.ivImage.setImageURI(it)
            }
        }
    }

}
