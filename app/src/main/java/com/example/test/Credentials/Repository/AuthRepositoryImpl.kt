package com.example.test.Credentials.Repository

import com.example.test.Credentials.ResultState
import com.example.test.Credentials.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(private val firebaseAuth : FirebaseAuth)

    : AuthRepository {
    override fun createUser(auth: User): Flow<ResultState<String>> = callbackFlow {
            trySend(ResultState.Loading)

        firebaseAuth.createUserWithEmailAndPassword(auth.email , auth.password).addOnCompleteListener{
            if(it.isSuccessful){
                trySend(ResultState.Success(firebaseAuth.uid.toString()))
            }
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose{
            close()
        }
    }

    override fun loginUser(auth: User): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseAuth.signInWithEmailAndPassword(auth.email , auth.password)
            .addOnCompleteListener{
                trySend(ResultState.Success("Login Successfully"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose{
            close()
        }
    }

    override fun userID(): String {
        return firebaseAuth.uid.toString()
    }

}