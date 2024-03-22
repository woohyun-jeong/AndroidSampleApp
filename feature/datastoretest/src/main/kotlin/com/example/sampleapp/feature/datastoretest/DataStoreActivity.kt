package com.example.sampleapp.feature.datastoretest

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import com.example.sampleapp.Sample
import com.example.sampleapp.feature.datastoretest.repository.SampleRepository
import dagger.hilt.android.AndroidEntryPoint

private val Context.sampleDataStore: DataStore<Sample> by dataStore(
    fileName = "sample.pb",
    serializer = SampleSerializer
)

@AndroidEntryPoint
class DataStoreActivity : AppCompatActivity() {
    private val viewModel: DataStoreTestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = ViewModelProvider(
                this,
                DataStoreViewModelFactory(SampleRepository(sampleDataStore))
            )[DataStoreTestViewModel::class.java]

            // 여기에 UI 코드 구현
            MainView(viewModel = viewModel)
        }
    }
}

@Composable
fun MainView(viewModel: DataStoreTestViewModel) {
    // 읽기
    val data: Sample = viewModel.flow.collectAsState(initial = Sample.getDefaultInstance()).value

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            // 위에서 선언한 data 변수를 통해 counter 값을 가져오는 코드
            text = (data.counter).toString(),
            style = MaterialTheme.typography.titleMedium
        )
        // onClick 매개 변수를 통해 viewModel에 딸린 데이터 조작 메소드를 호출하는 코드
        Button(onClick = { viewModel.increaseCounter() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Increase counter"
            )
        }
        // onClick 매개 변수를 통해 viewModel에 딸린 데이터 조작 메소드를 호출하는 코드
        Button(onClick = { viewModel.decreaseCounter() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrease counter"
            )
        }
    }
}