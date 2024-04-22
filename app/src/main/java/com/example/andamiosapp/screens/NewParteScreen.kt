package com.example.andamiosapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.andamiosapp.models.ParteTrabajoRequest
import com.example.andamiosapp.navigation.AppScreens
import com.example.andamiosapp.viewmodels.ManagerViewModel

@Composable
fun NewParteScreen(
    viewModel: ManagerViewModel,
    navController: NavController
) {
    var context = LocalContext.current
    var id_user by remember { mutableStateOf(0) }
    var id_obra by remember { mutableStateOf(0) }
    var fecha_parte by remember { mutableStateOf("") }


    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = id_user.toString(),
                onValueChange = { id_user = it.toIntOrNull() ?: 0 },
                label = { Text("Id del usuario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = id_obra.toString(),
                onValueChange = { id_obra = it.toIntOrNull() ?: 0 },
                label = { Text("Id de la Obra") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = fecha_parte.toString(),
                onValueChange = {
                    fecha_parte = it
                },
                label = { Text("Fecha del Parte (aaaa-MM-dd)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = {}),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.guardarParte(context = context, viewModel, ParteTrabajoRequest(id_user, id_obra, fecha_parte))
                    navController.navigate(AppScreens.InventarioScreen.route) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Guardar")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Volver")
            }
        }
    }
}