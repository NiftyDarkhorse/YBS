package com.example.ybschallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ybschallenge.ui.theme.YBSChallengeTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YBSChallengeTheme {
                YBSTestApp()
            }
        }
    }
}

@Composable
private fun YBSTestApp(modifier: Modifier = Modifier) {
    DestinationsNavHost(navGraph = NavGraphs.root)
}

@Preview
@Composable
fun MyAppPreview() {
    YBSChallengeTheme {
        YBSTestApp()
    }
}