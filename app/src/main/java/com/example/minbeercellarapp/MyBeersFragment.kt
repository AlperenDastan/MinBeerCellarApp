package com.example.minbeercellarapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minbeercellarapp.databinding.FragmentMyBeersBinding
import com.google.firebase.auth.FirebaseAuth

class MyBeersFragment : Fragment() {
    private var _binding: FragmentMyBeersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BeerViewModel by viewModels {
        BeerViewModelFactory((requireActivity().application as BeerApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyBeersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BeerAdapter() // No need to pass an empty list
        binding.beersRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.beersRecyclerView.adapter = adapter

        viewModel.beers.observe(viewLifecycleOwner) { beers ->
            adapter.submitList(beers) // Use submitList instead of updateBeers
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(context, "Error fetching beers: $error", Toast.LENGTH_LONG).show()
            }
        }

        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
        viewModel.getUserBeers(currentUserEmail) // Make sure method names match your actual implementation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}