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
        binding.apply {
            editTextBrewery.setText(beer.brewery)
            editTextName.setText(beer.name)
            editTextStyle.setText(beer.style)
            editTextAbv.setText(beer.abv.toString())
            editTextVolume.setText(beer.volume?.toString() ?: "")
            editTextHowMany.setText(beer.howMany.toString())
            buttonUpdateBeer.setOnClickListener {
                updateBeer(beer.copy(
                    brewery = editTextBrewery.text.toString(),
                    name = editTextName.text.toString(),
                    style = editTextStyle.text.toString(),
                    abv = editTextAbv.text.toString().toDoubleOrNull() ?: beer.abv,
                    volume = editTextVolume.text.toString().toDoubleOrNull(),
                    howMany = editTextHowMany.text.toString().toIntOrNull() ?: beer.howMany
                ))
            }
        }

        // Handle back press to navigate to SecondFragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(UpdateBeerFragmentDirections.actionUpdateBeerFragmentToSecondFragment())
            }
        })
    }

    private fun updateBeer(updatedBeer: Beer) {
        viewModel.updateBeer(updatedBeer)
        Toast.makeText(context, "Beer updated successfully!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(UpdateBeerFragmentDirections.actionUpdateBeerFragmentToSecondFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}