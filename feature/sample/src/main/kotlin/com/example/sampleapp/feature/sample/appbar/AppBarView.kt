package com.example.sampleapp.feature.sample.appbar

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalMaterial3Api
@Composable
fun AppBarSample(title:String, onClick : () -> Unit){
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        }
    )
}