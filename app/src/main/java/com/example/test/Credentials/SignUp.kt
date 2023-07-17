package com.example.test.Credentials

import android.annotation.SuppressLint
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test.Credentials.ViewModel.FAuthViewModel
import com.example.test.Data_Store.DataStoreViewModel
import com.example.test.Home.Home
import com.example.test.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SignUp : Screen {


    @Composable
    override fun Content() {
        SignUpUser()
    }




    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun SignUpUser(viewModel: FAuthViewModel = hiltViewModel() , dataStoreViewModel: DataStoreViewModel = hiltViewModel()){
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        var email by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        var name by remember {
            mutableStateOf("")
        }



        // Creating a variable to store toggle state
        var passwordVisible by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(painter = painterResource(id = R.drawable.img_1), contentDescription = "User")
            Text(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = Color(0xFF1A0FDB),
                text = "Sign Up"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(15.dp),
                colors = CardDefaults.cardColors(Color(0xFF3025e8)),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    )
            {
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .absolutePadding(left = 30.dp, right = 30.dp),
                    value = name,
                    label = {
                        Text(text = "Name")
                    },
                    placeholder = {
                        Text(text = "Name")
                    },
                    maxLines = 1,
                    onValueChange = {
                        name = it
                    })

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(

                    value = email,
                    label = {
                        Text(text = "Email")
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .absolutePadding(left = 30.dp, right = 30.dp),
                    placeholder = {
                        Text(text = "Email")
                    },
                    maxLines = 1,
                    onValueChange = {
                        email = it
                    })

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(

                    value = password,
                    label = {
                        Text(text = "Password")
                    },
                    placeholder = {
                        Text(text = "Password")
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .absolutePadding(left = 30.dp, right = 30.dp),
                    onValueChange = {
                        password = it
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        // Localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        // Toggle button to hide or display password
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))


                OutlinedButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {
                    if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                        Toast.makeText(context , "Enter Email and Password" , Toast.LENGTH_LONG).show()
                    }
                    else{
                        scope.launch(Dispatchers.Main) {
                            viewModel.createUser(User(email , password)).collect{
                                when(it){
                                    is ResultState.Success -> {
                                        dataStoreViewModel.writeToLocal(viewModel.userId)
                                        navigator.push(Home())
                                    }

                                    is ResultState.Failure ->{
                                        Toast.makeText(context , "Not Created : $email : $password" , Toast.LENGTH_LONG).show()
                                    }

                                    is ResultState.Loading ->{

                                    }
                                }
                            }
                        }
                    }
                }) {
                    Text(
                        color = Color(0xFF3025e8),
                        text = "Sign Up")
                }

                Spacer(modifier = Modifier.height(30.dp))

            }
        }
    }




}

