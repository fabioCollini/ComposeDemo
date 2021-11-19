package com.composedemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.composedemo.utils.Lce
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Preview
@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<ListViewModel>()
    val state = viewModel.state
    Scaffold(
        modifier = modifier,
        topBar = { MyTopAppBar(viewModel::toggleSortOrder) },
        content = { contentPadding ->
            SwipeRefresh(
                state = rememberSwipeRefreshState(state is Lce.Loading),
                onRefresh = { viewModel.refresh() },
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
            ) {
                when (state) {
                    is Lce.Error -> Text("Error :(")
                    is Lce.Loading -> {}
                    is Lce.Success ->
                        SitesList(
                            sites = state.data.urls,
                            visits = state.data.visits,
                            onClick = viewModel::incrementVisits,
                            modifier = Modifier.fillMaxSize(),
                        )
                }
            }
        }
    )
}

@Composable
private fun SitesList(
    sites: List<String>,
    visits: Map<String, Int>,
    onClick: (String) -> Unit,
    modifier: Modifier,
) {
    LazyColumn(modifier.padding(vertical = 8.dp)) {
        items(sites) {
            Site(it, visits[it], onClick)
        }
    }
}

@Composable
private fun Site(
    url: String,
    visits: Int?,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick(url) }
        .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Image(
            painter = rememberImagePainter("${url}favicon.ico"),
            modifier = Modifier
                .size(32.dp)
                .padding(end = 8.dp),
            contentDescription = url,
        )
        Text(
            text = "$url [$visits]",
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}
