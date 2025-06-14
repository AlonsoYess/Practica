package com.dta.practica.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp

@Composable
fun DogAgeCalculatorScreen(onBack: () -> Unit) {
    var humanAge by remember { mutableStateOf("") }
    var selectedSize by remember { mutableStateOf(dogSizes.first()) }
    var expanded by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Edad Humana del Perro:")
        OutlinedTextField(
            value = humanAge,
            onValueChange = { humanAge = it.filter { c -> c.isDigit() } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Tamaño:")
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedSize,
                onValueChange = { /* lectura solamente */ },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Filled.ArrowDropUp
                        else
                            Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                dogSizes.forEach { size ->
                    DropdownMenuItem(
                        text = { Text(size) },
                        onClick = {
                            selectedSize = size
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(onClick = {
            val age = humanAge.toIntOrNull()
            result = if (age == null || age <= 0) {
                "Ingresa un número positivo"
            } else {
                val factor = when (selectedSize) {
                    "Pequeño" -> 5
                    "Mediano" -> 6
                    else -> 7
                }
                "Edad perro: ${age * factor} años perro"
            }
        }) {
            Text("Calcular")
        }

        result?.let { Text(it) }

        Spacer(Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Regresar")
        }
    }
}

private val dogSizes = listOf("Pequeño", "Mediano", "Grande")
