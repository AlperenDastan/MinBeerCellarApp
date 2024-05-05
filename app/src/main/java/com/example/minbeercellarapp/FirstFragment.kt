package com.example.minbeercellarapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.minbeercellarapp.databinding.FragmentFirstBinding
import com.google.firebase.auth.FirebaseAuth

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            loginUser()
        }

        binding.registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun loginUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        if (email.isBlank() || password.isBlank()) {
            showMessage("Fields cannot be empty")
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showMessage("Login successful")
                    navigateToMain()
                } else {
                    showMessage("Login failed: ${task.exception?.message}")
                }
            }
        }
    }

    private fun registerUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        if (email.isBlank() || password.isBlank()) {
            showMessage("Fields cannot be empty")
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showMessage("Registration successful")
                    navigateToMain()
                } else {
                    showMessage("Registration failed: ${task.exception?.message}")
                }
            }
        }
    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
