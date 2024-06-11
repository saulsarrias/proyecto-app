package com.example.andamiosapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andamiosapp.models.InventarioRequest
import com.example.andamiosapp.models.InventarioResponse
import com.example.andamiosapp.models.ParteTrabajoRequest
import com.example.andamiosapp.models.ParteTrabajoResponse
import com.example.andamiosapp.models.TareaRequest
import com.example.andamiosapp.models.TareaResponse
import com.example.andamiosapp.services.deleteMaterial
import com.example.andamiosapp.services.deleteParte
import com.example.andamiosapp.services.deleteTarea
import com.example.andamiosapp.services.getInventario
import com.example.andamiosapp.services.getPartesUsuario
import com.example.andamiosapp.services.getTareasParte
import com.example.andamiosapp.services.login
import com.example.andamiosapp.services.saveMaterial
import com.example.andamiosapp.services.saveParte
import com.example.andamiosapp.services.saveTarea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagerViewModel() : ViewModel() {

    private val _isToken = MutableStateFlow(StateLogin())
    val isToken = _isToken.asStateFlow()
    private val _isInventarioList = MutableStateFlow(InventarioList())
    val isInventarioList: StateFlow<InventarioList> = _isInventarioList.asStateFlow()
    private val _isParteTrabajoList = MutableStateFlow(ParteTrabajoList())
    val isParteTrabajoList: StateFlow<ParteTrabajoList> = _isParteTrabajoList.asStateFlow()
    private val _isTareaList = MutableStateFlow(TareaList())
    val isTareaList: StateFlow<TareaList> = _isTareaList.asStateFlow()

    fun obtenerMateriales(context: Context, viewModel: ManagerViewModel){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                getInventario(context, viewModel)
            }
        }
    }

    fun inicio_sesion(email: String, password: String, context: Context, viewModel: ManagerViewModel){
        viewModelScope.launch  (Dispatchers.IO) {
            login(email, password, context, viewModel)
        }
    }

    fun setStateLogin(state:Boolean){
        _isToken.update { it.copy(isToken = state) }
    }

    fun updateParteTrabajoList(parteTrabajoList: List<ParteTrabajoResponse>) {
        _isParteTrabajoList.update { it.copy(parteTrabajoList = parteTrabajoList, isParteTrabajoList = true) }
    }
    fun updateInventarioList(inventarioList: List<InventarioResponse>) {
        _isInventarioList.update { it.copy(inventarioList = inventarioList, isInventarioList = true) }
    }

    fun updateTareaList(tareaList: List<TareaResponse>) {
        _isTareaList.update { it.copy(tareaList = tareaList, isTareaList = true) }
    }

    fun obtenerTareasParte(context: Context, viewModel: ManagerViewModel){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                getTareasParte(context, viewModel)
            }
        }
    }

    fun obtenerPartesUsuario(context: Context, viewModel: ManagerViewModel) {
        viewModelScope.launch {
            getPartesUsuario(context, viewModel)
        }
    }
    fun addParteToList(parte: ParteTrabajoResponse) {
        _isParteTrabajoList.update { currentList ->
            val updatedList = currentList.parteTrabajoList.toMutableList()
            updatedList.add(parte)
            currentList.copy(parteTrabajoList = updatedList)
        }
    }

    fun removeParteFromList(parteId: String) {
        _isParteTrabajoList.update { currentList ->
            val updatedList = currentList.parteTrabajoList.filterNot { it?.id.toString() == parteId }
            currentList.copy(parteTrabajoList = updatedList)
        }
    }

    fun addMaterialToList(material: InventarioResponse) {
        _isInventarioList.update { currentList ->
            val updatedList = currentList.inventarioList.toMutableList()
            updatedList.add(material)
            currentList.copy(inventarioList = updatedList)
        }
    }

    fun removeMaterialFromList(materialId: String) {
        _isInventarioList.update { currentList ->
            val updatedList = currentList.inventarioList.filterNot { it?.id.toString() == materialId }
            currentList.copy(inventarioList = updatedList)
        }
    }

    fun addTareaToList(tarea: TareaResponse) {
        _isTareaList.update { currentList ->
            val updatedList = currentList.tareaList.toMutableList()
            updatedList.add(tarea)
            currentList.copy(tareaList = updatedList)
        }
    }

    fun removeTareaFromList(tareaId: String) {
        _isTareaList.update { currentList ->
            val updatedList = currentList.tareaList.filterNot { it?.id.toString() == tareaId }
            currentList.copy(tareaList = updatedList)
        }
    }


    fun guardarParte(context: Context, parte: ParteTrabajoRequest) {
        viewModelScope.launch {
            saveParte(context, this@ManagerViewModel, parte)
        }
    }
    fun guardarMaterial(context: Context, viewModel: ManagerViewModel, material: InventarioRequest){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                saveMaterial(context = context, viewModel, material)
            }
        }
    }
    fun guardarTarea(context: Context,viewModel: ManagerViewModel, tarea: TareaRequest){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                saveTarea(context = context, viewModel, tarea)
            }
        }
    }

    fun borrarMaterial(context: Context, viewModel: ManagerViewModel, id: String){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                deleteMaterial(context = context, viewModel, id)
            }
        }
    }
    fun borrarParte(context: Context, viewModel: ManagerViewModel, id: String) {
        viewModelScope.launch {
            deleteParte(context, viewModel, id)
        }
    }
    fun borrarTarea(context: Context, viewModel: ManagerViewModel, id: String){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                deleteTarea(context = context, viewModel, id)
            }
        }
    }


}

data class StateLogin(
    val isToken: Boolean = false
)

data class InventarioList(
    val isInventarioList: Boolean = false,
    var inventarioList: List<InventarioResponse> = emptyList()
)

data class ParteTrabajoList(
    val isParteTrabajoList: Boolean = false,
    var parteTrabajoList: List<ParteTrabajoResponse?> = emptyList()
)

data class TareaList(
    val isTareaList: Boolean = false,
    var tareaList: List<TareaResponse> = emptyList()
)