package com.example.andamiosapp.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.andamiosapp.R
import com.example.andamiosapp.navigation.AppScreens

@Composable
fun BottomBar(navController: NavController) {

    BottomAppBar(
        contentColor = Color.White,
        containerColor = colorResource(R.color.topBar)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            NavigationButton(
                onClick = { navController.navigate(route = AppScreens.InventarioScreen.route) },
                selected = false,
                icon = {
                    val drawable = R.drawable.baseline_inventory_24
                    val painter = painterResource(drawable)
                    Text(text = "Inventario")
                    Icon(painter = painter, contentDescription = "Inventario")
                    
                },
                contentColor = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))

            NavigationButton(
                onClick = { navController.navigate(route = AppScreens.ParteTrabajoScreen.route) },
                selected = false,
                icon = {
                    val drawable = R.drawable.baseline_edit_note_24
                    val painter = painterResource(drawable)
                    Text(text = "Partes de Trabajo")
                    Icon(painter = painter, contentDescription = "Partes")
                },
                contentColor = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun NavigationButton(
    onClick: () -> Unit,
    selected: Boolean,
    icon: @Composable () -> Unit,
    contentColor: Color
) {
    TextButton(
        onClick = onClick,
        enabled = true,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor
        )
    ) {
        icon()
    }
}

@Composable
fun FloatingButton(  onClick: () -> Unit,
                     contentDescription: String
) {
    FloatingActionButton(
        onClick = onClick,
        contentColor = Color.White,
        containerColor = colorResource(R.color.floatingButton),
        content = {
            Icon(
                Icons.Filled.Add,
                contentDescription = contentDescription
            )
        }
    )
}
