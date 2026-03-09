package com.example.calculador11.feature

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculador11.R
import com.example.calculador11.feature.componet.calcButton
import com.example.calculador11.feature.componet.calculatorButtons
import com.example.calculador11.model.CalculatorButtonType
import com.example.calculador11.ui.theme.Calculador11Theme
import com.example.calculador11.ui.theme.LocalTheme
import com.example.calculador11.viewmodel.AppViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CalculadorContentScreen() {
    val viewModel: AppViewModel = viewModel()
    val history by viewModel.history.collectAsState()
    // Coleta o texto formatado como State
    val formattedDisplayText by viewModel.formattedDisplayText.collectAsState()

    var showHistory by remember { mutableStateOf(false) }
    val historyOffset by animateDpAsState(
        targetValue = if (showHistory) 0.dp else (-800).dp,
        animationSpec = tween(durationMillis = 400), label = "slide"
    )

    val darkModeEnabled by LocalTheme.current.darkMode.collectAsState()
    val textColor = if (darkModeEnabled) Color(0xffffffff) else Color(0xff212121)
    val themeViewModel = LocalTheme.current

    val scrollState = rememberScrollState()
    LaunchedEffect(viewModel.displayText) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.secondary) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val availableHeight = maxHeight
            val gridHeight = availableHeight * 0.6f
            val topHeight = availableHeight * 0.4f

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    // Parte superior
                    Column(
                        modifier = Modifier
                            .height(topHeight)
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(Modifier.height(25.dp))

                        Text(
                            text = "\u2B07 Histórico",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showHistory = true }
                                .padding(top = 8.dp),
                            fontSize = 16.sp,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(155.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(bottom = 3.dp, end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formattedDisplayText, // usa o valor coletado do StateFlow
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.padding(end = 8.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Clip
                            )
                            if (formattedDisplayText.length > 15) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = "Mais dígitos",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    // Teclado
                    LazyVerticalGrid(
                        modifier = Modifier
                            .height(gridHeight)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(10.dp),
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(calculatorButtons) { button ->
                            calcButton(
                                button = button,
                                textColor = textColor,
                                onClick = {
                                    when (button.type) {
                                        CalculatorButtonType.Normal -> viewModel.inputDigit(button.text ?: "")
                                        CalculatorButtonType.Action -> {
                                            if (button.text == "=") viewModel.calculateResult()
                                            else button.text?.let { viewModel.inputDigit(it) }
                                        }
                                        CalculatorButtonType.Reset -> viewModel.resetAll()
                                        CalculatorButtonType.Backspace -> viewModel.backspace()
                                    }
                                }
                            )
                        }
                    }
                }

                // Painel de histórico
                if (showHistory) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray.copy(alpha = 0.95f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "\u2B06 Fechar histórico",
                                color = Color.White,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .clickable { showHistory = false }
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            if (history.isEmpty()) {
                                Text(
                                    text = "Nenhum cálculo ainda.",
                                    color = Color.LightGray,
                                    fontSize = 18.sp
                                )
                            } else {
                                history.forEach { entry ->
                                    Text(
                                        text = entry,
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Botões de tema
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 35.dp, end = 8.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 15.dp, vertical = 8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp).clickable { themeViewModel.toggleTheme() },
                        painter = painterResource(id = R.drawable.ic_nightmode),
                        contentDescription = null,
                        tint = if (darkModeEnabled) Color.Gray.copy(alpha = .5f) else Color.Gray
                    )
                    Icon(
                        modifier = Modifier.size(20.dp).clickable { themeViewModel.toggleTheme() },
                        painter = painterResource(id = R.drawable.ic_darkmode),
                        contentDescription = null,
                        tint = if (!darkModeEnabled) Color.LightGray.copy(alpha = .5f) else Color.Gray
                    )
                    Text(
                        text = "$",
                        modifier = Modifier
                            .clickable {}
                            .size(20.dp),
                        fontSize = 20.sp,
                        color = if (!darkModeEnabled) Color.Green.copy(alpha = .5f) else Color.Green,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculadorContentScreen() {
    Calculador11Theme {
        CalculadorContentScreen()
    }
}