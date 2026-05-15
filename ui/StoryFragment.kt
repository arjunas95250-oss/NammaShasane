package com.mindmatrix.nammashasane.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mindmatrix.nammashasane.databinding.FragmentStoryBinding
import com.mindmatrix.nammashasane.viewmodel.ShasanaViewModel

class StoryFragment : Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShasanaViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shasanaId = arguments?.getInt("shasanaId", 0) ?: 0

        viewModel.allShasanas.observe(viewLifecycleOwner) { shasanas ->
            val shasana = shasanas.find { it.id == shasanaId } ?: return@observe
            with(binding) {
                tvTitle.text = shasana.title
                tvDynasty.text = "🏰 ${shasana.dynasty}"
                tvPeriod.text = "🕰️ ${shasana.period}"
                tvLocation.text = "📍 ${shasana.location}"
                tvKing.text = "👑 ${shasana.king}"
                tvGiftOrLaw.text = "📜 ${shasana.giftOrLaw}"
                tvTranslationKannada.text = shasana.translationKannada
                tvTranslationEnglish.text = shasana.translationEnglish
                tvDamagedBadge.visibility = if (shasana.isDamaged) View.VISIBLE else View.GONE
                tvUserAddedBadge.visibility = if (shasana.isUserAdded) View.VISIBLE else View.GONE

                btnReportDamage.setOnClickListener {
                    showDamageReportDialog(shasana.id)
                }
                btnBack.setOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun showDamageReportDialog(shasanaId: Int) {
        val editText = EditText(requireContext()).apply {
            hint = "Describe the damage..."
            setPadding(32, 16, 32, 16)
        }
        AlertDialog.Builder(requireContext())
            .setTitle("🚨 Report Damage")
            .setMessage("Report that this inscription has been damaged")
            .setView(editText)
            .setPositiveButton("Submit Report") { _, _ ->
                val desc = editText.text.toString()
                if (desc.isNotBlank()) {
                    viewModel.allShasanas.value?.find { it.id == shasanaId }?.let { shasana ->
                        viewModel.reportDamage(shasana, desc, shasana.latitude, shasana.longitude)
                        Toast.makeText(requireContext(), "Report submitted!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}