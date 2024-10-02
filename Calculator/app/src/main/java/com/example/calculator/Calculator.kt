package com.example.calculator


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var display by remember { mutableStateOf("0") }
    var currentValue by remember { mutableStateOf<Double>(0.0) }
    var previousValue by remember { mutableStateOf<Double>(0.0) }
    var operator by remember { mutableStateOf<String?>(null) }

    fun buttonNum(buttext: String) {
        when (buttext) {
            "CE" -> {
                display = "0"
                currentValue = 0.0
            }
            "C" -> {
                display = "0"
                currentValue = 0.0
                previousValue = 0.0
                operator = null
            }
            "%" -> {
                currentValue = display.toDouble() / 100
                display = currentValue.toString()
            }
            "÷", "x", "-", "+" -> {
                operator = buttext
                currentValue = display.toDouble()
                display = "0" // Reset display for the second number
                previousValue = currentValue
                currentValue = 0.0 // Reset currentValue for the second number
            }
            "=" -> {
                if (operator != null && previousValue != 0.0) {
                    currentValue = display.toDouble()
                    val result = when (operator) {
                        "÷" -> previousValue / currentValue
                        "x" -> previousValue * currentValue
                        "-" -> previousValue - currentValue
                        "+" -> previousValue + currentValue
                        else -> 0.0
                    }
                    display = result.toString()
                    previousValue = result
                    currentValue = result // Update currentValue with the result
                    operator = null
                }
            }
            else -> { // Number buttons
                if (display == "0" ) {
                    display = buttext // Start a new number after operator
                } else {
                    display += buttext // Append to existing number
                }
                currentValue = display.toDouble()
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = modifier.fillMaxSize()
        ) {
            textBox(text = display)
            Row(modifier = Modifier.fillMaxWidth(), // Fill width for horizontal arrangement
                horizontalArrangement = Arrangement.SpaceAround // Distribute buttons evenly
            ) {
                CalculatorButton(text = "CE", modifier = Modifier.weight(1f), onClick = { buttonNum("CE") })
                CalculatorButton(text = "C", modifier = Modifier.weight(1f), onClick = { buttonNum("C") })
                CalculatorButton(text = "%", modifier = Modifier.weight(1f), onClick = { buttonNum("%") })
                CalculatorButton(text = "÷", modifier = Modifier.weight(1f), onClick = { buttonNum("÷") })
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CalculatorButton(text = "7", modifier = Modifier.weight(1f), onClick = { buttonNum("7") })
                CalculatorButton(text = "8", modifier = Modifier.weight(1f), onClick = { buttonNum("8")})
                CalculatorButton(text = "9", modifier = Modifier.weight(1f), onClick = { buttonNum("9") })
                CalculatorButton(text = "x", modifier = Modifier.weight(1f), onClick = { buttonNum("x") })
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CalculatorButton(text = "4", modifier = Modifier.weight(1f), onClick = { buttonNum("4") })
                CalculatorButton(text = "5", modifier = Modifier.weight(1f), onClick = { buttonNum("5") })
                CalculatorButton(text = "6", modifier = Modifier.weight(1f), onClick = { buttonNum("6") })
                CalculatorButton(text = "-", modifier = Modifier.weight(1f), onClick = {buttonNum("-") })
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CalculatorButton(text = "1", modifier = Modifier.weight(1f), onClick = { buttonNum("1") })
                CalculatorButton(text = "2", modifier = Modifier.weight(1f), onClick = { buttonNum("2") })
                CalculatorButton(text = "3", modifier = Modifier.weight(1f), onClick = { buttonNum("3") })
                CalculatorButton(text = "+", modifier = Modifier.weight(1f), onClick = { buttonNum("+") })
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    onClick = { buttonNum("0") }) {
                    Text(text = "0", fontSize = 30.sp)
                }
                CalculatorButton(text = ".", modifier = Modifier.weight(1f), onClick = { buttonNum(".") })
                CalculatorButton(text = "=", modifier = Modifier.weight(1f), onClick = { buttonNum("=") })
            }
        }
    }
}

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f)
    ) {
        Text(text = text, fontSize = 30.sp)
    }
}

@Composable
fun textBox(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 150.sp,
        textAlign = TextAlign.Right,
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp)
    )

}



@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorTheme {
        CalculatorScreen()
    }
}
