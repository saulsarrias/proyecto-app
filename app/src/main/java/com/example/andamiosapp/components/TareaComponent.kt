package com.example.andamiosapp.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.andamiosapp.R

import com.example.andamiosapp.models.TareaResponse
import com.example.andamiosapp.viewmodels.ManagerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBarTarea(
    titulo: String,
    onSearchTextChanged: (String) -> Unit,
){
    var searchText by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        onSearchTextChanged(it)
                    },
                    placeholder = { Text("Buscar Tarea") },
                    trailingIcon = {
                        IconButton(
                            onClick = { searchText = "" }
                        ) {
                            Icon(Icons.Filled.Clear, contentDescription = "Borrar")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = colorResource(R.color.topBar)
        )
    )
}

@Composable
fun CardTarea(
    context: Context,
    tarea: TareaResponse,
    viewModel: ManagerViewModel

) {

    var showDialogDelete by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {  },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.cardColor))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Empleado: ${tarea.personal_asignado}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Descripcion: "+tarea.descripcion,
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Precio: ${tarea.precio_por_hora}€",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Total Horas: ${tarea.horas_trabajadas}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        IconButton(
            onClick = {
                showDialogDelete = true
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Borrar Material",
                modifier = Modifier.size(24.dp)
            )
        }
    }

    if (showDialogDelete) {
        AlertDialog(
            onDismissRequest = { showDialogDelete = false },
            title = { Text("Confirmación") },
            text = { Text("¿Estás seguro que quieres borrarlo?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialogDelete = false
                        viewModel.borrarTarea(context, viewModel, tarea.id.toString())
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialogDelete = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
