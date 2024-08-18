package com.jay.mypracticesdemo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp

class ComposeAnimation {
}

@Composable
fun AnimateDp() {
    Column {
        var clicked by remember { mutableStateOf(false) }
        val color by animateColorAsState(targetValue = if (clicked) Color(0xFFFF0055) else Color(0xFF998866),animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 100f
        )) // 0xFF0055 is the color code
        val size by animateDpAsState(targetValue =if (clicked) 100.dp else 200.dp , animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 100f
        )) // 100.dp is the target value
        Box(
            Modifier
                .size(size.coerceAtLeast(1.dp))
                .background(color)
                .clickable {
                    clicked = !clicked
                }
        )
    }
}

@Preview
@Composable
fun animateContentSize() {
    Column(
        Modifier
            .background(Color.LightGray)
            .animateContentSize()
    ) {
        Text(text ="Hello, World!")
        Text(text ="lorem ipsum dolor sit amet".repeat(20))

    }
}

@Preview
@Composable
fun AnimateDpPreview() {
    AnimateDp()
}