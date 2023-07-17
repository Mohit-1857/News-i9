package com.example.test.SplashScreen

import android.content.Context
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test.Credentials.SignIn
import com.example.test.Credentials.User
import com.example.test.Data_Store.DataStorePrefrence
import com.example.test.Data_Store.DataStoreViewModel
import com.example.test.Home.Home
import com.example.test.R
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashS : Screen{
    @Inject
    constructor()

    @Composable
    override fun Content() {

        SplashSc()


    }

    @Composable
    fun SplashSc(dataStoreViewModel: DataStoreViewModel = hiltViewModel()){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val scale = remember{
            androidx.compose.animation.core.Animatable(0f)
        }

        // Animation
        LaunchedEffect(key1 = true){
//            scale.animateTo(
//                targetValue = 0.7f,
//                // tween Animation
//                animationSpec = tween(
//                    durationMillis = 800,
//                    easing = {
//                        OvershootInterpolator(4f).getInterpolation(it)
//                    }
//                )
//            )
            // Customize the delay time
            delay(3000L)

            var email : String = ""

            email = dataStoreViewModel.id

            if(email.isEmpty()){
                navigator.push(SignIn())
            }
            else{
                navigator.push(Home())
            }
//
//
//
//
//
//
//            if(dataStoreViewModel.id.isEmpty()){
//                Toast.makeText(context , "Empty"  , Toast.LENGTH_LONG).show()
//            }


           // navigator.push(SignIn())



        }

        // Image


        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Image(
                painter = painterResource(id = R.drawable.img_3),
                contentDescription = "splash",
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }

    }

}


