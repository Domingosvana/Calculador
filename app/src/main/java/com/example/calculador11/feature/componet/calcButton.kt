package com.example.calculador11.feature.componet

import androidx.compose.material3.Text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculador11.model.CalculatorButton
import com.example.calculador11.model.CalculatorButtonType
import com.example.calculador11.ui.theme.Cyan
import com.example.calculador11.ui.theme.Red


@Composable
fun calcButton(button: CalculatorButton, textColor:Color,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxHeight()
            .aspectRatio(1f)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        val contentColor =
            if (button.type == CalculatorButtonType.Normal)
                textColor
            else if (button.type == CalculatorButtonType.Action)
                Red
            else
                Cyan
        if (button.text != null) {
            Text(
                button.text,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                fontSize = if (button.type == CalculatorButtonType.Action) 25.sp else 20.sp
            )
        } else {
            button.icon?.let {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = it,
                    contentDescription = null,
                    tint = contentColor
                )
            }

        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun CalcButtonPreview() {
//    CalcButton(
//        button = CalculatorButton(
//            text = "5",
//            type = CalculatorButtonType.Normal
//        ),
//        textColor = Color.Black,
//        onClick = {}
//    )
//}










