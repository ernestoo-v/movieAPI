package com.example.moviesapi.ui.screens.personDetail

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapi.data.entities.movie.credits.cast.MovieCast
import com.example.moviesapi.data.entities.movie.credits.cast.PersonDetail
import com.example.moviesapi.data.entities.movie.credits.cast.image.PersonImages
import com.example.moviesapi.data.entities.movie.credits.cast.image.Profile
import com.example.moviesapi.ui.navigation.CustomTopAppBar
import com.example.moviesapi.ui.screens.common.IconWithText
import com.example.moviesapi.ui.screens.common.MovieThumb
import com.example.moviesapi.ui.screens.common.ShowLoadingIndicator
import com.example.moviesapi.ui.screens.common.TextResource
import com.example.moviesapi.ui.screens.common.undefinedImage
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailScreen(
    personId: Int,
    modifierBox: Modifier = Modifier,
    modifierImg: Modifier = Modifier,
    onUpClick: () -> Unit,
    onPersonMovieCreditsItemMovieClick: (movieCastId: Int) -> Unit,
    personDetailScreenViewModel: PersonDetailScreenViewModel,

    ) {
    LaunchedEffect(personDetailScreenViewModel) {
        personDetailScreenViewModel.getPersonDetail(personId)
        personDetailScreenViewModel.getPersonDetailMovieCredits(personId)
        personDetailScreenViewModel.getPersonImages(personId)
    }
    val loading by personDetailScreenViewModel.loading
    val personDetail by personDetailScreenViewModel.personDetail
    val personDetailMovieCredits by personDetailScreenViewModel.personDetailMovieCredits
    val personImages by personDetailScreenViewModel.personImages


    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = personDetail.name,
                onUpClick = onUpClick
            )
        },
    ) {
        if (loading) {
            ShowLoadingIndicator()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp)
            ) {

                item {
                    TextResource(
                        text = "Filmography",
                        modifierBox = modifierBox
                            .fillMaxWidth()
                            .padding(start = 15.dp, bottom = 23.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                if (personImages != null) {
                    item {
                        PersonColumn(personDetail, personImages)
                    }
                } else {
                    Log.d("Revisar", "Null-> $personImages")
                }

                items(personDetailMovieCredits) { movieCast ->
                    PersonMovieCreditsItem(
                        movieCast = movieCast,
                        onPersonMovieCreditsItemMovieClick = onPersonMovieCreditsItemMovieClick
                    )
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 40.dp)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonColumn(personDetail: PersonDetail, personImagesProfile: List<Profile>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (personDetail.profile_path != null) {
            LazyRow(modifier = Modifier.padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 10.dp), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                items(personImagesProfile) { personImage ->
                    MovieThumb(
                        imageUrl = "https://image.tmdb.org/t/p/original/${personImage.file_path}",
                        contentScale = ContentScale.FillWidth,
                        modifierBox = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .width(300.dp)
                            .height(400.dp)
                          ,
                        modifierImage = Modifier
                            .fillMaxWidth()
                            .padding(1.dp),
                    )
                }

            }


        } else {

            undefinedImage(
                icon = Icons.Default.Person,
                modifierBox = Modifier
                    .fillMaxWidth()
                    .padding(top = 90.dp, start = 50.dp, end = 50.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                modifierImage = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(1.dp)
                    .aspectRatio(1f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextResource(
                text = personDetail.name,
                modifierBox = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp),
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                style = TextStyle(fontSize = 32.sp)
            )
            if (personDetail.birthday != null && personDetail.birthday != "") {
                IconWithText(
                    icon = Icons.Default.DateRange,
                    text = formatDate(personDetail.birthday),
                    rowModifier = Modifier.padding(top = 15.dp)
                )
            } else {
                IconWithText(
                    icon = Icons.Default.DateRange,
                    text = "Date not available",
                    rowModifier = Modifier.padding(top = 15.dp)
                )
            }
            if (personDetail.place_of_birth != null && personDetail.place_of_birth != "") {
                IconWithText(
                    icon = Icons.Default.LocationCity,
                    text = personDetail.place_of_birth,
                    rowModifier = Modifier.padding(top = 10.dp)
                )
            } else {
                IconWithText(
                    icon = Icons.Default.LocationCity,
                    text = "Data not available",
                    rowModifier = Modifier.padding(top = 10.dp)
                )
            }

            TextResource(
                text = personDetail.biography,
                modifierBox = Modifier.padding(top = 20.dp),
                style = TextStyle.Default,
                lineHeight = 23.sp
            )
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 15.dp)
            )
        }
    }
}

//@Preview(showBackground = true, heightDp = 200, widthDp = 400)
@Composable
fun PersonMovieCreditsItem(
    movieCast: MovieCast,
    onPersonMovieCreditsItemMovieClick: (movieCastId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                Log.d("Revisar", "Pelicla clicada dentro de Person ${movieCast.id}")
                onPersonMovieCreditsItemMovieClick(movieCast.id)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 30.dp, end = 30.dp, bottom = 10.dp)
        ) {
            if (movieCast.poster_path != null) {
                MovieThumb(
                    imageUrl = "https://image.tmdb.org/t/p/original/${movieCast.poster_path}",
                    contentScale = ContentScale.FillWidth,
                    modifierBox = Modifier
                        .fillMaxWidth(0.3f)
                        .clip(RoundedCornerShape(6.dp))
                )
            } else {
                undefinedImage(
                    icon = Icons.Default.Photo,
                    modifierBox = Modifier
                        .fillMaxWidth(0.3f)
                        .clip(RoundedCornerShape(6.dp)),
                    modifierImage = Modifier
                        .padding(1.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
                    .padding(start = 15.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextResource(
                    annotatedText = buildAnnotatedString {
                        append("In ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(movieCast.title)
                        }
                        append(" as ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(movieCast.character)
                        }
                    },
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Start,
                    softWrap = true,
                    maxLines = 2,
                    modifierBox = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = TextStyle(fontSize = 16.sp)
                )
                if (movieCast.release_date != "") {
                    TextResource(
                        text = movieCast.release_date.substring(0, 4),
                        fontFamily = FontFamily.Monospace,
                        modifierBox = Modifier
                            .padding(top = 15.dp)
                    )
                } else {
                    TextResource(
                        text = "Date not available",
                        fontFamily = FontFamily.Monospace,
                        modifierBox = Modifier
                            .padding(top = 15.dp)
                    )
                }
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    return try {
        val localDate = LocalDate.parse(dateString)
        val year = localDate.year
        val fullMonthName =
            localDate.month.getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH)
        "$fullMonthName ${localDate.dayOfMonth}, $year"
    } catch (e: DateTimeParseException) {
        Log.e("FormatDate", "Error parsing date: $dateString", e)
        ""
    }
}
