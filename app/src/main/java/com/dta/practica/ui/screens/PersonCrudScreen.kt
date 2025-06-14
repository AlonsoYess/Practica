package com.dta.practica.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.dta.practica.ui.models.Person
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun PersonCrudScreen(onBack: () -> Unit) {
    var persons by remember { mutableStateOf(listOf<Person>()) }
    var editPerson by remember { mutableStateOf<Person?>(null) }
    var showForm by remember { mutableStateOf(false) }
    val db = Firebase.firestore

    // Escuchar cambios en tiempo real
    LaunchedEffect(Unit) {
        db.collection("people")
            .addSnapshotListener { snap, _ ->
                persons = snap?.documents
                    ?.mapNotNull { doc ->
                        doc.toObject(Person::class.java)?.copy(id = doc.id)
                    } ?: emptyList()
            }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = onBack) { Text("← Menú") }
            Spacer(Modifier.height(16.dp))

            if (showForm) {
                PersonForm(
                    person = editPerson,
                    onSave = { p ->
                        val coll = db.collection("people")
                        if (p.id.isBlank()) coll.add(p) else coll.document(p.id).set(p)
                        showForm = false; editPerson = null
                    },
                    onCancel = {
                        showForm = false; editPerson = null
                    }
                )
            } else {
                PersonList(
                    persons = persons,
                    onAddNew = {
                        editPerson = null; showForm = true
                    },
                    onEdit = { p ->
                        editPerson = p; showForm = true
                    },
                    onDelete = { id ->
                        db.collection("people").document(id).delete()
                    }
                )
            }
        }
    }
}

@Composable
private fun PersonForm(
    person: Person?,
    onSave: (Person) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(person?.name ?: "") }
    var surname by remember { mutableStateOf(person?.surname ?: "") }
    var ageText by remember { mutableStateOf(person?.age?.toString() ?: "") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = if (person == null) "Nueva Persona" else "Editar Persona",
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = ageText,
            onValueChange = { ageText = it.filter { c -> c.isDigit() } },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                val age = ageText.toIntOrNull() ?: 0
                onSave(Person(person?.id ?: "", name, surname, age))
            }) { Text("Guardar") }
            TextButton(onClick = onCancel) { Text("Cancelar") }
        }
    }
}

@Composable
private fun PersonList(
    persons: List<Person>,
    onAddNew: () -> Unit,
    onEdit: (Person) -> Unit,
    onDelete: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Listado de Personas", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = onAddNew) { Text("+ Añadir") }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(persons) { p ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("${p.name} ${p.surname}", style = MaterialTheme.typography.titleMedium)
                            Text("Edad: ${p.age}")
                        }
                        Row {
                            TextButton(onClick = { onEdit(p) })   { Text("Editar") }
                            Spacer(Modifier.width(8.dp))
                            TextButton(onClick = { onDelete(p.id) }) { Text("Eliminar") }
                        }
                    }
                }
            }
        }
    }
}