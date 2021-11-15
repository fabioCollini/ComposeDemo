package com.composedemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter

@Preview
@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<ListViewModel>()
    when (val state = viewModel.state) {
        is Lce.Error ->
            Text("Error :(")
        Lce.Loading ->
            Text("Loading...")
        is Lce.Success ->
            Sites(
                sites = state.data.urls,
                visits = state.data.visits,
                onClick = viewModel::incrementVisits,
                modifier = modifier,
            )
    }
}

@Composable
private fun Sites(
    sites: List<String>,
    visits: Map<String, Int>,
    onClick: (String) -> Unit,
    modifier: Modifier,
) {
    LazyColumn(modifier.padding(vertical = 8.dp)) {
        items(sites) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(it) }
                .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Image(
                    painter = rememberImagePainter("${it}favicon.ico"),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp),
                    contentDescription = it,
                )
                Text(
                    text = "$it [${visits[it]}]",
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
            }
        }
    }
}

