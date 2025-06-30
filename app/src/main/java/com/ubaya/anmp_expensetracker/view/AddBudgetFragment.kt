package com.ubaya.anmp_expensetracker.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.anmp_expensetracker.R
import com.ubaya.anmp_expensetracker.databinding.FragmentAddBudgetBinding
import com.ubaya.anmp_expensetracker.model.Budget
import com.ubaya.anmp_expensetracker.viewmodel.BudgetViewModel
import kotlinx.coroutines.Job


class AddBudgetFragment : Fragment() {
    private lateinit var binding:FragmentAddBudgetBinding
    private lateinit var viewModel:BudgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBudgetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BudgetViewModel::class)

        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val user_id = prefs.getString("uuid", null)

        binding.btnAddBudget.setOnClickListener {
            val budget = Budget(
                binding.txtBudgetName.text.toString(),
                binding.txtNominal.text.toString().toInt(),
                user_id!!.toInt()
            )
            viewModel.AddBudget(budget)
        }

        viewModel.insertSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Budget has been added!", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).popBackStack()

                viewModel.insertSuccess.value = false
            }
        }


    }


}