package com.ubaya.anmp_expensetracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.anmp_expensetracker.databinding.ReportListItemBinding
import com.ubaya.anmp_expensetracker.model.BudgetWithListExpenses
import com.ubaya.anmp_expensetracker.util.formatRupiah

class ReportListAdapter(val reportList:ArrayList<BudgetWithListExpenses>):RecyclerView.Adapter<ReportListAdapter.ReportViewHolder>() {
    class ReportViewHolder(var binding: ReportListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ReportListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.binding.txtBudgetName.text = reportList[position].budget.name
        holder.binding.txtTotalReport.text = formatRupiah(reportList[position].budget.nominal.toDouble())
        val used = reportList[position].expenses.sumOf { it.nominal }
        holder.binding.txtUsedReport.text = formatRupiah(used)
        val left = reportList[position].budget.nominal - used
        holder.binding.txtBudgetLeft.text = formatRupiah(left)

        val progress = if (reportList[position].budget.nominal > 0) {
            (used / reportList[position].budget.nominal * 100).toInt()
        } else {
            0
        }

        holder.binding.progressBarReport.progress = progress


    }

    fun updateExpense(newList:List<BudgetWithListExpenses>){
        reportList.clear()
        reportList.addAll(newList)
        notifyDataSetChanged()
    }


}