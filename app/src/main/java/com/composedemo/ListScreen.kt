package com.composedemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
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
    LazyColumn(modifier.padding(16.dp)) {
        items(viewModel.sites) {
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                Image(
                    painter = rememberImagePainter("${it}favicon.ico"),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp),
                    contentDescription = it,
                )
                Text(text = it, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}