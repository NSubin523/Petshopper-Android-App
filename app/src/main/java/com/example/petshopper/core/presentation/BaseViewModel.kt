package com.example.petshopper.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base ViewModel that provides common state management functionality
 * @param STATE The UI state type
 * @param ACTION The action/intent type that the UI can trigger
 */
abstract class BaseViewModel<STATE, ACTION>(
    initialState: STATE
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    /**
     * Single entry point for all UI actions/intents
     */
    abstract fun onAction(action: ACTION)

    /**
     * Thread-safe state update function
     */
    protected fun updateState(transform: (STATE) -> STATE) {
        _state.update(transform)
    }

    /**
     * Direct state update (use sparingly, prefer updateState)
     */
    protected fun setState(newState: STATE) {
        _state.value = newState
    }

    /**
     * Get current state value
     */
    protected val currentState: STATE
        get() = _state.value

    /**
     * Launch coroutine in viewModelScope with optional error handling
     */
    protected fun launchAsync(
        onError: ((Throwable) -> Unit)? = null,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                onError?.invoke(e) ?: throw e
            }
        }
    }
}
