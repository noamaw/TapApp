@file:OptIn(ExperimentalMaterial3Api::class)

package com.noam.tapapp

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.noam.tapapp.ui.theme.TapAppTheme
import com.noam.tapapp.viewmodel.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videosVM: VideoViewModel by viewModels()
        setContent {
            TapAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchBar()
                    Scaffold(
                        content = {
                            WebViewContent(paddingValues = it,
                                mUrl = if (videosVM.snapshotUrlsStateList.isEmpty()) {
                                    "https://www.geeksforgeeks.org"
                                } else {
                                    videosVM.snapshotUrlsStateList.first()
                                })
                        }
                    )

                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    var searchedTerm by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)) {
        OutlinedTextField(
            value = searchedTerm,
            onValueChange = { searchedTerm = it },
            modifier = Modifier
                .padding(8.dp)
                .weight(3F),
            label = { "Search Engine" },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun WebViewContent(mUrl : String = "https://www.geeksforgeeks.org", paddingValues: PaddingValues = PaddingValues(4.dp)) {
    Surface(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        AndroidView(
            modifier = Modifier.aspectRatio(0.75f),
            factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(mUrl)
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }
}

// Creating a composable
// function to create WebView
// Calling this function as
// content in the above function
@Composable
fun MyContent() {

    // Declare a string that contains a url
    val mUrl = "https://www.geeksforgeeks.org"

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            webViewClient = WebViewClient()
            loadUrl(mUrl)
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TapAppTheme {
        SearchBar()
        WebViewContent()
    }
}