package com.example.calculador11.model

import androidx.compose.ui.graphics.vector.ImageVector

data class CalculatorButton(
    val text: String? =null,
    val type: CalculatorButtonType,
    val icon: ImageVector? =null,
)
