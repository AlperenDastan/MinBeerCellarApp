package com.example.minbeercellarapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minbeercellarapp.databinding.FragmentUpdateBeerBinding

class UpdateBeerFragment : Fragment() {
    private var _binding: FragmentUpdateBeerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BeerViewModel by viewModels {
        BeerViewModelFactory((requireActivity().application as BeerApplication).repository)
    }
    private val args: UpdateBeerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUpdateBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val beer = args.beer
        populateFields(beer)
        binding.buttonUpdateBeer.setOnClickListener {
            if (validateInput()) {
                val updatedBeer = beer.copy(
                    brewery = binding.editTextBrewery.text.toString(),
                    name = binding.editTextName.text.toString(),
                    style = binding.editTextStyle.text.toString(),
                    abv = binding.editTextAbv.text.toString().toDoubleOrNull() ?: beer.abv,
                    volume = binding.editTextVolume.text.toString().toDoubleOrNull(),
                    howMany = binding.editTextHowMany.text.toString().toIntOrNull() ?: beer.howMany
                )
                updateBeer(updatedBeer)
            }
        }
    }

    private fun populateFields(beer: Beer) {
        binding.apply {
            editTextBrewery.setText(beer.brewery)
            editTextName.setText(beer.name)
            editTextStyle.setText(beer.style)
            editTextAbv.setText(beer.abv.toString())
            editTextVolume.setText(beer.volume?.toString() ?: "")
            editTextHowMany.setText(beer.howMany.toString())
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        clearErrors() // Clear any existing errors

        if (binding.editTextBrewery.text.isBlank()) {
            binding.editTextBrewery.error = "Brewery is required"
            isValid = false
        }
        if (binding.editTextName.text.isBlank()) {
            binding.editTextName.error = "Name is required"
            isValid = false
        }
        if (binding.editTextStyle.text.isBlank()) {
            binding.editTextStyle.error = "Style is required"
            isValid = false
        }
        if (binding.editTextAbv.text.toString().toDoubleOrNull() == null) {
            binding.editTextAbv.error = "ABV must be a number"
            isValid = false
        }
        if (binding.editTextVolume.text.toString().toDoubleOrNull() == null) {
            binding.editTextVolume.error = "Volume must be a number"
            isValid = false
        }
        if (binding.editTextHowMany.text.toString().toIntOrNull() == null) {
            binding.editTextHowMany.error = "Quantity must be a number"
            isValid = false
        }

        return isValid
    }

    private fun updateBeer(updatedBeer: Beer) {
        viewModel.updateBeer(updatedBeer)
        Toast.makeText(context, "Beer updated successfully!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(UpdateBeerFragmentDirections.actionUpdateBeerFragmentToSecondFragment())
    }

    private fun clearErrors() {
        // Clear all error messages
        binding.editTextBrewery.error = null
        binding.editTextName.error = null
        binding.editTextStyle.error = null
        binding.editTextAbv.error = null
        binding.editTextVolume.error = null
        binding.editTextHowMany.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}