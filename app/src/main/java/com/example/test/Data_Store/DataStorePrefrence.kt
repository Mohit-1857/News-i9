package com.example.test.Data_Store

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.test.Credentials.User
import com.google.firebase.firestore.remote.Datastore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.prefs.Preferences
import javax.inject.Inject

const val USER_DATA = "user_Data"
private val Context.dataStore  by preferencesDataStore(name = USER_DATA)




class DataStorePrefrence
    @Inject
    constructor(@ApplicationContext val context: Context) {


    val pref = context.dataStore
    companion object{
        val idKey = stringPreferencesKey("UID")
    }

    suspend fun saveToDataStore( key : String){
       pref.edit {
            it[idKey] = key
        }
    }

    val read : Flow<String> = pref.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("Datastore " , exception.message.toString())
            }else{
                throw exception
            }
        }
        .map {
            val name = it[idKey] ?: ""
            name
        }

//    suspend fun readToLocal() : Flow<String> =   pref.data
//        .catch {
//            if(this is Exception){
//                emit(emptyPreferences())
//            }
//        }.map {
//            val name = it[idKey] ?: ""
//            if(name.isEmpty()) {
//
//            Log.d("Error" , "Data store is empty")
//
//        }
//            Log.d("UID" , name)
//            return@map name
//        }

//    val index = stringPreferencesKey("UID")
//    val id : Flow<String> = pref.data
//        .map {
//            it[index] ?: 0
//            return@map it
//        }



    suspend fun clearDataStore() = context.dataStore.edit{
        it.clear()
    }
}