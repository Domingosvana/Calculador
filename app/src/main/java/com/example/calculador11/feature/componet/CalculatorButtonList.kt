package com.example.calculador11.feature.componet

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.calculador11.model.CalculatorButton
import com.example.calculador11.model.CalculatorButtonType

val calculatorButtons: SnapshotStateList<CalculatorButton> = mutableStateListOf(
    CalculatorButton("AC", CalculatorButtonType.Reset),
    CalculatorButton(icon = Icons.Default.ArrowBack, type = CalculatorButtonType.Backspace),
    CalculatorButton("%", CalculatorButtonType.Action),
    CalculatorButton("÷", CalculatorButtonType.Action),

    CalculatorButton("7", CalculatorButtonType.Normal),
    CalculatorButton("8", CalculatorButtonType.Normal),
    CalculatorButton("9", CalculatorButtonType.Normal),
    CalculatorButton("x", CalculatorButtonType.Action),

    CalculatorButton("4", CalculatorButtonType.Normal),
    CalculatorButton("5", CalculatorButtonType.Normal),
    CalculatorButton("6", CalculatorButtonType.Normal),
    CalculatorButton("-", CalculatorButtonType.Action),

    CalculatorButton("1", CalculatorButtonType.Normal),
    CalculatorButton("2", CalculatorButtonType.Normal),
    CalculatorButton("3", CalculatorButtonType.Normal),
    CalculatorButton("+", CalculatorButtonType.Action),

    CalculatorButton(icon = Icons.Outlined.Refresh, type = CalculatorButtonType.Reset),
    CalculatorButton("0", CalculatorButtonType.Normal),
    CalculatorButton(".", CalculatorButtonType.Normal),
    CalculatorButton("=", CalculatorButtonType.Action),
)