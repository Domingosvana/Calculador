package com.example.calculador11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.calculador11.feature.CalculadorContentScreen
import com.example.calculador11.ui.theme.Calculador11Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculador11Theme {
                Box(modifier = Modifier.safeDrawingPadding()) {
                    CalculadorContentScreen()
                }

            }
        }
    }
}
