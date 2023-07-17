package com.example.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import javax.inject.Inject

class SignIN
@Inject
constructor() : ScreenModel{

    @Composable
    fun SignIn(){
        Box(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {

        }
    }

}

