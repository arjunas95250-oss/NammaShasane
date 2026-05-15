package com.mindmatrix.nammashasane.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindmatrix.nammashasane.R
import com.mindmatrix.nammashasane.adapter.ShasanaAdapter
import com.mindmatrix.nammashasane.databinding.FragmentListBinding
import com.mindmatrix.nammashasane.viewmodel.ShasanaViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShasanaViewModel by activityViewModels()
    private lateinit var adapter: ShasanaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShasanaAdapter { shasana ->
            findNavController().navigate(
                R.id.action_listFragment_to_storyFragment,
                bundleOf("shasanaId" to shasana.id)
            )
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ListFragment.adapter
        }

        viewModel.allShasanas.observe(viewLifecycleOwner) { shasanas ->
            adapter.submitList(shasanas)
            binding.tvEmpty.visibility = if (shasanas.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
