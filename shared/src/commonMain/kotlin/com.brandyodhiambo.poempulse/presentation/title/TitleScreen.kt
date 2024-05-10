package com.brandyodhiambo.poempulse.presentation.title

import com.brandyodhiambo.poempulse.utils.LocalAppNavigator
import com.brandyodhiambo.poempulse.utils.ObserveAsEvents
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import com.brandyodhiambo.poempulse.utils.UiEvents

@Composable
fun TitleScreen(
    titleViewModel: TitleViewModel = koinInject()
){
    val navigator = LocalAppNavigator.currentOrThrow
    val titleState = titleViewModel.titleState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveAsEvents(titleViewModel.eventsFlow) { event ->
        when (event) {
            is UiEvents.SnackbarEvent -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
            }

            else -> {}
        }
    }

    TitleScreenContent(
        titleState = titleState,
        snackbarHostState = { SnackbarHost(snackbarHostState) },
        onPoemTitleClicked = {
            navigator.push(TitlePoemScreen(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleScreenContent(
    titleState: TitleState,
    snackbarHostState: @Composable () -> Unit,
    onPoemTitleClicked:(String)->Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ) ,
                title = {
                    Text(
                        text = "Poem's Titles",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            )
        },
        snackbarHost = snackbarHostState
    ) { paddingvalues->
        Box(modifier = Modifier.fillMaxSize().padding(paddingvalues)) {

            if(titleState.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            if (titleState.title.isEmpty() && titleState.isLoading.not()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    text = "No Titles found",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                )
            }

         if(titleState.title.isNotEmpty() && titleState.isLoading.not()){
             LazyVerticalGrid(
                 columns = GridCells.Fixed(2),
                 contentPadding = PaddingValues(8.dp),
                 verticalArrangement = Arrangement.spacedBy(8.dp),
             ) {
                 items(titleState.title) { title ->
                     TitleCard(
                         title = title,
                         onTitleClicked = onPoemTitleClicked
                     )
                 }
             }
         }

        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TitleCard(
    title:String,
    onTitleClicked:(String)->Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onTitleClicked(title.removeQuotes())
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource("title_filled.xml"),
                contentDescription = "title_icon",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

fun String.removeQuotes(): String {
    return replace("\"", "")
}