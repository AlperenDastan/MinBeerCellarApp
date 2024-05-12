package com.example.minbeercellarapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minbeercellarapp.databinding.FragmentMyBeersBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MyBeersFragment : Fragment() {
    private var _binding: FragmentMyBeersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BeerViewModel by viewModels {
        BeerViewModelFactory((requireActivity().application as BeerApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBeersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BeerAdapter(
            onDeleteClick = { beerId ->
                viewModel.deleteBeer(beerId)
                Toast.makeText(context, "Beer deleted", Toast.LENGTH_SHORT).show()
            },
            onEditClick = { beer ->
                val action = MyBeersFragmentDirections.actionMyBeersFragmentToUpdateBeerFragment(beer)
                findNavController().navigate(action)
            }
        )
        binding.beersRecyclerView.adapter = adapter
        binding.beersRecyclerView.layoutManager = LinearLayoutManager(context)

        val sortingOptions = arrayOf("Brewery", "Name", "ABV")
        binding.sortingSpinner.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, sortingOptions
        )

        binding.sortingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (view == null) {
                    return
                }
                viewModel.sortBeers(sortingOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Optionally handle the case where nothing is selected
            }
        }

        binding.searchButton.setOnClickListener {
            val filterText = binding.filterEditText.text.toString()
            if (filterText.isBlank()) {
                viewModel.getUserBeers(FirebaseAuth.getInstance().currentUser?.email ?: "")
            } else {
                viewModel.filterBeers(filterText)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserBeers(FirebaseAuth.getInstance().currentUser?.email ?: "")
        }

        viewModel.beers.observe(viewLifecycleOwner) { beers ->
            adapter.submitList(beers)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(context, "Error fetching beers: $error", Toast.LENGTH_LONG).show()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
        viewModel.getUserBeers(currentUserEmail) // Trigger initial load
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}