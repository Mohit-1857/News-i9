package com.example.test.DI

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.test.BaseApplication
import com.example.test.Data_Store.DataStorePrefrence
import com.example.test.NetworkRequest.APIService
import com.google.firebase.auth.FirebaseAuth
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.prefs.Preferences
import javax.inject.Singleton


@dagger.Module
@InstallIn(SingletonComponent::class)
object Module : BaseApplication(){


    // FirebaseAuth Instance
    @Provides
    @Singleton
    fun providesAuth() : FirebaseAuth = FirebaseAuth.getInstance()



    // Retrofit Instance
    @Provides
    @Singleton
    fun provideRetrofit () : APIService = Retrofit.Builder()
        .run {
            baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }.create(APIService::class.java)




}