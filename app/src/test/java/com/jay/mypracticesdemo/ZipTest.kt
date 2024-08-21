package com.jay.mypracticesdemo

import org.junit.Test
import java.io.File
import java.text.SimpleDateFormat

class ZipTest {
    @Test
    fun zipArchive() {
        val path = "E:\\DEV\\DevProject\\AISpeechTTSEngin"
        val format = SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(System.currentTimeMillis())
        val name = File(path).name
        print("name=$name")
        val pathDist = "D:\\zip$name$format.zip"
        XZip.ZipFolder(path, pathDist, "build")
    }
}