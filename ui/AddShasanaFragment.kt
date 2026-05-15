package com.mindmatrix.nammashasane.ui

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mindmatrix.nammashasane.databinding.FragmentAddShasanaBinding
import com.mindmatrix.nammashasane.viewmodel.ShasanaViewModel

class AddShasanaFragment : Fragment() {

    private var _binding: FragmentAddShasanaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShasanaViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLat = 15.3173
    private var currentLng = 75.7139
    private var imagePath = ""

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            Toast.makeText(requireContext(), "ಫೋಟೋ ಸೆರೆ ಹಿಡಿಯಲಾಗಿದೆ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddShasanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getLocation()

        binding.btnTakePhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureLauncher.launch(intent)
        }

        binding.btnSave.setOnClickListener {
            saveShasana()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    currentLat = it.latitude
                    currentLng = it.longitude
                    binding.tvLocation.text = "📍 ${String.format("%.4f", it.latitude)}, ${String.format("%.4f", it.longitude)}"
                }
            }
        } catch (e: SecurityException) {
            // Permission not granted
        }
    }

    private fun saveShasana() {
        val title = binding.etTitle.text.toString().trim()
        val dynasty = binding.etDynasty.text.toString().trim()
        val period = binding.etPeriod.text.toString().trim()
        val location = binding.etLocation.text.toString().trim()
        val king = binding.etKing.text.toString().trim()
        val giftLaw = binding.etGiftLaw.text.toString().trim()
        val transKannada = binding.etTranslationKannada.text.toString().trim()
        val transEnglish = binding.etTranslationEnglish.text.toString().trim()

        if (title.isEmpty() || dynasty.isEmpty()) {
            Toast.makeText(requireContext(), "ಶೀರ್ಷಿಕೆ ಮತ್ತು ರಾಜವಂಶ ಅಗತ್ಯ", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.addUserShasana(
            title, dynasty, period, location, currentLat, currentLng,
            transKannada, transEnglish, king, giftLaw, imagePath
        )

        Toast.makeText(requireContext(), "ಶಾಸನ ನಕ್ಷೆಗೆ ಸೇರಿಸಲಾಗಿದೆ!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
