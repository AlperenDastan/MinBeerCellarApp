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
        savedInstanceState: Bundle?): View {
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
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        var hasError = false

        if (email.isBlank()) {
            binding.textViewErrorEmail.text = "Email cannot be empty"
            binding.textViewErrorEmail.visibility = View.VISIBLE
            hasError = true
        } else {
            binding.textViewErrorEmail.visibility = View.GONE
        }

        if (password.isBlank()) {
            binding.textViewErrorPassword.text = "Password cannot be empty"
            binding.textViewErrorPassword.visibility = View.VISIBLE
            hasError = true
        } else {
            binding.textViewErrorPassword.visibility = View.GONE
        }

        if (!hasError) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToMain()
                } else {
                    binding.textViewErrorEmail.text = "Login failed: Incorrect email or password"
                    binding.textViewErrorEmail.visibility = View.VISIBLE
                    binding.textViewErrorPassword.visibility = View.GONE
                }
            }
        }
    }

    private fun registerUser() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        var hasError = false

        if (email.isBlank()) {
            binding.textViewErrorEmail.text = "Email cannot be empty"
            binding.textViewErrorEmail.visibility = View.VISIBLE
            hasError = true
        } else {
            binding.textViewErrorEmail.visibility = View.GONE
        }

        if (password.isBlank()) {
            binding.textViewErrorPassword.text = "Password cannot be empty"
            binding.textViewErrorPassword.visibility = View.VISIBLE
            hasError = true
        } else {
            binding.textViewErrorPassword.visibility = View.GONE
        }

        if (!hasError) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showMessage("Registration successful")
                    navigateToMain()
                } else {
                    binding.textViewErrorEmail.text = "Registration failed: ${task.exception?.message}"
                    binding.textViewErrorEmail.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun clearErrors() {
        binding.textViewErrorEmail.visibility = View.GONE
        binding.textViewErrorPassword.visibility = View.GONE
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
