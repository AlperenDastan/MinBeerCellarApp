package com.example.minbeercellarapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.minbeercellarapp.databinding.FragmentSecondBinding
import com.google.firebase.auth.FirebaseAuth

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewMyBeersButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_MyBeersFragment)
        }

        //binding.viewUserBeersButton.setOnClickListener {
            // Navigate to Search Beers Screen
          //  findNavController().navigate(R.id.action_SecondFragment_to_SearchBeersFragment)
        //}

        binding.addBeerButton.setOnClickListener {
            // Navigate to Add Beer Screen
            findNavController().navigate(R.id.action_SecondFragment_to_AddBeerFragment)
        }

        binding.logoutButton.setOnClickListener {
            // Log out and navigate to SignIn Screen
            auth.signOut()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}