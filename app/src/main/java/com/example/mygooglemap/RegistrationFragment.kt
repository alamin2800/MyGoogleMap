package com.example.mygooglemap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mygooglemap.databinding.FragmentRegistrationBinding
import com.example.mygooglemap.models.ProfileModel
import com.example.mygooglemap.viewmodels.RegistrationViewModel
import kotlin.random.Random

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        viewModel.erroeMsgLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }
        binding.otpCodeId.setOnClickListener {
            getOtp()

        }
        binding.RegSubmitButtonId.setOnClickListener {
            val firstName = binding.firstNameEt.text.toString()
            val lastName = binding.lsatNameEt.text.toString()
            val userName = binding.usernameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val retypePassword = binding.retypePasswordEt.text.toString()
            if (password.isEmpty() || retypePassword.isEmpty() || password != retypePassword) {
                Toast.makeText(
                    requireActivity(),
                    "please fill the input and write password correctly", Toast.LENGTH_LONG
                ).show()

            }
            if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || email.isEmpty()) {
                Toast.makeText(
                    requireActivity(),
                    "please fill the input", Toast.LENGTH_LONG
                ).show()

            }
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty() && retypePassword.isNotEmpty() && password == retypePassword) {
                val profileModel =
                    ProfileModel(firstName = firstName, lastName = lastName, userName = userName)

                viewModel.insertProfile(profileModel)

                viewModel.registration(email, password) {
                    Toast.makeText(
                        requireActivity(),
                        "Successfually Registration Complete",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)

                }
            }


        }

        return binding.root
    }

    private fun getOtp() {
        val otp = List(1) { Random.nextInt(100000, 900000) }
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("alamin.ict2800@gmail.com"))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, "Verification code")
        mIntent.putExtra(Intent.EXTRA_TEXT, "your OTP code is ${otp[0]}")


        try {
            startActivity(Intent.createChooser(mIntent, "alamin@gmail.com"))
        } catch (e: Exception) {
            Toast.makeText(requireActivity(), e.message, Toast.LENGTH_LONG).show()
        }

    }

}