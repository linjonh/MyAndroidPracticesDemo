package com.jay.mypracticesdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.jay.mvvm.MyViewModel
import com.jay.mypracticesdemo.ui.theme.MyPracticesDemoTheme

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    lateinit var viewModel: MyViewModel

    //获取viewModel的方式
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MyViewModel::class.java]
        setContent {
            MyPracticesDemoTheme {
                // A surface container using the 'background' color from the theme
                GreetingPreview()
            }
        }
        viewModel.liveData.observe(this) {
            Log.d(TAG, "onChange it=$it")
        }
    }
}
