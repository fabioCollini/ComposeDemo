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
    Sites(viewModel.sites, modifier)
}

@Composable
private fun Sites(sites: List<WebSiteHolder>, modifier: Modifier) {
    LazyColumn(modifier.padding(vertical = 8.dp)) {
        items(sites) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { it.incrementVisit() }
                .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(it.icon),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp),
                    contentDescription = it.url,
                )
                Text(
                    text = "${it.url} [${it.visits}]",
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
            }
        }
    }
}

