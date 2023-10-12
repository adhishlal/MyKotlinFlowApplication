package com.example.mykotlinflowapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mykotlinflowapplication.ui.theme.MyKotlinFlowApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyKotlinFlowApplicationTheme {

                val mainActivityViewModel = viewModel<MainActivityViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Greeting(
                            "My current value is ${
                                mainActivityViewModel.myCountDownTimer.collectAsState(initial = 10).value
                            }"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(value: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello! $value!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyKotlinFlowApplicationTheme {
        Greeting("Android")
    }
}