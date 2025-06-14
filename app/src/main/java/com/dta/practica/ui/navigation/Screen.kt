package com.dta.practica.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Menu     : Screen("menu",     "Menú Principal")
    object AgeCalc  : Screen("age_calc", "Calculadora Edad Canina")
    object Currency : Screen("currency", "Conversor de Divisas")
    object Catalog  : Screen("catalog",  "Catálogo de Productos")
    object PersonCrud : Screen("people_crud","CRUD Personas")
}