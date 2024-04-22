package com.example.andamiosapp.navigation

sealed class AppScreens (val route: String) {
    object LoginScreen: AppScreens("login")
    object InventarioScreen: AppScreens("inventario")
    object NewMaterialScreen: AppScreens("newmaterial")
    object ParteTrabajoScreen: AppScreens("partes")
    object NewParteScreen: AppScreens("newparte")
    object TareaScreen: AppScreens("tareas")
    object NewTareaScreen: AppScreens("newtarea")

}