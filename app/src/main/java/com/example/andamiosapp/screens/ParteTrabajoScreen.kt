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
import com.example.andamiosapp.components.CardParte
import com.example.andamiosapp.components.FloatingButton
import com.example.andamiosapp.components.MainTopBarParte
import com.example.andamiosapp.navigation.AppScreens
import com.example.andamiosapp.viewmodels.ManagerViewModel

@Composable
fun ParteTrabajoScreen(navController: NavController, viewModel: ManagerViewModel){
    Scaffold (
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingButton(
                onClick = { navController.navigate(AppScreens.NewParteScreen.route) },
                contentDescription = "Añadir parte"
            )
        }
    ) {
        ViewContentPartes(
            navController = navController,
            viewModel = viewModel,
            pad = it,
            context = LocalContext.current
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ViewContentPartes(
    navController: NavController,
    viewModel: ManagerViewModel,
    pad: PaddingValues,
    context: Context
){
    val partes = viewModel.isParteTrabajoList.value.parteTrabajoList
    var filteredPartes by remember { mutableStateOf(partes) }


    fun filterPartes(searchText: String) {
        filteredPartes = if (searchText.isEmpty()) {
            partes
        } else {
            partes.filter { parte ->
                parte.fecha_parte.contains(searchText, ignoreCase = true)
            }
        }
    }

    Column {
        MainTopBarParte(titulo = "", onSearchTextChanged = { searchText ->
            filterPartes(searchText)
        })

        LazyColumn(modifier = Modifier.padding(pad)) {
            items(filteredPartes) { parte ->
                CardParte(
                    parte = parte,
                    context = context,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
