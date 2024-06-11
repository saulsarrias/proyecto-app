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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.andamiosapp.components.BottomBar
import com.example.andamiosapp.components.CardMaterial
import com.example.andamiosapp.components.FloatingButton
import com.example.andamiosapp.components.MainTopBar

import com.example.andamiosapp.navigation.AppScreens
import com.example.andamiosapp.viewmodels.ManagerViewModel

@Composable
fun InventarioScreen(navController: NavController, viewModel: ManagerViewModel){
    Scaffold (
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingButton(
                onClick = { navController.navigate(AppScreens.NewMaterialScreen.route) },
                contentDescription = "Añadir Material"
            )
        }
    ) {
        ViewContent(
            navController = navController,
            viewModel = viewModel,
            pad = it,
            context = LocalContext.current
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ViewContent(
    navController: NavController,
    viewModel: ManagerViewModel,
    pad: PaddingValues,
    context: Context
){
    val materiales = viewModel.isInventarioList.value.inventarioList

    var searchText by remember { mutableStateOf("") }

    val filteredMateriales by remember(searchText, materiales) {
        derivedStateOf {
            if (searchText.isEmpty()) {
                materiales
            } else {
                materiales.filter { material ->
                    material.nombre.contains(searchText, ignoreCase = true) ||
                            material.descripcion.contains(searchText, ignoreCase = true)
                }
            }
        }
    }


    Column {
        MainTopBar(titulo = "", onSearchTextChanged = { newText ->
            searchText = newText
        })

        LazyColumn(modifier = Modifier.padding(pad)) {
            items(filteredMateriales) { material ->
                CardMaterial(
                    material = material,
                    context = context,
                    viewModel = viewModel
                )
            }
        }
    }
}
