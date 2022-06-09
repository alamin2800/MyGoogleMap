package com.example.mygooglemap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mygooglemap.databinding.FragmentLoginBinding
import com.example.mygooglemap.viewmodels.Auth
import com.example.mygooglemap.viewmodels.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.SignUpButtonId.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        viewModel.AuthLiveData.observe(viewLifecycleOwner) {
            if (it == Auth.AUTHENTICATED) {
                findNavController().navigate(R.id.action_loginFragment_to_mapFragment)
            }
        }
        viewModel.erroeMsgLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }
        binding.submitLoginButton.setOnClickListener {
            val email = binding.usernameLoginEt.text.toString()
            val password = binding.passwordLoginEt.text.toString()
            viewModel.login(email, password)
        }
        

        return binding.root
    }
}

