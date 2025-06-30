package com.ubaya.anmp_expensetracker.view

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.anmp_expensetracker.databinding.FragmentLoginBinding
import com.ubaya.anmp_expensetracker.viewmodel.SignInViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel:SignInViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        //Melakukan pengecekkan apakah pernah login
        val username = prefs.getString("username", null)

        if (username != null){
            goToMainActivity()
            return
        }
        viewModel = ViewModelProvider(this).get(SignInViewModel::class)

        binding.btnSignIn.setOnClickListener {
            viewModel.checkLogin(binding.textUsername.text.toString(), binding.textPassword.text.toString())
            viewModel.userLogin.observe(viewLifecycleOwner){ user ->
                if (user != null){
                    Toast.makeText(context, "Login berhasil! Selamat datang ${user.username}", Toast.LENGTH_SHORT).show()
                    val editor = prefs.edit()
                    editor.putString("username", user.username)
                    editor.putString("firstname", user.firstname)
                    editor.putString("lastname", user.lastname)
                    editor.putString("uuid", user.uuid.toString())
                    editor.apply()

                    goToMainActivity()
                }else{
                    Toast.makeText(context, "Gagal Login", Toast.LENGTH_LONG).show()
                }
            }

        }
        binding.btnSignUp.setOnClickListener {
            val action = LoginFragmentDirections.actionSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun goToMainActivity(){
        val intent = Intent(
            requireContext(),
            MainActivity::class.java
        )
        startActivity(intent)
        requireActivity().finish()
    }


}