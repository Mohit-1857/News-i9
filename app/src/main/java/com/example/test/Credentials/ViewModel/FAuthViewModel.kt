package com.example.test.Credentials.ViewModel

import androidx.lifecycle.ViewModel
import com.example.test.Credentials.Repository.AuthRepository
import com.example.test.Credentials.User
import com.example.test.DataModel.News
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FAuthViewModel
@Inject
constructor(private val repo : AuthRepository) : ViewModel(){
    fun createUser(user : User)  = repo.createUser(user)

    fun loginUser(user: User) = repo.loginUser(user)

    val userId = repo.userID()


}