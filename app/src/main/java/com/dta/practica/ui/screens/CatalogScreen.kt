package com.dta.practica.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class Product(val name:String, val price:Double, val category:String, val imageUrl:String)

@Composable
fun CatalogScreen(onBack: ()->Unit) {
    // Datos de ejemplo
    val products = remember {
        listOf(
            Product("Laptop XYZ", 2500.0, "Laptop", "https://via.placeholder.com/150"),
            Product("Smartphone ABC", 1200.0, "Smartphone", "https://via.placeholder.com/150"),
            Product("Auriculares", 200.0,  "Accesorio", "https://via.placeholder.com/150"),
        )
    }

    val total = products.sumOf { it.price }
    val fmt = java.text.DecimalFormat("#,##0.00")

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { p ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AsyncImage(
                            model = p.imageUrl,
                            contentDescription = p.name,
                            modifier = Modifier.size(80.dp)
                        )
                        Column {
                            Text(p.name, style = MaterialTheme.typography.titleMedium)
                            Text("S/ ${fmt.format(p.price)}")
                            Text(p.category, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }

        Text(
            text = "Total: S/ ${fmt.format(total)}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Regresar")
        }
    }
}