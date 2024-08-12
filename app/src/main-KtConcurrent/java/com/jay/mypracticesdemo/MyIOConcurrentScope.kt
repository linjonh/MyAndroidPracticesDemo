package com.jay.mypracticesdemo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class MyIOConcurrentScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = SupervisorJob().plus(Dispatchers.IO)
}