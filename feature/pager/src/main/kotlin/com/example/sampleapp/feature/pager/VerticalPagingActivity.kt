package com.example.sampleapp.feature.pager

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.paging.compose.collectAsLazyPagingItems
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class VerticalPagingActivity : AppCompatActivity() {
    private val viewModel: PagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            KnightsTheme(true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
internal fun ShoppingScreen(
    viewModel: PagingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.shoppingUiState.collectAsStateWithLifecycle()
    Log.d("VerticalPagingActivity", "uiState = ${uiState.toString()}")
    val snackBarHostState = remember { SnackbarHostState() }
    val localContextResource = LocalContext.current.resources
    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            snackBarHostState.showSnackbar(
                when (throwable) {
                    is UnknownHostException -> "테스트1"
                    else -> "테스트2"//localContextResource.getString(R.string.error_message_unknown)
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
            lazyListState = lazyListState,
            viewModel = viewModel
        )
    }
}

private const val SHOPPING_ITEM_COUNT = 20

@Composable
fun ShoppingList(
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    viewModel: PagingViewModel = hiltViewModel(),
) {
    //방법1
    /*LaunchedEffect(lazyListState.canScrollForward) {
        Log.d("VerticalPagingActivity","ShoppingList1 lazyListState canScrollForward == false, page = ${viewModel.page}")
        viewModel.page = viewModel.page + 1
    }
*/
    //방법2
    /*LaunchedEffect(lazyListState) {
        snapshotFlow {
            Log.d("VerticalPagingActivity","ShoppingList2 lazyListState = ${lazyListState.canScrollForward}")
            lazyListState.canScrollForward
        }
            .distinctUntilChanged()
            .filter { !it }
            .collect {
                viewModel.page = viewModel.page + 1
                lazyListState.scrollToItem(0)
                Log.d("VerticalPagingActivity","ShoppingList2 lazyListState canScrollForward == false, page = ${viewModel.page}")
            }
    }*/

    //방법3
    val pagingItems = viewModel.flowShoppingItem.collectAsLazyPagingItems()
    Log.d("VerticalPagingActivity", "pagingItems itemCount = ${pagingItems.itemCount}, items = ${pagingItems.itemSnapshotList.items}")

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Box(
                modifier = Modifier
                    .background(if (isSystemInDarkTheme()) Black else Neon05)
                    .statusBarsPadding()
                    .padding(top = 48.dp)
            ) {
                Text(
                    text = "canScrollForward = ${lazyListState.canScrollForward}, canScrollBackward = ${lazyListState.canScrollBackward}",
                    style = KnightsTheme.typography.headlineSmallBL,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .then(modifier))
            }
            TopBanner()
        }
        items(pagingItems.itemCount) { index ->
            ShoppingItem(
                shopping = pagingItems[index],
                modifier = Modifier.padding(horizontal = 8.dp),
            )
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