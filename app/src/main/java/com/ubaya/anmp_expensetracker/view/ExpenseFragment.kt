package com.ubaya.anmp_expensetracker.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.anmp_expensetracker.R
import com.ubaya.anmp_expensetracker.databinding.FragmentExpenseBinding
import com.ubaya.anmp_expensetracker.model.Expense
import com.ubaya.anmp_expensetracker.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ExpenseFragment : Fragment() {
    private lateinit var binding:FragmentExpenseBinding
    private  lateinit var viewModel:ExpenseViewModel
    private val expenseAdapter = ExpenseListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExpenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class)

        val sharedPref = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val uuid = sharedPref.getString("uuid", null)

        if (uuid != null){
            viewModel.Refresh(uuid.toString().toInt())
            binding.expenseRecView.layoutManager = LinearLayoutManager(context)
            binding.expenseRecView.adapter = expenseAdapter

            binding.btnAddExpense.setOnClickListener {
                val action = ExpenseFragmentDirections.actionAddExpense()
                Navigation.findNavController(it).navigate(action)
            }

            observeViewModel()


        }

    }

    fun observeViewModel(){
        viewModel.expenseLD.observe(viewLifecycleOwner, Observer {
            expenseAdapter.updateExpense(it)
            if (it.isEmpty()){
                binding.expenseRecView?.visibility = View.GONE
            }else{
                binding.expenseRecView?.visibility = View.VISIBLE

            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer{
            if (it == false){
                binding.progressLoadExpense?.visibility = View.GONE
            }else{
                binding.progressLoadExpense?.visibility = View.VISIBLE
            }
        })

        viewModel.expenseLoadErrorLD.observe(viewLifecycleOwner,  Observer {
            if (it == false){
                binding.txtExpenseError.visibility = View.GONE
            }else{
                binding.txtExpenseError?.visibility = View.VISIBLE
            }
        })
    }

}