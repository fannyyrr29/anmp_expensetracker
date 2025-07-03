package com.ubaya.anmp_expensetracker.view

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ubaya.anmp_expensetracker.R
import com.ubaya.anmp_expensetracker.databinding.FragmentProfileBinding
import com.ubaya.anmp_expensetracker.databinding.FragmentReportBinding
import com.ubaya.anmp_expensetracker.viewmodel.ProfileViewModel
import com.ubaya.anmp_expensetracker.viewmodel.ReportViewModel

class ProfileFragment : Fragment() {
    private lateinit var  binding:FragmentProfileBinding
    private lateinit var viewModel:ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.toastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.isSignOut.observe(viewLifecycleOwner) {
            if (it == true) {
                startActivity(Intent(requireContext(), AuthActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                viewModel.isSignOut.postValue(false)
                requireActivity().finish()
            }

        }
    }
}