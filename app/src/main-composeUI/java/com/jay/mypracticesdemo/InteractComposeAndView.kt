package com.jay.mypracticesdemo

import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

class InteractComposeAndView {
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AnimatedImageDrawableDemo(name: String) {
    AndroidView(factory = { context ->
        ImageView(context).apply {
            //to do something
            setImageDrawable(context.getDrawable(R.drawable.think_loop_animation_drawable) as AnimationDrawable)
            contentDescription = name
        }
    }, update = { view ->
        //start animation
        (view.drawable as AnimationDrawable).start()
    }, modifier = Modifier.size(100.dp))
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun AnimatedImageDrawableDemoPreview() {
    CommonBgSurface(modifier = Modifier
        .wrapContentHeight(Alignment.Top)
        ) {
        AnimatedImageDrawableDemo("AnimatedImageDrawableDemo")
    }

}