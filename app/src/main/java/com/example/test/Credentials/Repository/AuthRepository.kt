package com.example.test.Credentials.Repository

import com.example.test.Credentials.ResultState
import com.example.test.Credentials.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createUser(
        auth: User
    ) : Flow<ResultState<String>>

    fun loginUser(
        auth: User
    ) : Flow<ResultState<String>>

    fun userID() : String
}