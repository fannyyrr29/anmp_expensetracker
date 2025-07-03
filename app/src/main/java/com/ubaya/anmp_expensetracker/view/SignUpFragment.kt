package com.ubaya.anmp_expensetracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.anmp_expensetracker.R
import com.ubaya.anmp_expensetracker.databinding.FragmentSignUpBinding
import com.ubaya.anmp_expensetracker.model.User
import com.ubaya.anmp_expensetracker.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {
    private lateinit var binding:FragmentSignUpBinding
    private lateinit var viewModel:SignUpViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.toastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            if(it.contains("berhasil")){
                Navigation.findNavController(view).popBackStack()

            }
        }
        binding.btnCreateAccount.setOnClickListener {
            if (binding.textPassword.text.toString() == binding.textRepeatPassword.text.toString()){
                var user = User(
                    binding.textUsername.text.toString(), binding.textFirstName.text.toString(), binding.textLastname.text.toString(), binding.textPassword.text.toString()
                )
                viewModel.createAccount(user)
                //Toast.makeText(view.context, "Account has been created!", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(view.context, "Password and Repeat Password is not match", Toast.LENGTH_LONG).show()
            }
        }



    }
}