package com.example.andamiosapp.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.andamiosapp.R
import com.example.andamiosapp.SharedPreferences.SharesPreferencesApplication
import com.example.andamiosapp.models.ParteTrabajoResponse
import com.example.andamiosapp.navigation.AppScreens
import com.example.andamiosapp.viewmodels.ManagerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBarParte(
    titulo: String,
    onSearchTextChanged: (String) -> Unit
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
                    placeholder = { Text("Buscar fecha de parte") },
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
fun CardParte(
    context: Context,
    parte: ParteTrabajoResponse,
    navController: NavController,
    viewModel: ManagerViewModel
) {
    val formattedDate = with(LocalContext.current) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = formatter.parse(parte.fecha_parte) as Date
        val outputFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        outputFormatter.format(parsedDate)

    }
    var showDialogDelete by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { SharesPreferencesApplication.preferences.saveData("idParte", parte.id.toString())
                viewModel.obtenerTareasParte(context, viewModel)
                navController.navigate(route = AppScreens.TareaScreen.route)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.cardColor)),
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
                    text = "Obra: ${parte.id_obra}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Fecha : ${formattedDate}",
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
                        viewModel.borrarParte(context, viewModel, parte.id.toString())
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
