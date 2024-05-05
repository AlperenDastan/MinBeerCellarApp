package com.example.minbeercellarapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.minbeercellarapp.databinding.FragmentAddBeerBinding
import com.google.firebase.auth.FirebaseAuth

class AddBeerFragment : Fragment() {
    private var _binding: FragmentAddBeerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BeerViewModel by viewModels {
        BeerViewModelFactory((requireActivity().application as BeerApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddBeer.setOnClickListener {
            addBeer()
        }
    }

    private fun addBeer() {
        val brewery = binding.editTextBrewery.text.toString()
        val name = binding.editTextName.text.toString()
        val style = binding.editTextStyle.text.toString()
        val abv = binding.editTextAbv.text.toString().toDoubleOrNull()
        val volume = binding.editTextVolume.text.toString().toDoubleOrNull()
        val howMany = binding.editTextHowMany.text.toString().toIntOrNull() ?: 0

        if (brewery.isBlank() || name.isBlank() || style.isBlank() || abv == null) {
            Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
        } else {
            val newBeer = Beer(
                id = 0, // Assuming ID is auto-generated or not needed for new entries
                user = FirebaseAuth.getInstance().currentUser?.email ?: "",
                brewery = brewery,
                name = name,
                style = style,
                abv = abv,
                volume = volume,
                pictureUrl = null, // Assuming no picture URL is entered
                howMany = howMany
            )
            viewModel.addBeer(newBeer)
            viewModel.beers.observe(viewLifecycleOwner) { beers ->
                Toast.makeText(context, "Beer added successfully!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Navigate back after successful addition
            }
            viewModel.error.observe(viewLifecycleOwner) { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
