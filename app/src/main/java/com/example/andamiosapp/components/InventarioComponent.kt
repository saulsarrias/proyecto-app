package com.example.andamiosapp.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.andamiosapp.models.InventarioCantidad

import com.example.andamiosapp.models.InventarioResponse
import com.example.andamiosapp.services.updateCantidad
import com.example.andamiosapp.viewmodels.ManagerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    titulo: String,
    onSearchTextChanged: (String) -> Unit
){
    var searchText by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            Text(
                text = titulo,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = {

            IconButton(
                onClick = {  }
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Buscar", tint = Color.White)
            }

            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    onSearchTextChanged(it)
                },
                placeholder = { Text("Buscar material") },
                trailingIcon = {
                    IconButton(
                        onClick = { searchText = ""}
                    ) {
                        Icon(Icons.Filled.Clear, contentDescription = "Borrar")
                    }
                }
            )
        }
    )
}

@Composable
fun CardMaterial(
    context: Context,
    material: InventarioResponse,
    viewModel: ManagerViewModel
) {
    var cantidad by remember { mutableStateOf(material.cantidad) }
    val showDialog = remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { showDialog.value = true },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
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
                    text = "Material",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Cantidad: $cantidad",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = material.nombre,
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = material.descripcion,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalContentColor.current.copy()
                )
            )
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
                        viewModel.borrarMaterial(context, viewModel, material.id.toString())
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

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Editar Cantidad") },
            text = {
                TextField(
                    value = cantidad.toString(),
                    onValueChange = {
                        cantidad = it.toIntOrNull() ?: cantidad
                    },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                )

            },
            confirmButton = {
                Button(
                    onClick = {
                            updateCantidad(material.id.toString(), newCantidad = InventarioCantidad(cantidad), context)
                            showDialog.value = false

                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
