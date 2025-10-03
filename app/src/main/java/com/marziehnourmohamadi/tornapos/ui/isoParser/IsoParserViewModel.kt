package com.marziehnourmohamadi.tornapos.ui.isoParser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.marziehnourmohamadi.tornapos.R
import com.marziehnourmohamadi.tornapos.data.IsoRepository
import com.marziehnourmohamadi.tornapos.data.model.IsoField
import com.marziehnourmohamadi.tornapos.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IsoParserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = IsoRepository(application.applicationContext)

    private val _uiState = MutableStateFlow<UiState<List<IsoField>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<IsoField>>> = _uiState

    fun parseMessage(isoMessage: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val fields = repository.parseMessage(isoMessage).getOrThrow()
                _uiState.value = UiState.Success(fields)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(application.getString(R.string.err_parsing, e.message))
            }
        }
    }
}