package com.dta.practica.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat

@Composable
fun CurrencyConverterScreen(onBack: ()->Unit) {
    var amount by remember { mutableStateOf("") }
    var direction by remember { mutableStateOf("USD → PEN") }
    var result by remember { mutableStateOf<String?>(null) }
    val rate = 3.80

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it.filter { c->c.isDigit() || c=='.' } },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Row {
            RadioButton(
                selected = direction=="USD → PEN",
                onClick = { direction="USD → PEN" }
            ); Spacer(Modifier.width(8.dp)); Text("USD → PEN")
        }
        Row {
            RadioButton(
                selected = direction=="PEN → USD",
                onClick = { direction="PEN → USD" }
            ); Spacer(Modifier.width(8.dp)); Text("PEN → USD")
        }

        Button(onClick = {
            val x = amount.toDoubleOrNull()
            result = if (x==null) {
                "Ingresa un monto válido"
            } else {
                val converted = if (direction=="USD → PEN") x * rate else x / rate
                val fmt = DecimalFormat("#,##0.00")
                (if(direction=="USD → PEN") "S/ " else "$ ") + fmt.format(converted)
            }
        }) {
            Text("Convertir")
        }

        result?.let { Text("Resultado: $it") }

        Spacer(Modifier.height(24.dp))
        Button(onClick = onBack) { Text("Regresar") }
    }
}