package com.jay.mypracticesdemo

import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jay.mypracticesdemo.ui.theme.MyPracticesDemoTheme

//class ComposeDemo {
//}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .wrapContentWidth(Alignment.CenterHorizontally)
        .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = {
            //to do something
//            Toast.makeText(getApplicationContext, "Button clicked", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Click me")
        }
    }
}

@Composable
fun ListItemLayout(index: Int = 0) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val expandExtraPadding by animateDpAsState(
        targetValue = if (isExpanded) 16.dp else 0.dp, animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .padding(vertical = expandExtraPadding.coerceAtLeast(0.dp)),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            val shape = RoundedCornerShape(4.dp)
            Image(
                contentDescription = "avatar",
                painter = painterResource(id = R.drawable.icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(shape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, shape)
                    .clickable { /* do something */
                        isExpanded = !isExpanded
                    }, alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier.clickable { isExpanded = !isExpanded },
            ) {
                Text(
                    text = "Title ${index + 1}",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(Alignment.Bottom),
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 2.dp
                ) {
                    Text(
                        text = "lorem ipsum dolor sit amet lorem ipsum dolor sit amet,lorem ipsum dolor sit amet".repeat(10),
                        modifier = Modifier.padding(all = 4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Greeting(name = "Android")
            LazyColumn {
                items(2) { index ->
                    ListItemLayout(index)
                }
            }
        }
    }
}

@Composable
fun GridItemLayout(index: Int = 0, imageLoadCallback: () -> ImageBitmap) {
    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        var name by remember { mutableStateOf("ComposeView in AndroidView") }
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                bitmap = imageLoadCallback(), contentDescription = "item image", modifier = Modifier
                    .height(120.dp)
                    .fillMaxSize()
            )
            Text(text = "GridItem $index", style = MaterialTheme.typography.titleLarge)
            AndroidView(factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.compose_resuse, null)
                val composeView: ComposeView = view.rootView.findViewById(R.id.composeView)
                composeView.setContent {
                    MyPracticesDemoTheme {
                        Greeting(name = name)
                    }
                }
                return@AndroidView view
            }, update = {
                //to do something
                name = "AndroidView"
            }, modifier = Modifier
                .fillMaxSize()
                .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun GridItemLayoutPreview() {
    CommonBgSurface {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(14) { index ->
                GridItemLayout(index, imageLoadCallback = {
                    ImageBitmap(100, 100)
                })
            }
        }
    }
}

@Composable
fun TabRowDemo() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Tab1", "Tab2", "Tab3")
    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            tabs.forEachIndexed { index, title ->
                LeadingIconTab(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.icon),
                            contentDescription = "icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    text = { Text(text = title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        Text(
            text = "Selected tab index: $selectedTabIndex",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun TabRowDemoPreview() {
    CommonBgSurface {
        TabRowDemo()
    }
}