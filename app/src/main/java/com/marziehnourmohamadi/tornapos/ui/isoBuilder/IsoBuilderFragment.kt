package com.marziehnourmohamadi.tornapos.ui.isoBuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.marziehnourmohamadi.tornapos.databinding.FragmentIsoBuilderBinding
import com.marziehnourmohamadi.tornapos.ui.adapter.IsoHistoryAdapter
import com.marziehnourmohamadi.tornapos.utils.UiState
import kotlinx.coroutines.launch


class IsoBuilderFragment : Fragment() {

    private var _binding: FragmentIsoBuilderBinding? = null
    private val binding get() = _binding!!

   private val viewModel: IsoBuilderViewModel by viewModels()

    private lateinit var adapter: IsoHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIsoBuilderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        collectUiState()
    }


    private fun setupRecyclerView() {
        adapter = IsoHistoryAdapter()
        binding.messagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@IsoBuilderFragment.adapter
        }
    }

    private fun setupListeners() {
        binding.buildButton.setOnClickListener {
            val cardNumber = binding.cardNumberInput.text.toString().trim()
            val amount = binding.amountInput.text.toString().trim()

            val error = validateInputs(cardNumber, amount)
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.buildMessage(cardNumber, amount)
        }
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Idle -> Unit
                    is UiState.Loading -> binding.buildButton.isEnabled = false
                    is UiState.Success -> {
                        binding.buildButton.isEnabled = true
                        adapter.submitList(state.data)
                    }
                    is UiState.Error -> {
                        binding.buildButton.isEnabled = true
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validateInputs(cardNumber: String, amount: String): String? {
        return when {
            cardNumber.isEmpty() || amount.isEmpty() ->
                "لطفا تمام فیلدها را پر کنید"
            cardNumber.length != 16 || !cardNumber.all { it.isDigit() } ->
                "شماره کارت باید 16 رقم عددی باشد"
            !amount.all { it.isDigit() } ->
                "مبلغ باید عدد باشد"
            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}