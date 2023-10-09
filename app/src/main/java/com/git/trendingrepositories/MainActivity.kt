package com.git.trendingrepositories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.git.trendingrepositories.presentation.compose.GitAppHost
import com.git.trendingrepositories.presentation.compose.theme.GitAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GitAppTheme {
                Surface {
                    GitAppHost()
                }
            }
        }
    }
}