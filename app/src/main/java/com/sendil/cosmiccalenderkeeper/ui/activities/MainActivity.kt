package com.sendil.cosmiccalenderkeeper.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sendil.cosmiccalenderkeeper.theme.CosmicCalendarKeeperTheme
import com.sendil.cosmiccalenderkeeper.navigation.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CosmicCalendarKeeperTheme {
                MainScreen()
            }
        }
    }
}