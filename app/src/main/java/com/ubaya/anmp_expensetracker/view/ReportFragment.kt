package com.ubaya.anmp_expensetracker.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.anmp_expensetracker.R
import com.ubaya.anmp_expensetracker.databinding.FragmentReportBinding
import com.ubaya.anmp_expensetracker.util.formatRupiah
import com.ubaya.anmp_expensetracker.viewmodel.ReportViewModel

class ReportFragment : Fragment() {
    private lateinit var binding:FragmentReportBinding
    private lateinit var viewModel:ReportViewModel
    private val reportAdapter = ReportListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val uuid = sharedPref.getString("uuid", null)
        if (uuid != null){
            viewModel = ViewModelProvider(this).get(ReportViewModel::class)
            viewModel.Refresh(uuid.toString().toInt())
            binding.recViewReport.layoutManager = LinearLayoutManager(context)
            binding.recViewReport.adapter = reportAdapter

            observeViewModel()
        }

    }

    fun observeViewModel(){
        viewModel.reportLD.observe(viewLifecycleOwner, Observer { reportList ->
            reportAdapter.updateExpense(reportList)

            val totalBudget = reportList.sumOf { it.budget.nominal }
            val totalExpenses = reportList.sumOf { it.expenses.sumOf { expense -> expense.nominal } }

            binding.txtRecap.setText("${formatRupiah(totalExpenses)}/${formatRupiah(totalBudget.toDouble())}")

            if (reportList.isEmpty()){
                binding.recViewReport?.visibility = View.GONE
                binding.txtErrorReport.setText("Your Report is still empty")
            }else{
                binding.recViewReport?.visibility = View.VISIBLE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            binding.progressLoadReport.visibility = if (it == true) View.VISIBLE else View.GONE
        })

        viewModel.reportErrorLD.observe(viewLifecycleOwner, Observer {
            if (it == false){
                binding.txtErrorReport?.visibility = View.GONE
            }else{
                binding.txtErrorReport?.visibility = View.VISIBLE
            }
        })
    }

}