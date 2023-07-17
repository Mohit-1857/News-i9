package com.example.test.Home

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.core.screen.Screen
import com.example.test.DataModel.News

class NewsWeb
    constructor(private val news: News) : Screen {

    @Composable
    @SuppressLint("SetJavaScriptEnabled")
    fun MyContent(news : News) {

        // Declare a string that contains a url
        val mUrl = news.url

        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = WebViewClient()
                loadUrl(mUrl)
                this.settings.apply {
                    javaScriptEnabled = true
                }
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }

    @Composable
    override fun Content() {
        MyContent(news = news)
    }
}