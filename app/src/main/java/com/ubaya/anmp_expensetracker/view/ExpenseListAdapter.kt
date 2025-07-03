package com.ubaya.anmp_expensetracker.view

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.ubaya.anmp_expensetracker.R
import com.ubaya.anmp_expensetracker.databinding.ExpenseListItemBinding
import com.ubaya.anmp_expensetracker.model.ExpenseWithBudget
import com.ubaya.anmp_expensetracker.util.formatRupiah
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseListAdapter(val expenseList: ArrayList<ExpenseWithBudget>): RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>()  {
    class ExpenseViewHolder(var binding:ExpenseListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseListAdapter.ExpenseViewHolder {
        val binding = ExpenseListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.binding.txtTanggal.text = formatDate(expenseList[position].expense.tanggal)
        holder.binding.chipBudget.text = expenseList[position].budget.name
        holder.binding.txtHarga.text = formatRupiah(expenseList[position].expense.nominal)
//        Log.d("expense", "${formatRupiah(expenseList[position].expense.nominal)}")

        holder.binding.txtHarga.setOnClickListener {
            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.dialog_expense_detail, null)

            val txtDate = dialogView.findViewById<TextView>(R.id.txtDialogDate)
            val txtNote = dialogView.findViewById<TextView>(R.id.txtDialogNote)
            val txtAmount = dialogView.findViewById<TextView>(R.id.txtDialogAmount)
            val chip = dialogView.findViewById<Chip>(R.id.chipDialogBudget)
            val btnClose = dialogView.findViewById<Button>(R.id.btnCloseDialog)

            // Isi data
            txtDate.text = formatDate(expenseList[position].expense.tanggal)
            txtNote.text = expenseList[position].expense.notes
            txtAmount.text = formatRupiah(expenseList[position].expense.nominal)
            chip.text = expenseList[position].budget.name

            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .create()

            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    fun updateExpense(newList:List<ExpenseWithBudget>){
        expenseList.clear()
        expenseList.addAll(newList)
        notifyDataSetChanged()
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("in", "ID"))
        return sdf.format(Date(timestamp))
    }
}