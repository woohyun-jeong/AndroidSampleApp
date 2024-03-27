package com.example.sampleapp.feature.datastoretest

import android.R.attr.data
import android.R.attr.text
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.sampleapp.core.designsystem.theme.KnightsTheme
import com.google.protobuf.value
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DataStoreActivity : BaseActivity() {
    private val viewModel: DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initUiObserver()
    }

    private fun initUiObserver() {
        repeatOnStarted {
            /*viewModel.isDarkThemeFlow.collect {
                Log.d("DataStoreActivity", "initUiObserver SettingData = $it")
                viewModel.currentSettingData = it
            }*/

            viewModel.testDataFlow.collect {
                Log.d("DataStoreActivity", "initUiObserver isTest = ${it.isTest}")
                viewModel.currentTestData = it
                viewModel.updateIsTest(it.isTest)
            }
        }
    }

    private fun initView() {
        setContent {
            KnightsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestScreen()

                }
            }
        }
    }
    @Composable
    private fun TestScreen() {
        Column {
            Button(
                onClick = {
                    Log.d(
                        "DataStoreActivity",
                        "test button currentTestData = ${viewModel.currentTestData?.isTest}"
                    )
                    if (viewModel.currentTestData != null) {
                        val isTest = viewModel.currentTestData?.isTest ?: false
                        viewModel.updateTest(!isTest)
                    }
                },
                modifier = Modifier.wrapContentSize()
            ) { Text(text = "Test Value Change!") }
            var text by remember {
                mutableStateOf(viewModel.currentTestData.toString())
            }

            TestScreen(viewModel.isTest.value.toString())

        }
    }

    @Composable
    private fun TestScreen(str: String) {
        Text(text = str)
    }
}

