package com.ubaya.anmp_expensetracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.anmp_expensetracker.databinding.BudgetListItemBinding
import com.ubaya.anmp_expensetracker.model.Budget

class BudgetListAdapter(val budgetList:ArrayList<Budget>):RecyclerView.Adapter<BudgetListAdapter.BudgetViewHolder>() {
    class BudgetViewHolder(var binding:BudgetListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = BudgetListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return budgetList.size
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.binding.budget = budgetList[position]

        holder.binding.imgEdit.setOnClickListener {
            val action = BudgetFragmentDirections.actionEditBudgetFragment(budgetList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }


    }

    fun updateBudgetList(newBudgetList:List<Budget>){
        budgetList.clear()
        budgetList.addAll(newBudgetList)
        notifyDataSetChanged()
    }



}