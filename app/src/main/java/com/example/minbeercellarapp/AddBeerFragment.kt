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
        val howMany = binding.editTextHowMany.text.toString().toIntOrNull()

        clearErrors()
        if (validateInput(brewery, name, style, abv, volume, howMany)) {
            val newBeer = Beer(
                id = 0,
                user = FirebaseAuth.getInstance().currentUser?.email ?: "",
                brewery = brewery,
                name = name,
                style = style,
                abv = abv!!,
                volume = volume,
                pictureUrl = null,
                howMany = howMany ?: 0
            )
            viewModel.addBeer(newBeer)
            viewModel.beers.observe(viewLifecycleOwner) { beers ->
                Toast.makeText(context, "Beer added successfully!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            viewModel.error.observe(viewLifecycleOwner) { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(brewery: String, name: String, style: String, abv: Double?, volume: Double?, howMany: Int?): Boolean {
        var isValid = true
        if (brewery.isBlank()) {
            binding.textViewErrorBrewery.text = "Brewery is required"
            binding.textViewErrorBrewery.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.textViewErrorBrewery.visibility = View.GONE
        }

        if (name.isBlank()) {
            binding.textViewErrorName.text = "Name is required"
            binding.textViewErrorName.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.textViewErrorName.visibility = View.GONE
        }

        if (style.isBlank()) {
            binding.textViewErrorStyle.text = "Style is required"
            binding.textViewErrorStyle.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.textViewErrorStyle.visibility = View.GONE
        }

        if (abv == null) {
            binding.textViewErrorAbv.text = "ABV must be a number"
            binding.textViewErrorAbv.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.textViewErrorAbv.visibility = View.GONE
        }

        if (volume == null) {
            binding.textViewErrorVolume.text = "Volume must be a number"
            binding.textViewErrorVolume.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.textViewErrorVolume.visibility = View.GONE
        }

        if (howMany == null) {
            binding.textViewErrorHowMany.text = "Quantity must be a number"
            binding.textViewErrorHowMany.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.textViewErrorHowMany.visibility = View.GONE
        }

        return isValid
    }

    private fun clearErrors() {
        binding.textViewErrorBrewery.visibility = View.GONE
        binding.textViewErrorName.visibility = View.GONE
        binding.textViewErrorStyle.visibility = View.GONE
        binding.textViewErrorAbv.visibility = View.GONE
        binding.textViewErrorVolume.visibility = View.GONE
        binding.textViewErrorHowMany.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
