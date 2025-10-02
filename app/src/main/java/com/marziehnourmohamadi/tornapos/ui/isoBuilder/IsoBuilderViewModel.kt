package com.marziehnourmohamadi.tornapos.ui.isoBuilder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.marziehnourmohamadi.tornapos.R
import com.marziehnourmohamadi.tornapos.data.IsoRepository
import com.marziehnourmohamadi.tornapos.data.model.IsoMessage
import com.marziehnourmohamadi.tornapos.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IsoBuilderViewModel(application: Application) : AndroidViewModel(application){

    private val repository = IsoRepository(application.applicationContext)

    private val messageHistory = mutableListOf<IsoMessage>()

    private val _uiState = MutableStateFlow<UiState<List<IsoMessage>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<IsoMessage>>> = _uiState

    fun buildMessage(cardNumber: String, amount: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val message = repository.buildMessage(cardNumber, amount).getOrThrow()
                messageHistory.add(0, message)
                _uiState.value = UiState.Success(messageHistory.toList())
            } catch (e: Exception) {
                _uiState.value = UiState.Error(application.getString(R.string.err_build_iso, e.message))
            }
        }
    }
}