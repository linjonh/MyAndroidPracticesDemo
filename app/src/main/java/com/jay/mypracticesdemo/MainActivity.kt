package com.jay.mypracticesdemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
        viewModel.liveData.observe(this) {
            Log.d(TAG, "onChange it=$it")
        }

        val intent = Intent()
        val pkg = "com.aispeech.lyra.adapter"
        intent.setClassName(pkg, "com.aispeech.lyra.adapter.media.videosearch.VideoVUIService")
        val bindService = bindService(intent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.i(TAG, "onServiceConnected=$name")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.i(TAG, "onServiceDisconnected=$name")

            }
        }, BIND_AUTO_CREATE)
        Log.i(TAG, "bindService=$bindService")

        val packageManager = packageManager
        val resolveService = packageManager.resolveService(intent, 0)
        Log.i(TAG, "resolveService=$resolveService")
        val installedApplications = packageManager.getInstalledApplications(0)
        installedApplications.forEach {
            if (it.packageName.equals(pkg)) {
                Log.i(TAG, "installedApplications=${it.packageName}")
            }
        }
        val installedPackages = packageManager.getInstalledPackages(0)
        installedPackages.forEach {
            if (it.packageName.equals(pkg)) {
                Log.i(TAG, "installedPackages=${it.packageName}")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    Button(onClick = {


    }) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyPracticesDemoTheme {
        Greeting("Android")
    }
}