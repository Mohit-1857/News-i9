package com.example.test.DetailNews

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.test.DataModel.News
import com.example.test.Home.NewsWeb

class DetailArticle(private val news: News ) : Screen {



    @Composable
    override fun Content() {
        DetailNews()

        //MyContent()
    }


    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun DetailNews() {

        val scrobleState = rememberScrollState()
        val navigator = LocalNavigator.currentOrThrow

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .padding(10.dp)
                .scrollable(scrobleState, Orientation.Vertical, true)

        ) {


            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = news.urlToImage)
                        .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                            //scale(Scale.FIT)
                            transformations(RoundedCornersTransformation())
                        }).build()
                ), contentDescription = "News", modifier = Modifier
                    .fillMaxSize()

                    .weight(0.2f)
            )

            Row {
                Text(
                    modifier = Modifier.weight(0.1f),
                    text = news.source.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.width(15.dp))

//            Text(
//                text = news.author,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Light
//            )

                //Spacer(modifier = Modifier.height(3.dp))

                Text(
                    modifier = Modifier.weight(0.1f),
                    text = news.publishedAt,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light
                )
            }


            Text(
                text = news.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(5.dp))



            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = news.description,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = {
                    val obj : NewsWeb = NewsWeb(news)
                    navigator.push((obj))
                }) {

                Text(
                    text = "Read More",
                    color = Color(0xFF0172d9)
                )

            }
        }
    }
}