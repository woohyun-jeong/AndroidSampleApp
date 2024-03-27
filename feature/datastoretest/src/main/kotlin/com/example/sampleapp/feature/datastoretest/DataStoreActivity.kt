package com.example.sampleapp.feature.datastoretest

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.sampleapp.core.designsystem.theme.KnightsTheme
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
                    TwoButtonCard({
                        if (viewModel.currentSettingData != null) {
//                            val isDarkTheme = viewModel.currentSettingData?.isDarkTheme ?: false
//                            viewModel.updateTest(isDarkTheme)
                        }
                    }, {
                        Log.d(
                            "DataStoreActivity",
                            "test button currentTestData = ${viewModel.currentTestData?.isTest}"
                        )
                        if (viewModel.currentTestData != null) {
                            val isTest = viewModel.currentTestData?.isTest ?: false
                            viewModel.updateTest(!isTest)
                        }
                    })
                }
            }
        }
    }
}

