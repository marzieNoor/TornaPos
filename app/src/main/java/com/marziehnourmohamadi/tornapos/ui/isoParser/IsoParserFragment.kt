package com.marziehnourmohamadi.tornapos.ui.isoParser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marziehnourmohamadi.tornapos.R
import com.marziehnourmohamadi.tornapos.databinding.FragmentIsoParserBinding
import com.marziehnourmohamadi.tornapos.utils.UiState
import kotlinx.coroutines.launch

class IsoParserFragment : Fragment() {

    private var _binding: FragmentIsoParserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IsoParserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIsoParserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        collectUiState()

    }

    private fun setupListeners() {
        binding.parseButton.setOnClickListener {
            val message = binding.messageInput.text.toString().trim()

            val error = validateInput(message)
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.parseMessage(message)
        }
    }

    private fun validateInput(message: String): String? {
        return when {
            message.isEmpty() -> getString(R.string.validation_iso_8583)
            else -> null
        }
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Idle -> Unit
                    is UiState.Loading -> binding.messageInput.isEnabled = false
                    is UiState.Success -> {
                        val fieldsText = state.data.joinToString("\n\n") { field ->
                            "${field.fieldNumber}: ${field.fieldName}\n${field.value}"
                        }
                        binding.parsedFieldsText.text = fieldsText
                        binding.parsedFieldsText.visibility = View.VISIBLE
                    }

                    is UiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        binding.messageInput.isEnabled = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}