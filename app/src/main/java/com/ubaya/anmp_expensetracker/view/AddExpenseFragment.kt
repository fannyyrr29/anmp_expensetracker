package com.ubaya.anmp_expensetracker.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.anmp_expensetracker.databinding.FragmentAddExpenseBinding
import com.ubaya.anmp_expensetracker.model.Expense
import com.ubaya.anmp_expensetracker.util.formatRupiah
import com.ubaya.anmp_expensetracker.viewmodel.ExpenseViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddExpenseFragment : Fragment() {
    private lateinit var binding:FragmentAddExpenseBinding
    private lateinit var viewModel:ExpenseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExpenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class)

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDateTime = formatter.format(Date())
        binding.txtDate.text = currentDateTime.toString()

        //Loading Pemakaian Budget
        val budgetList = viewModel.selectBudget()
        viewModel.budgetLD.observe(viewLifecycleOwner){ budgetList ->
            if (budgetList != null){
                val budgetsName = budgetList.map { it.name }
                val adapter = ArrayAdapter(
                    requireContext(), android.R.layout.simple_spinner_item, budgetsName
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerBudget.adapter = adapter

                binding.spinnerBudget.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val selectedBudget = budgetList[p2]
                        binding.txtTotalEnd.text = formatRupiah(selectedBudget.nominal.toString().toDouble())

                        viewModel.checkUsedBudget(selectedBudget.uuid) ?: 0.0

                        viewModel.usedBudget.observe(viewLifecycleOwner) { used ->
                            binding.txtUsed.text = formatRupiah(used)
                        }
                        viewModel.updateProgressBar(selectedBudget.uuid, selectedBudget.nominal.toDouble())
                        viewModel.percentUsedLD.observe(viewLifecycleOwner) { percent ->
                            binding.progressBarExpense.progress = percent
                        }

                        binding.btnAddExpense.setOnClickListener {
                            val nominal = binding.txtExpenseNominal.text.toString().toDoubleOrNull() ?: 0.0
                            val note = binding.txtNote.text.toString()
                            val currentTime = System.currentTimeMillis()
                            if (viewModel.usedBudget.value <= selectedBudget.nominal){
                                val expense = Expense(tanggal = currentTime, nominal = nominal, notes = note, budget_id = selectedBudget.uuid  )
                                viewModel.addExpense(expense)
                                Toast.makeText(context, "Expense has sucessfully inputted!", Toast.LENGTH_LONG).show()
                                Navigation.findNavController(it).popBackStack()
                            }else{
                                Toast.makeText(context, "Expense can't be inputted. Your Expense Exceeding Budget Limit", Toast.LENGTH_LONG).show()
                            }

                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
            }

        }
    }
}