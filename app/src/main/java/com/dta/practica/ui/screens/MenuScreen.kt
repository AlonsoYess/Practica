package com.dta.practica.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dta.practica.ui.navigation.Screen

@Composable
fun MenuScreen(onNavigate: (route: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onNavigate(Screen.AgeCalc.route) }) {
            Text("Calculadora de Edad Canina")
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = { onNavigate(Screen.Currency.route) }) {
            Text("Conversor de Divisas")
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = { onNavigate(Screen.Catalog.route) }) {
            Text("Catálogo de Productos")
        }
        Spacer(Modifier.height(12.dp))            // ← nuevo
        Button(onClick = { onNavigate(Screen.PersonCrud.route) }) {
            Text("CRUD Personas")
        }
    }
}