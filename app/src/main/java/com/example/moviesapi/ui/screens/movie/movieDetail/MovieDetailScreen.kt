package com.example.moviesapi.ui.screens.movie.movieDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapi.data.entities.movie.Genre
import com.example.moviesapi.data.entities.movie.credits.Cast
import com.example.moviesapi.ui.navigation.CustomTopAppBar
import com.example.moviesapi.ui.screens.common.ArrowBackIcon
import com.example.moviesapi.ui.screens.common.IconWithText
import com.example.moviesapi.ui.screens.common.MovieThumb
import com.example.moviesapi.ui.screens.common.ShowLoadingIndicator
import com.example.moviesapi.ui.screens.common.TextResource
import com.example.moviesapi.ui.screens.common.undefinedImage
import com.example.moviesapi.ui.screens.personDetail.formatDate
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    movieDetailViewModel: MovieDetailViewModel,
    movieId: Int,
    modifierBox: Modifier = Modifier,
    modifierImg: Modifier = Modifier,
    onUpClick: () -> Unit,
    onCastPersonClick: (personId: Int) -> Unit
) {
    val loading by movieDetailViewModel.loading
    val movieDetail by movieDetailViewModel.movieDetail
    val movieCredits by movieDetailViewModel.movieCredits
    DisposableEffect(movieDetailViewModel) {
        movieDetailViewModel.getMovieDetail(movieId)
        movieDetailViewModel.getMovieCredits(movieId)
        onDispose {
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = movieDetail.title,
                onUpClick = onUpClick
            )
        },
    ) {

        if (loading) {
            ShowLoadingIndicator()
        } else {
            Log.d("Revisar", "BorrarmovieDeta movie -> $movieDetail")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 60.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {
                MovieThumb(
                    imageUrl = "https://image.tmdb.org/t/p/original/${movieDetail.backdrop_path}",
                    modifierBox = modifierBox.fillMaxWidth(),
                    modifierImage = modifierImg.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    TextResource(
                        text = movieDetail.title,
                        modifierBox = modifierBox
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 23.sp),
                        fontFamily = FontFamily.Monospace
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .height(30.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextResource(
                            text = "${movieDetail.runtime.toHoursAndMinutes().first} h ${movieDetail.runtime.toHoursAndMinutes().second} min",
                            modifierBox = modifierBox,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Monospace,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(fontSize = 12.sp)
                        )
                        Text(
                            text = "·",
                            modifier = Modifier.padding(start = 6.dp, end = 6.dp),
                            fontWeight = FontWeight.Bold,
                            lineHeight = 28.sp,
                            style = TextStyle(fontSize = 18.sp),
                        )
                        TextResource(
                            text = appendGenders(movieDetail.genres),
                            modifierBox = modifierBox
                                .fillMaxWidth(),
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Monospace,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }
                    IconWithText(
                        icon = Icons.Default.DateRange,
                        text = movieDetail.release_date,
                        rowModifier = Modifier.padding(top = 10.dp)
                    )
                    /*if (movieDetail.tagline != "") {
                        MovieText(
                            text = movieDetail.tagline,
                            modifierBox = modifierBox
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Monospace
                        )
                    }*/
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, bottom = 15.dp)
                    )
                    TextResource(
                        text = "Movie Information",
                        modifierBox = modifierBox
                            .fillMaxWidth(),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 18.sp)
                    )
                    TextResource(
                        text = movieDetail.overview,
                        modifierBox = modifierBox
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Default,
                    )
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, bottom = 15.dp)
                    )
                    TextResource(
                        text = "Cast",
                        modifierBox = modifierBox
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 18.sp)
                    )
                    CastList(
                        movieCreditsList = movieCredits.cast,
                        onCastPersonClick = onCastPersonClick
                    )
                }
            }
        }

    }
}


@Composable
fun CastList(
    movieCreditsList: List<Cast>,
    onCastPersonClick: (personId: Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(2.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    )
    {
        items(movieCreditsList) { castPerson ->
            CastListItem(
                castPerson = castPerson,
                onClick = {
                    Log.d("Revisar", "BotonCastClickado -> $castPerson")
                    onCastPersonClick(castPerson.id)
                }
            )
        }
    }
}

@Composable
fun CastListItem(
    castPerson: Cast,
    onClick: () -> Unit
) {
    Card(shape = RoundedCornerShape(7.dp)) {
        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(120.dp)
                    .clickable { onClick() }
                //
            ) {
                if (castPerson.profile_path != null && castPerson.profile_path != "") {
                    MovieThumb(
                        imageUrl = "https://image.tmdb.org/t/p/original/${castPerson.profile_path}",
                        modifierBox = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                            .graphicsLayer(alpha = 0.5f),
                        modifierImage = Modifier
                            .fillMaxWidth()
                            .padding(1.dp),
                        contentScale = ContentScale.FillWidth
                    )
                } else {
                    undefinedImage(
                        icon = Icons.Default.Person,
                        modifierBox = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                            .graphicsLayer(alpha = 0.8f),
                        modifierImage = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                }

                CastText(
                    name = castPerson.name,
                    movieName = castPerson.character,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 13.sp),
                    modifierBox = Modifier
                        .wrapContentWidth()
                        .fillMaxSize(),
                    textAlign = TextAlign.Center,
                    softWrap = false,
                    color = Color.White,
                    maxLines = 1,
                    modifierText = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp, start = 3.dp, end = 3.dp)
                )
            }
        }
    }
}

fun Int.toHoursAndMinutes(): Pair<Int, Int> {
    val hours = this / 60
    val remainingMinutes = this % 60
    return Pair(hours, remainingMinutes)
}

fun appendGenders(genderList: List<Genre>): String {
    return genderList.joinToString { it.name }
}

@Composable
fun CastText(
    name: String,
    movieName: String,
    modifierBox: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    fontWeight: FontWeight,
    fontFamily: FontFamily,
    overflow: TextOverflow,
    style: TextStyle,
    textAlign: TextAlign,
    softWrap: Boolean,
    color: Color,
    maxLines: Int

) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifierBox,

        )
    {
        Text(
            text = name,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            style = style,
            overflow = overflow,
            textAlign = textAlign,
            softWrap = softWrap,
            color = color,
            maxLines = maxLines,
            modifier = modifierText.padding(bottom = 17.dp)
        )

        Text(
            text = movieName,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            style = style,
            overflow = overflow,
            textAlign = textAlign,
            softWrap = softWrap,
            color = color,
            maxLines = maxLines,
            modifier = modifierText
        )
    }
}


