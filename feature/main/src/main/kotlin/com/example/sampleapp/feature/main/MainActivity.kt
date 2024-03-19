package com.example.sampleapp.feature.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sampleapp.core.designsystem.component.BottomLogo
import com.example.sampleapp.core.designsystem.component.KnightsCard
import com.example.sampleapp.core.designsystem.component.NetworkImage
import com.example.sampleapp.core.designsystem.component.TextChip
import com.example.sampleapp.core.designsystem.res.rememberPainterResource
import com.example.sampleapp.core.designsystem.theme.Black
import com.example.sampleapp.core.designsystem.theme.KnightsTheme
import com.example.sampleapp.core.designsystem.theme.LocalDarkTheme
import com.example.sampleapp.core.designsystem.theme.Neon01
import com.example.sampleapp.core.designsystem.theme.Neon05
import com.example.sampleapp.core.model.Shopping
import com.example.sampleapp.core.ui.HTInputCheckTextFields
import com.example.sampleapp.core.ui.HTInputCheckTextFields2
import com.example.sampleapp.feature.main.services.ClosingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val tag = "MainActivityTest"
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.page = 1

        setContent {
            // A surface container using the 'background' color from the theme
            KnightsTheme(true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        HTInputCheckTextFields()
                        HTInputCheckTextFields2()
                    }
                }
            }
        }
        startAppCloseService()
    }

    private fun startAppCloseService(){
        startService(Intent(this@MainActivity, ClosingService::class.java))
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "Activity onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "Activity onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "Activity onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "Activity onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Activity onDestroy")
    }
}

@Composable
internal fun ShoppingScreen(
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.shoppingUiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val localContextResource = LocalContext.current.resources
    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            snackBarHostState.showSnackbar(
                when (throwable) {
                    is UnknownHostException -> localContextResource.getString(R.string.error_message_network)
                    else -> localContextResource.getString(R.string.error_message_unknown)
                }
            )
        }
    }

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    val lazyListState = rememberLazyListState()
    Box(
        modifier = modifier.navigationBarsPadding(),
    ) {
        ShoppingList(
            uiState = uiState,
            lazyListState = lazyListState
        )
    }
}

private const val SHOPPING_ITEM_COUNT = 4

@Composable
fun ShoppingList(
    uiState: MainUiState,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            TopBanner()
        }
        when (uiState) {
            MainUiState.Loading -> {
                items(SHOPPING_ITEM_COUNT) {
                    ShoppingItem(
                        shopping = null,
                        Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            is MainUiState.Success -> {
                val shoppingItems = uiState.shoppingItems
                items(shoppingItems.size) { index ->
                    ShoppingItem(
                        shopping = shoppingItems[index],
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
                }
            }
        }
        item {
            Footer(modifier = Modifier.padding(bottom = 16.dp))
        }
    }
}

@Composable
private fun ShoppingItem(
    shopping: Shopping?,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val modifier =
        if (shopping == null) {
            Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.outline)
        } else {
            Modifier
        }
    val placeholder = rememberPainterResource(
        lightId = com.example.sampleapp.core.ui.R.drawable.ic_contributor_placeholder_lightmode,
        darkId = com.example.sampleapp.core.ui.R.drawable.ic_contributor_placeholder_darkmode,
    )

    KnightsCard(
        enabled = shopping?.linkUrl?.isNotEmpty() ?: false,
        onClick = { uriHandler.openUri(shopping?.linkUrl ?: return@KnightsCard) },
        modifier = modifier,
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 24.dp,
                        end = 16.dp
                    )
            ) {
                TextChip(
                    stringResource(id = com.example.sampleapp.core.ui.R.string.session_room_keynote),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    labelColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = modifier
                )
                Text(
                    text = shopping?.title ?: " ".repeat(20),
                    style = KnightsTheme.typography.headlineSmallBL,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .then(modifier)
                )
            }

            NetworkImage(
                imageUrl = shopping?.imageUrl,
                placeholder = placeholder,
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .then(modifier)
            )
        }
    }
}

@Composable
private fun TopBanner(darkTheme: Boolean = LocalDarkTheme.current) {
    Box(
        modifier = Modifier
            .background(if (darkTheme) Black else Neon05)
            .statusBarsPadding()
            .padding(top = 48.dp)
    ) {
        Image(
            painter = painterResource(
                id = if (darkTheme) {
                    com.example.sampleapp.core.ui.R.drawable.bg_contributors_darkmode
                } else {
                    com.example.sampleapp.core.ui.R.drawable.bg_contributors_lightmode
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = stringResource(id = com.example.sampleapp.core.ui.R.string.contributor_banner_title),
                style = KnightsTheme.typography.headlineSmallBL,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(top = 24.dp),
            )
            Text(
                text = stringResource(id = com.example.sampleapp.core.ui.R.string.contributor_banner_description),
                style = KnightsTheme.typography.titleSmallM140,
                color = Neon01,
                modifier = Modifier.padding(top = 6.dp, start = 3.dp),
            )
        }
    }
}

@Composable
private fun Footer(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        BottomLogo()
    }
}