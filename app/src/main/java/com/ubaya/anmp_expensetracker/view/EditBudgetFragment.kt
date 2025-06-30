package com.ubaya.anmp_expensetracker.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.anmp_expensetracker.util.formatRupiah
import com.ubaya.anmp_expensetracker.databinding.FragmentAddBudgetBinding
import com.ubaya.anmp_expensetracker.viewmodel.BudgetViewModel

class EditBudgetFragment : Fragment() {
    private lateinit var binding: FragmentAddBudgetBinding
    private lateinit var viewModel: BudgetViewModel
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
        viewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)

        val uuid = EditBudgetFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

        binding.txtBudgetTitle.text = "Edit Budget"
        binding.btnAddBudget.text = "Save Changes"

        binding.btnAddBudget.setOnClickListener {
            // Misalnya di dalam Fragment
            val sharedPref = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val user_id = sharedPref.getString("uuid", "0")

            if (user_id != null) {
                viewModel.update(binding.txtBudgetName.text.toString(), binding.txtNominal.text.toString().toDouble(), user_id.toInt(), uuid )
                Toast.makeText(context, "Budget berhasil diupdate", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(it).popBackStack()
            }else{
                Toast.makeText(context, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show()
            }

        }

        observerViewModel()
    }

    fun observerViewModel(){
        viewModel.detailBudgetLD.observe(viewLifecycleOwner, Observer {
            binding.txtBudgetName.setText(it.name)
            binding.txtNominal.setText(it.nominal.toString())
        })
    }

}