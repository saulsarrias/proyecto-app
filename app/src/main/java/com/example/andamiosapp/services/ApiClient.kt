package com.example.andamiosapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.andamiosapp.SharedPreferences.SharesPreferencesApplication
import com.example.andamiosapp.models.InventarioCantidad
import com.example.andamiosapp.models.InventarioRequest
import com.example.andamiosapp.models.InventarioResponse
import com.example.andamiosapp.models.LoginRequest
import com.example.andamiosapp.models.LoginResponse
import com.example.andamiosapp.models.ParteTrabajoRequest
import com.example.andamiosapp.models.ParteTrabajoResponse
import com.example.andamiosapp.models.TareaRequest
import com.example.andamiosapp.models.TareaResponse
import com.example.andamiosapp.viewmodels.ManagerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
        }

}

suspend fun login(email: String, password: String, context: Context, viewModel: ManagerViewModel) = withContext(Dispatchers.IO){
    val call: Call<LoginResponse> = ApiClient.apiService.login(LoginRequest(email, password))
    call.enqueue(object: Callback<LoginResponse>{
        override fun onResponse(
            call: Call<LoginResponse>,
            response: Response<LoginResponse>
        ) {
            Log.d("TAG", ""+response.code().toString())
            if (response.isSuccessful) {
                val token = response.body()!!.token
                SharesPreferencesApplication.preferences.saveData("token", "Bearer "+token)
                viewModel.setStateLogin(true)
            } else {
                Toast.makeText(context,"Contraseña o usuario incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            Log.d("TAG", "failure"+t.message)
        }
    })
}

suspend fun getInventario(context: Context, viewModel: ManagerViewModel) = withContext(Dispatchers.IO){
    val token = SharesPreferencesApplication.preferences.getData("token", "")

    val call: Call<List<InventarioResponse>> = ApiClient.apiService.getInventarios(token)
    call.enqueue(object: Callback<List<InventarioResponse>>{
        override fun onResponse(

            call: Call<List<InventarioResponse>>,
            response: Response<List<InventarioResponse>>
        ) {
            Log.d("TAG", "onResponse: ")
            if (response.isSuccessful) {
                viewModel.setStateInventarioList(true)
                viewModel.isInventarioList.value.inventarioList = response.body()!!

            } else {
                Toast.makeText(context,"Error al obtener materiales", Toast.LENGTH_SHORT).show()

            }
        }
        override fun onFailure(call: Call<List<InventarioResponse>>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}

suspend fun getPartesUsuario(context: Context, viewModel: ManagerViewModel) = withContext(Dispatchers.IO){
    val id = SharesPreferencesApplication.preferences.getData("idEmpleado", "2")
    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val call: Call<List<ParteTrabajoResponse>> = ApiClient.apiService.getPartes(token, id)
    call.enqueue(object: Callback<List<ParteTrabajoResponse>>{
        override fun onResponse(
            call: Call<List<ParteTrabajoResponse>>,
            response: Response<List<ParteTrabajoResponse>>
        ) {
            if (response.isSuccessful) {
                viewModel.setStateParteTrabajoList(true)
                viewModel.isParteTrabajoList.value.parteTrabajoList = response.body()!!
            } else {
                Log.e("TAG", "onResponse: " + response.errorBody())
                Toast.makeText(context,"Error al obtener materiales", Toast.LENGTH_SHORT).show()

            }
        }
        override fun onFailure(call: Call<List<ParteTrabajoResponse>>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}

suspend fun getTareasParte(context: Context, viewModel: ManagerViewModel) = withContext(Dispatchers.IO){

    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val idParte = SharesPreferencesApplication.preferences.getData("idParte", "")
    val call: Call<List<TareaResponse>> = ApiClient.apiService.getTareas(token, idParte)
    call.enqueue(object: Callback<List<TareaResponse>>{
        override fun onResponse(
            call: Call<List<TareaResponse>>,
            response: Response<List<TareaResponse>>
        ) {
            if (response.isSuccessful) {
                viewModel.setStateTareaList(true)
                viewModel.isTareaList.value.tareaList = response.body()!!
            } else {
                Log.e("TAG", "onResponse: " + response.errorBody())
                Toast.makeText(context,"Error al obtener tareas", Toast.LENGTH_SHORT).show()

            }
        }
        override fun onFailure(call: Call<List<TareaResponse>>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}

    fun updateCantidad(id:String, newCantidad: InventarioCantidad, context: Context){
    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val call: Call<InventarioResponse> = ApiClient.apiService.updateCantidad(token, id, newCantidad)
    call.enqueue(object: Callback<InventarioResponse>{
        override fun onResponse(

            call: Call<InventarioResponse>,
            response: Response<InventarioResponse>
        ) {
            if (response.isSuccessful) {
                Toast.makeText(context, "Cantidad actualizada correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al mofificar cantidad", Toast.LENGTH_SHORT).show()

            }
        }
        override fun onFailure(call: Call<InventarioResponse>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}

fun saveParte(context: Context, viewModel: ManagerViewModel, parteTrabajoRequest: ParteTrabajoRequest){
    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val call: Call<ParteTrabajoResponse> = ApiClient.apiService.saveParte(token, parteTrabajoRequest)
    call.enqueue(object: Callback<ParteTrabajoResponse>{
        override fun onResponse(
            call: Call<ParteTrabajoResponse>,
            response: Response<ParteTrabajoResponse>
        ) {
            if (response.isSuccessful) {
                viewModel.setStateParteTrabajoList(true)
                Toast.makeText(context, "Parte guardado con exito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al guardar parte", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<ParteTrabajoResponse>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}

fun saveMaterial(context: Context, viewModel: ManagerViewModel, inventario: InventarioRequest){
    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val call: Call<InventarioResponse> = ApiClient.apiService.saveMaterial(token, inventario)
    call.enqueue(object: Callback<InventarioResponse>{
        override fun onResponse(
            call: Call<InventarioResponse>,
            response: Response<InventarioResponse>
        ) {
            if (response.isSuccessful) {
                Toast.makeText(context, "Material guardado con exito", Toast.LENGTH_SHORT).show()
                viewModel.setStateInventarioList(true)

            } else {
                Toast.makeText(context, "Error al guardar material", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<InventarioResponse>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}

fun saveTarea(context: Context, viewModel: ManagerViewModel, tareaRequest: TareaRequest){
    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val call: Call<TareaResponse> = ApiClient.apiService.saveTarea(token, tareaRequest)
    call.enqueue(object: Callback<TareaResponse>{
        override fun onResponse(
            call: Call<TareaResponse>,
            response: Response<TareaResponse>
        ) {
            if (response.isSuccessful) {
                viewModel.setStateTareaList(true)
                Toast.makeText(context, "Tarea guardada con exito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al guardar tarea", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<TareaResponse>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}

fun deleteMaterial(context: Context, viewModel: ManagerViewModel, id: String){
    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val call: Call<Void> = ApiClient.apiService.deleteMaterial(token, id)
    call.enqueue(object: Callback<Void>{
        override fun onResponse(
            call: Call<Void>,
            response: Response<Void>
        ) {
            if (response.isSuccessful) {
                viewModel.setStateTareaList(true)
                Toast.makeText(context, "Material borrado con exito", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("TAG", "onResponse: "+response)
                Toast.makeText(context, "Error al borrar material", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}
fun deleteParte(context: Context, viewModel: ManagerViewModel, id: String){
    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val call: Call<Void> = ApiClient.apiService.deleteParte(token, id)
    call.enqueue(object: Callback<Void>{
        override fun onResponse(
            call: Call<Void>,
            response: Response<Void>
        ) {
            if (response.isSuccessful) {
                viewModel.setStateTareaList(true)
                Toast.makeText(context, "Parte borrado con exito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al guardar tarea", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}
fun deleteTarea(context: Context, viewModel: ManagerViewModel, id: String){
    val token = SharesPreferencesApplication.preferences.getData("token", "")
    val call: Call<Void> = ApiClient.apiService.deleteTarea(token, id)
    call.enqueue(object: Callback<Void>{
        override fun onResponse(
            call: Call<Void>,
            response: Response<Void>
        ) {
            if (response.isSuccessful) {
                viewModel.setStateTareaList(true)
                Toast.makeText(context, "Tarea borrada conexito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al guardar tarea", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.d("TAG", "onFailure: "+ t)
        }
    })
}