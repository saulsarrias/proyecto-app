package com.example.andamiosapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.andamiosapp.ui.theme.AndamiosAppTheme
import com.example.andamiosapp.navigation.AppNavigation
import com.example.andamiosapp.viewmodels.ManagerViewModel
import com.example.andamiosapp.viewmodels.ManagerViewModelFactory


class MainActivity : ComponentActivity() {
    val viewModel: ManagerViewModel by lazy {
        ViewModelProvider(
            this,
            ManagerViewModelFactory()
        )[ManagerViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndamiosAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel)
                }
            }
        }
    }
}
