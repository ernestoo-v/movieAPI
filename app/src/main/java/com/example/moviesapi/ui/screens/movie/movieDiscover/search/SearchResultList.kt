package com.example.moviesapi.ui.screens.movie.movieDiscover.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult

@Composable
fun SearchResultsList(
    items: List<PopularMovieResult>,
    onItemClicked: (PopularMovieResult) -> Unit
) {
    LazyColumn {
        itemsIndexed(items = items) { index, searchResult ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClicked.invoke(searchResult) }) {
                Spacer(
                    modifier = Modifier.height(height = if (index == 0) 16.dp else 4.dp)
                )
                Text(
                    text = searchResult.title,
                    color = Color.LightGray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = searchResult.title,
                    color = Color.LightGray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 16.dp)
                        .background(Color.LightGray.copy(alpha = 0.2f))
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}