package com.example.andamiosapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.andamiosapp.screens.InventarioScreen
import com.example.andamiosapp.screens.LoginScreen
import com.example.andamiosapp.SharedPreferences.SharesPreferencesApplication
import com.example.andamiosapp.screens.NewMaterialScreen
import com.example.andamiosapp.screens.NewParteScreen
import com.example.andamiosapp.screens.NewTareaScreen
import com.example.andamiosapp.screens.ParteTrabajoScreen
import com.example.andamiosapp.screens.TareaScreen

import com.example.andamiosapp.viewmodels.ManagerViewModel

@Composable
fun AppNavigation(viewModel: ManagerViewModel) {

    val isToken = SharesPreferencesApplication.preferences.getData("token","")
    val firstScreen = if(isToken == ""){
        AppScreens.LoginScreen.route
    } else{
        AppScreens.InventarioScreen.route
    }
    val context = LocalContext.current
    val stateLogin by viewModel.isToken.collectAsState()
    val stateInventarioList by viewModel.isInventarioList.collectAsState()
    val statePartesList by viewModel.isParteTrabajoList.collectAsState()
    val stateTareaList by viewModel.isTareaList.collectAsState()


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = firstScreen ){
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController, viewModel)
            if(stateLogin.isToken){
                navController.navigate(route = AppScreens.InventarioScreen.route)
            }
        }
        composable(route = AppScreens.InventarioScreen.route){
            if(stateInventarioList.isInventarioList) {
                InventarioScreen(navController, viewModel)
            } else{
                viewModel.obtenerMateriales(context, viewModel)
            }
        }
        composable(route = AppScreens.ParteTrabajoScreen.route){
            if(statePartesList.isParteTrabajoList) {
                ParteTrabajoScreen(navController = navController, viewModel = viewModel)
            } else{
                viewModel.obtenerPartesUsuario(context, viewModel)
            }
        }
        composable(route = AppScreens.TareaScreen.route){
            if(stateTareaList.isTareaList) {
                TareaScreen(navController = navController, viewModel = viewModel)
            } else{
                viewModel.obtenerTareasParte(context, viewModel)
            }
        }
        composable(route = AppScreens.NewMaterialScreen.route){
            if(stateInventarioList.isInventarioList) {
                NewMaterialScreen(navController = navController, viewModel = viewModel)
            } else{
                viewModel.obtenerMateriales(context, viewModel)
            }
        }
        composable(route = AppScreens.NewParteScreen.route){
            if(stateInventarioList.isInventarioList) {
                NewParteScreen(navController = navController, viewModel = viewModel)
            } else{
                viewModel.obtenerPartesUsuario(context, viewModel)
            }
        }
        composable(route = AppScreens.NewTareaScreen.route){
            if(stateInventarioList.isInventarioList) {
                NewTareaScreen(navController = navController, viewModel = viewModel)
            } else{
                viewModel.obtenerTareasParte(context, viewModel)
            }
        }

    }
}