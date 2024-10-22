package com.example.calculator


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var textStyle = remember { mutableStateOf(TextStyle(fontSize = 100.sp, color = Color.White)) }
    var display by remember { mutableStateOf("0") }
    var operand by remember { mutableDoubleStateOf(0.0) }
    var operator by remember { mutableStateOf("") }
    var userIsEnteringNums by remember { mutableStateOf(true) }

    fun buttonFunction(buttext: String) {
        when (buttext) {
            "CE" -> {
                textStyle.value = textStyle.value.copy(fontSize = 100.sp)
                display = "0"
                userIsEnteringNums = true
            }
            "C" -> {
                textStyle.value = textStyle.value.copy(fontSize = 100.sp)
                display = "0"
                operand = 0.0
                operator = ""
                userIsEnteringNums = true
            }
            "%" -> {
                operand = display.toDouble() / 100
                display = operand.toString()
                userIsEnteringNums = false
            }
            "÷", "x", "-", "+", "=" -> {
                if (operator.isNotEmpty()) {
                    val currentValue = display.toDouble()
                    when (operator) {
                        "÷" -> operand /= currentValue
                        "x" -> operand *= currentValue
                        "-" -> operand -= currentValue
                        "+" -> operand += currentValue
                    }
                    if (operand % 1 == 0.0) {
                        display = operand.toInt().toString() // Display result as integer if no fractional part
                    } else {
                        display = operand.toString()
                    }
                    operator = buttext // Update operator for the next operation
                }
                operand = display.toDouble()
                operator = buttext
                userIsEnteringNums = false
                textStyle.value = textStyle.value.copy(fontSize = 100.sp)
            }
            "." -> {
                if (!display.contains(".")) {
                    display += "."
                    userIsEnteringNums = true
                }
            }
            else -> { // Number buttons
                if (userIsEnteringNums) {
                    if (display == "0") {
                        display = buttext // Start new number if display is 0
                    } else {
                        display += buttext // Append to existing number
                    }
                } else {
                    display = buttext
                }
                userIsEnteringNums = true
            }
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
            verticalArrangement = Arrangement.spacedBy(0.dp),
            modifier = modifier.fillMaxSize()
        ) {
            Row(modifier=Modifier.weight(2f)){
                textBox(text = display,textStyle = textStyle)

            }
            Row(modifier = Modifier.fillMaxWidth().weight(1f), // Fill width for horizontal arrangement
                horizontalArrangement = Arrangement.SpaceAround // Distribute buttons evenly
            ) {
                CalculatorButton(text = "CE", modifier = Modifier.weight(1f), onClick = { buttonFunction("CE") })
                CalculatorButton(text = "C", modifier = Modifier.weight(1f), onClick = { buttonFunction("C") })
                CalculatorButton(text = "%", modifier = Modifier.weight(1f), onClick = { buttonFunction("%") })
                CalculatorButton(text = "÷", modifier = Modifier.weight(1f), onClick = { buttonFunction("÷") })
            }
            Row(modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CalculatorButton(text = "7", modifier = Modifier.weight(1f), onClick = { buttonFunction("7") })
                CalculatorButton(text = "8", modifier = Modifier.weight(1f), onClick = { buttonFunction("8")})
                CalculatorButton(text = "9", modifier = Modifier.weight(1f), onClick = { buttonFunction("9") })
                CalculatorButton(text = "x", modifier = Modifier.weight(1f), onClick = { buttonFunction("x") })
            }
            Row(modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CalculatorButton(text = "4", modifier = Modifier.weight(1f), onClick = { buttonFunction("4") })
                CalculatorButton(text = "5", modifier = Modifier.weight(1f), onClick = { buttonFunction("5") })
                CalculatorButton(text = "6", modifier = Modifier.weight(1f), onClick = { buttonFunction("6") })
                CalculatorButton(text = "-", modifier = Modifier.weight(1f), onClick = {buttonFunction("-") })
            }
            Row(modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CalculatorButton(text = "1", modifier = Modifier.weight(1f), onClick = { buttonFunction("1") })
                CalculatorButton(text = "2", modifier = Modifier.weight(1f), onClick = { buttonFunction("2") })
                CalculatorButton(text = "3", modifier = Modifier.weight(1f), onClick = { buttonFunction("3") })
                CalculatorButton(text = "+", modifier = Modifier.weight(1f), onClick = { buttonFunction("+") })
            }
            Row(
                modifier = Modifier.fillMaxSize().weight(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { buttonFunction("0") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(2f)
                        .aspectRatio(2f)
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF415BAA), contentColor = Color.White)
                ) {
                    Text(text = "0", fontSize = 25.sp)
                }
                CalculatorButton(text = ".", modifier = Modifier.weight(1f), onClick = { buttonFunction(".") })
                CalculatorButton(text = "=", modifier = Modifier.weight(1f), onClick = { buttonFunction("=") })
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
    val isOperator = text !in "123456789."
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f),
        colors = ButtonDefaults.buttonColors( // Set button colors
            containerColor = if (isOperator) Color(0xFF3D4355) else Color(0xFF415BAA),
            contentColor = Color.White
        )

    ) {
        Text(text = text, fontSize = 25.sp)
    }
}

@Composable
fun textBox(text: String, textStyle: MutableState<TextStyle>, modifier: Modifier = Modifier) {
    var check by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Black)
            .border(1.dp, Color.Gray)
            .height(170.dp),
        contentAlignment = Alignment.CenterEnd // Align to right and center vertically
    ) {
        Text(
            text = text,
            modifier = Modifier.drawWithContent { if(check) drawContent() },
            style = textStyle.value,
            softWrap = false,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.didOverflowWidth || textLayoutResult.didOverflowHeight) {
                    check = false
                    textStyle.value = textStyle.value.copy(fontSize = textStyle.value.fontSize * 0.9)
                } else {
                    check = true
                }
            }
        )

    }
}



@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorTheme {
        CalculatorScreen()
    }
}
