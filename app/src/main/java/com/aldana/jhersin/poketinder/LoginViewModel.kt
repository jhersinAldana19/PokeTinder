package com.aldana.jhersin.poketinder

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel(
    private val context: Context
) : ViewModel() {

    val inputsError = MutableLiveData<Boolean>()
    val loginSuccess = MutableLiveData<Boolean>()
    val authError = MutableLiveData<Boolean>()

    private val sharedPreferencesRepository = SharedPreferencesRepository().apply {
        setSharedPreferences(context)
    }

    // Función para validar los campos de entrada (email y contraseña)
    fun validateInputs(email: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            if (isEmptyInputs(email, password)) {
                inputsError.postValue(true)
                return@launch
            }

            try {
                // Llamada a la API de login
                val request = getRetrofit().create(PokemonApi::class.java).loginUser(User(email, password))

                // Obtener email y contraseña almacenados en SharedPreferences
                val emailSharedPreferences = sharedPreferencesRepository.getUserEmail()
                val passwordSharedPreferences = sharedPreferencesRepository.getUserPassword()


                if (email == emailSharedPreferences && password == passwordSharedPreferences) {
                    loginSuccess.postValue(true)
                } else {
                    authError.postValue(true)
                }
            } catch (e: Exception) {
                authError.postValue(true)
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer ${sharedPreferencesRepository.getAuthKey()}")
                .build()
            chain.proceed(newRequest)
        }
        val client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // Función auxiliar para verificar si los campos de entrada están vacíos
    private fun isEmptyInputs(email: String, password: String) = email.isEmpty() || password.isEmpty()
}
