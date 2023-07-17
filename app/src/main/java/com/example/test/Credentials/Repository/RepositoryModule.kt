package com.example.test.Credentials.Repository

import com.example.test.Credentials.ViewModel.FAuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {




    @Binds
    abstract fun providesFirebaseAuthRep(
        rep : AuthRepositoryImpl
    ) : AuthRepository


}