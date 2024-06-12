package com.example.andamiosapp.screens


import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.example.andamiosapp.viewmodels.ParteTrabajoList

@Composable
fun ParteTrabajoScreen(navController: NavController, viewModel: ManagerViewModel) {
    val context = LocalContext.current

    val parteTrabajoListState by viewModel.isParteTrabajoList.collectAsState()

    Scaffold(
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
            context = context,
            parteTrabajoListState = parteTrabajoListState
        )
    }
}

@Composable
fun ViewContentPartes(
    navController: NavController,
    viewModel: ManagerViewModel,
    pad: PaddingValues,
    context: Context,
    parteTrabajoListState: ParteTrabajoList
) {
    val partes = parteTrabajoListState.parteTrabajoList
    var searchText by remember { mutableStateOf("") }

    val filteredPartes by remember(searchText, partes) {
        derivedStateOf {
            if (searchText.isEmpty()) {
                partes
            } else {
                partes.filter { parte ->
                    parte?.fecha_parte?.contains(searchText, ignoreCase = true)!!
                }
            }
        }
    }

    Column {
        MainTopBarParte(titulo = "", onSearchTextChanged = { newText ->
            searchText = newText
        })
            LazyColumn(modifier = Modifier.padding(pad)) {
                items(filteredPartes) { parte ->
                    if (parte != null) {
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
}