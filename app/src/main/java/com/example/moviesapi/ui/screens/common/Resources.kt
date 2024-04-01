package com.example.moviesapi.ui.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun ShowLoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.Black,
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
fun MovieThumb(
    imageUrl: String,
    modifierBox: Modifier = Modifier,
    modifierImage: Modifier = Modifier,
    contentScale: ContentScale
) {
    Box(
        modifier = modifierBox
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(200)
                .build(),
            contentDescription = null,
            modifier = modifierImage,
            contentScale = contentScale,
        )
    }
}

@Composable
fun undefinedImage(
    icon: ImageVector,
    modifierBox: Modifier = Modifier,
    modifierImage: Modifier = Modifier,
) {
    Box(
        modifier = modifierBox
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = modifierImage
        )
    }
}

@Composable
fun TextResource(
    text: String,
    modifierBox: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = FontFamily.Default,
    lineHeight: TextUnit = 23.sp,
    style: TextStyle = TextStyle.Default,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign = TextAlign.Start,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    contentAlignment: Alignment = Alignment.TopStart
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifierBox
    ) {
        Text(
            text = text,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            lineHeight = lineHeight,
            style = style,
            overflow = overflow,
            textAlign = textAlign,
            softWrap = softWrap,
            maxLines = maxLines,
            modifier = modifierText
        )
    }
}

@Composable
fun TextResource(
    annotatedText: AnnotatedString,
    modifierBox: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = FontFamily.Default,
    lineHeight: TextUnit = 23.sp,
    style: TextStyle = TextStyle.Default,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign = TextAlign.Start,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    contentAlignment: Alignment = Alignment.TopStart
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifierBox
    ) {
        Text(
            text = annotatedText,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            lineHeight = lineHeight,
            style = style,
            overflow = overflow,
            textAlign = textAlign,
            softWrap = softWrap,
            maxLines = maxLines,
            modifier = modifierText
        )
    }
}

@Composable
fun IconWithText(
    icon: ImageVector,
    text: String,
    rowModifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    fontFamily: FontFamily = FontFamily.Monospace
) {
    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = iconModifier.size(20.dp)
        )
        TextResource(
            text = text,
            fontFamily = fontFamily,
            style = textStyle,
            modifierText = Modifier.padding(start = 8.dp)
        )
    }
}
