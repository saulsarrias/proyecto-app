package com.example.andamiosapp.screens


import android.annotation.SuppressLint
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.andamiosapp.R
import com.example.andamiosapp.SharedPreferences.SharesPreferencesApplication
import com.example.andamiosapp.models.ParteTrabajoRequest
import com.example.andamiosapp.navigation.AppScreens
import com.example.andamiosapp.viewmodels.ManagerViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewParteScreen(
    viewModel: ManagerViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var id_user by remember { mutableStateOf(0) }
    var id_obra by remember { mutableStateOf(0) }
    var fecha_parte by remember { mutableStateOf("") }
    id_user = SharesPreferencesApplication.preferences.getData("idEmpleado", "0").toIntOrNull()!!
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        var fecha = "No hay fecha seleccionada"
                        if(datePickerState.selectedDateMillis != null){
                            fecha = convertLongToTime(datePickerState.selectedDateMillis!!)
                        }
                        fecha_parte = fecha
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

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
                value = fecha_parte,
                onValueChange = {},
                label = { Text("Fecha del Parte (dd-MM-aaaa)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = {}),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_edit_calendar_24), // Reemplaza con tu icono de calendario
                        contentDescription = "Seleccionar Fecha",
                        modifier = Modifier.clickable { showDatePicker = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val formattedDate = formatDateToServerFormat(fecha_parte)
                    viewModel.guardarParte(context,  ParteTrabajoRequest(id_user, id_obra, formattedDate))
                    Log.d("TAG", "fecha $formattedDate")
                    navController.navigate(AppScreens.ParteTrabajoScreen.route)
                },
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

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd-MM-yyyy")
    return format.format(date)
}

fun formatDateToServerFormat(date: String): String {
    val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val parsedDate = inputFormat.parse(date)
    return outputFormat.format(parsedDate!!)
}