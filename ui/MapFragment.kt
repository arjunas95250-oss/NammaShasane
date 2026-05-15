package com.mindmatrix.nammashasane.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mindmatrix.nammashasane.R
import com.mindmatrix.nammashasane.databinding.FragmentMapBinding
import com.mindmatrix.nammashasane.model.Shasana
import com.mindmatrix.nammashasane.viewmodel.ShasanaViewModel
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShasanaViewModel by activityViewModels()
    private lateinit var mapView: MapView
    private val markerShasanaMap = mutableMapOf<Marker, Shasana>()

    // TODO: Add your OpenRouter API key to local.properties or BuildConfig
    // private val OPENROUTER_API_KEY = BuildConfig.OPENROUTER_API_KEY

    // Camera launcher
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imagePath = result.data?.getStringExtra("imagePath")
            if (imagePath != null) {
                val bitmap = BitmapFactory.decodeFile(imagePath)
                analyzeInscriptionImage(bitmap)
            }
        }
    }

    // Gallery launcher
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val inputStream: InputStream? = requireContext().contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            analyzeInscriptionImage(bitmap)
        }
    }

    // Permission launcher
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) openCamera()
        else Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Configuration.getInstance().userAgentValue = requireContext().packageName
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup OSM map
        mapView = binding.map
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(7.0)
        mapView.controller.setCenter(GeoPoint(15.3173, 75.7139))

        // FAB click → show options: Add Shasana, Scan Inscription
        binding.fabAddShasana.setOnClickListener {
            showActionDialog()
        }

        viewModel.allShasanas.observe(viewLifecycleOwner) { shasanas ->
            mapView.overlays.clear()
            markerShasanaMap.clear()
            shasanas.forEach { shasana ->
                val marker = Marker(mapView)
                marker.position = GeoPoint(shasana.latitude, shasana.longitude)
                marker.title = shasana.title
                marker.snippet = "${shasana.dynasty} • ${shasana.period}"
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.setOnMarkerClickListener { m, _ ->
                    m.showInfoWindow()
                    true
                }
                markerShasanaMap[marker] = shasana
                mapView.overlays.add(marker)
            }
            mapView.invalidate()
            binding.tvShasanaCount.text = "${shasanas.size} Inscriptions Found"

            // Info window click → navigate to detail
            mapView.overlays.filterIsInstance<Marker>().forEach { marker ->
                marker.infoWindow?.view?.setOnClickListener {
                    markerShasanaMap[marker]?.let { shasana ->
                        findNavController().navigate(
                            R.id.action_mapFragment_to_storyFragment,
                            bundleOf("shasanaId" to shasana.id)
                        )
                    }
                }
            }
        }
    }

    private fun showActionDialog() {
        val options = arrayOf("➕ Add New Inscription", "📷 Scan Inscription with Camera", "🖼️ Scan from Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("What would you like to do?")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> findNavController().navigate(R.id.action_mapFragment_to_addShasanaFragment)
                    1 -> checkCameraPermissionAndOpen()
                    2 -> galleryLauncher.launch("image/*")
                }
            }
            .show()
    }

    private fun checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        cameraLauncher.launch(intent)
    }

    private fun analyzeInscriptionImage(bitmap: Bitmap) {
        // Show loading dialog
        val loadingDialog = AlertDialog.Builder(requireContext())
            .setTitle("🔍 Analyzing Inscription...")
            .setMessage("Please wait while AI reads the inscription...")
            .setCancelable(false)
            .create()
        loadingDialog.show()

        // Convert bitmap to base64
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val base64Image = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://openrouter.ai/api/v1/chat/completions")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", "Bearer $OPENROUTER_API_KEY")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val body = JSONObject().apply {
                    put("model", "google/gemini-flash-1.5")
                    put("messages", JSONArray().apply {
                        put(JSONObject().apply {
                            put("role", "user")
                            put("content", JSONArray().apply {
                                put(JSONObject().apply {
                                    put("type", "image_url")
                                    put("image_url", JSONObject().apply {
                                        put("url", "data:image/jpeg;base64,$base64Image")
                                    })
                                })
                                put(JSONObject().apply {
                                    put("type", "text")
                                    put("text", """
                                        You are an expert in ancient Indian inscriptions, especially Karnataka/South Indian stone inscriptions (Shasanas).
                                        Analyze this image and provide:
                                        1. What dynasty/period this inscription belongs to
                                        2. The approximate date/century
                                        3. What the inscription says (translate to English)
                                        4. Historical significance
                                        5. Any king or ruler mentioned
                                        
                                        If this is not an inscription, say so clearly.
                                        Respond in clear English only.
                                    """.trimIndent())
                                })
                            })
                        })
                    })
                }

                connection.outputStream.write(body.toString().toByteArray())
                val response = connection.inputStream.bufferedReader().readText()
                val result = JSONObject(response)
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")

                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    showResultDialog(result)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage("Could not analyze image: ${e.message}")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
        }
    }

    private fun showResultDialog(result: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("📜 Inscription Analysis")
            .setMessage(result)
            .setPositiveButton("Close", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}