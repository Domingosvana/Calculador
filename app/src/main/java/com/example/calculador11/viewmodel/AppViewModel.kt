package com.example.calculador11.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.NumberFormat
import java.util.Locale

class AppViewModel : ViewModel() {
    private val _displayText = MutableStateFlow("0")
    val displayText = _displayText.asStateFlow()

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history = _history.asStateFlow()

    private var shouldStartNewInput = false
    private var lastResult: String? = null
    private var newInputStarted = false

    // Propriedade para UI formatada
    val formattedDisplayText: String
        get() = formatExpressionNumbers(_displayText.value)

    fun inputDigit(digit: String) {
        var input = digit
        // Converte vírgula digitada para ponto interno
        if (input == ",") input = "."

        if (input == ".") {
            // Verifica se o último token já tem ponto decimal
            val tokens = _displayText.value.split(Regex("(?<=[+\\-x÷%])|(?=[+\\-x÷%])"))
            val lastToken = tokens.lastOrNull() ?: ""
            if (lastToken.contains(".")) {
                return // já existe ponto decimal no número atual
            }
        }

        when {
            digit in listOf("+", "-", "x", "÷", "%") && newInputStarted -> {
                _displayText.value += input
                newInputStarted = false
                shouldStartNewInput = false
            }
            digit in listOf("+", "-", "x", "÷", "%") && lastResult != null && !newInputStarted -> {
                _displayText.value = lastResult + input
                shouldStartNewInput = false
                lastResult = null
            }
            shouldStartNewInput || _displayText.value == "0" || _displayText.value == "Erro" -> {
                _displayText.value = input
                shouldStartNewInput = false
                newInputStarted = true
            }
            else -> {
                _displayText.value += input
                newInputStarted = true
            }
        }
    }

    fun calculateResult() {
        try {
            val expression = _displayText.value
                .replace("x", "*")
                .replace("÷", "/")
                .replace("%", "*0.01*")

            val result = ExpressionBuilder(expression)
                .build()
                .evaluate()

            val resultInternal = if (result % 1.0 == 0.0) {
                result.toLong().toString()
            } else {
                "%.10f".format(result).replace(",", ".")
                    .replace("\\.?0+$".toRegex(), "")
            }

            val formattedExpression = formatExpressionNumbers(_displayText.value)
            val formattedResult = formatDisplayNumber(resultInternal)

            _history.update { listOf("$formattedExpression = $formattedResult") + it }
            _displayText.value = resultInternal
            lastResult = resultInternal
            shouldStartNewInput = true
            newInputStarted = false
        } catch (e: Exception) {
            _displayText.value = "Erro"
            shouldStartNewInput = true
            newInputStarted = false
        }
    }

    fun resetAll() {
        _displayText.value = "0"
        shouldStartNewInput = false
        lastResult = null
        newInputStarted = false
    }

    fun clearHistory() {
        _history.update { emptyList() }
    }

    fun backspace() {
        _displayText.value = when {
            _displayText.value.length == 1 -> "0"
            _displayText.value == "Erro" -> "0"
            else -> _displayText.value.dropLast(1)
        }
        if (_displayText.value == "0") {
            shouldStartNewInput = false
            lastResult = null
            newInputStarted = false
        }
    }

    // Funções de formatação
    private fun formatDisplayNumber(internalNumber: String): String {
        return try {
            val number = internalNumber.toDouble()
            val format = NumberFormat.getInstance(Locale("pt", "BR")) as java.text.DecimalFormat
            format.applyPattern("#,##0.###")
            format.format(number)
        } catch (e: Exception) {
            internalNumber
        }
    }

    private fun formatExpressionNumbers(expression: String): String {
        val regex = "\\d+(\\.\\d+)?".toRegex()
        return regex.replace(expression) { matchResult ->
            formatDisplayNumber(matchResult.value)
        }
    }
}