package com.example.andamiosapp.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.andamiosapp.components.BottomBar
import com.example.andamiosapp.components.CardTarea
import com.example.andamiosapp.components.FloatingButton
import com.example.andamiosapp.components.MainTopBarTarea
import com.example.andamiosapp.navigation.AppScreens
import com.example.andamiosapp.viewmodels.ManagerViewModel

@Composable
fun TareaScreen(navController: NavController, viewModel: ManagerViewModel){
    Scaffold (
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingButton(
                onClick = {  navController.navigate(AppScreens.NewTareaScreen.route) },
                contentDescription = "Añadir Tarea"
            )
        }
    ) {
        ViewContentTareas(
            navController = navController,
            viewModel = viewModel,
            pad = it,
            context = LocalContext.current
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ViewContentTareas(
    navController: NavController,
    viewModel: ManagerViewModel,
    pad: PaddingValues,
    context: Context
){
    val tareas = viewModel.isTareaList.value.tareaList
    var filteredTareas by remember { mutableStateOf(tareas) }


    fun filterTareas(searchText: String) {
        filteredTareas = if (searchText.isEmpty()) {
            tareas
        } else {
            tareas.filter { tarea ->
                tarea.personal_asignado.contains(searchText, ignoreCase = true) ||
                        tarea.descripcion.contains(searchText, ignoreCase = true)
            }
        }
    }

    Column {
        MainTopBarTarea(titulo = "", onSearchTextChanged = { searchText ->
            filterTareas(searchText)
        }, navController)

        LazyColumn(modifier = Modifier.padding(pad)) {
            items(filteredTareas) { tarea ->
                CardTarea(
                    tarea = tarea,
                    context = context,
                    viewModel = viewModel
                )
            }
        }
    }
}
