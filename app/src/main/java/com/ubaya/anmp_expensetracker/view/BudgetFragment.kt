package com.ubaya.anmp_expensetracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.anmp_expensetracker.R
import com.ubaya.anmp_expensetracker.databinding.FragmentBudgetBinding
import com.ubaya.anmp_expensetracker.viewmodel.BudgetViewModel

class BudgetFragment : Fragment() {
    private lateinit var binding:FragmentBudgetBinding
    private lateinit var viewModel:BudgetViewModel
    private val budgetListAdapter = BudgetListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BudgetViewModel::class)
        viewModel.Refresh()
        binding.budgetRecView.layoutManager = LinearLayoutManager(context)
        binding.budgetRecView.adapter = budgetListAdapter


        binding.fabAddBudget.setOnClickListener {
            val action = BudgetFragmentDirections.actionAddBudgetFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.Refresh()
        }

        observeViewModel()
    }


    fun observeViewModel() {
        viewModel.budgetLD.observe(viewLifecycleOwner, Observer {
            budgetListAdapter.updateBudgetList(it)
            if(it.isEmpty()) {
                binding.budgetRecView?.visibility = View.GONE
                binding.txtError.setText("Your Budget still empty.")
            } else {
                binding.budgetRecView?.visibility = View.VISIBLE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
        })


        viewModel.budgetLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.txtError?.visibility = View.GONE
            } else {
                binding.txtError?.visibility = View.VISIBLE
            }
        })
    }

}