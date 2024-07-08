package com.jay.mypracticesdemo

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Enumeration
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * Android zip工具
 *
 * @author Ren.xia
 * @version 1.0
 */
class XZip {
    @Throws(Throwable::class)
    fun finalize() {
    }

    companion object {
        /**
         * 取得压缩包中的 文件列表(文件夹,文件自选)
         *
         * @param zipFileString  压缩包名字
         * @param bContainFolder 是否包括 文件夹
         * @param bContainFile   是否包括 文件
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getZipFileList(
            zipFileString: String?, bContainFolder: Boolean,
            bContainFile: Boolean
        ): List<File> {
            println("GetFileList(String)")
            val fileList: MutableList<File> = ArrayList()
            val inZip = ZipInputStream(FileInputStream(zipFileString))
            var zipEntry: ZipEntry
            var szName = ""
            while (inZip.getNextEntry().also { zipEntry = it } != null) {
                szName = zipEntry.name
                if (zipEntry.isDirectory) {

                    // get the folder name of the widget
                    szName = szName.substring(0, szName.length - 1)
                    val folder = File(szName)
                    if (bContainFolder) {
                        fileList.add(folder)
                    }
                } else {
                    val file = File(szName)
                    if (bContainFile) {
                        fileList.add(file)
                    }
                }
            } // end of while
            inZip.close()
            return fileList
        }

        /**
         * 返回压缩包中的文件InputStream
         *
         * @param zipFileString 压缩文件的名字
         * @param fileString    解压文件的名字
         * @return InputStream
         * @throws Exception
         */
        @Throws(Exception::class)
        fun UpZip(zipFileString: String?, fileString: String?): InputStream {
            println("UpZip(String, String)")
            val zipFile = ZipFile(zipFileString)
            val zipEntry = zipFile.getEntry(fileString)
            return zipFile.getInputStream(zipEntry)
        }

        /**
         * 解压一个压缩文档 到指定位置
         *
         * @param zipFileString 压缩包的名字
         * @param outPathString 指定的路径
         * @throws Exception
         */
        @Throws(Exception::class)
        fun UnZipFolder(zipFileString: String?, outPathString: String) {
            println("UnZipFolder(String, String)")
            val inZip = ZipInputStream(
                FileInputStream(
                    zipFileString
                )
            )
            var zipEntry: ZipEntry
            var szName = ""
            while (inZip.getNextEntry().also { zipEntry = it } != null) {
                szName = zipEntry.name
                if (zipEntry.isDirectory) {

                    // get the folder name of the widget
                    szName = szName.substring(0, szName.length - 1)
                    val folder = File(outPathString + File.separator + szName)
                    folder.mkdirs()
                } else {
                    val file = File(outPathString + File.separator + szName)
                    file.createNewFile()
                    // get the output stream of the file
                    val out = FileOutputStream(file)
                    var len: Int
                    val buffer = ByteArray(1024)
                    // read (len) bytes into buffer
                    while (inZip.read(buffer).also { len = it } != -1) {
                        // write (len) byte from buffer at the position 0
                        out.write(buffer, 0, len)
                        out.flush()
                    }
                    out.close()
                }
            } // end of while
            inZip.close()
        } // end of func

        /**
         * 压缩文件,文件夹
         *
         * @param srcFileString 要压缩的文件/文件夹名字
         * @param zipFileString 指定压缩的目的和名字
         * @throws Exception
         */
        @Throws(Exception::class)
        fun ZipFolder(srcFileString: String?, zipFileString: String?) {
            val x = String.format("ZipFolder(String, String)=%s, %s", srcFileString, zipFileString)
            println(x)

            // 创建Zip包
            val outZip = ZipOutputStream(FileOutputStream(zipFileString))

            // 打开要输出的文件
            val file = File(srcFileString)

            // 压缩
            ZipFiles(file.getParent() + File.separator, file.getName(), outZip)

            // 完成,关闭
            outZip.finish()
            outZip.close()
        } // end of func

        /**
         * 压缩文件
         *
         * @param folderString
         * @param fileString
         * @param zipOutputSteam
         * @throws Exception
         */
        @Throws(Exception::class)
        private fun ZipFiles(folderString: String, fileString: String, zipOutputSteam: ZipOutputStream?) {
            val format = String.format("ZipFiles(String, String, ZipOutputStream) =%s , %s", folderString, fileString)
            println(format)
            if (zipOutputSteam == null) return
            val file = File(folderString + fileString)

            // 判断是不是文件
            if (file.isFile()) {
                val zipEntry = ZipEntry(fileString)
                val inputStream = FileInputStream(file)
                zipOutputSteam.putNextEntry(zipEntry)
                var len: Int
                val buffer = ByteArray(4096)
                while (inputStream.read(buffer).also { len = it } != -1) {
                    zipOutputSteam.write(buffer, 0, len)
                }
                zipOutputSteam.closeEntry()
            } else {

                // 文件夹的方式,获取文件夹下的子文件
                val fileList = file.list()

                // 如果没有子文件, 则添加进去即可
                if (fileList.size <= 0) {
                    val zipEntry = ZipEntry(fileString + File.separator)
                    zipOutputSteam.putNextEntry(zipEntry)
                    zipOutputSteam.closeEntry()
                }

                // 如果有子文件, 遍历子文件
                for (i in fileList.indices) {
                    ZipFiles(folderString, fileString + File.separator + fileList[i], zipOutputSteam)
                } // end of for
            } // end of if
        } // end of func

        /**
         * 将sourceZip连同子目录解压到outFileName目录下.
         *
         * @param sourceZip   待解压的zip文件
         * @param isCover     是否覆盖文件
         * @param outFileName
         * @throws IOException
         */
        @Throws(IOException::class)
        fun releaseZipToFile(sourceZip: String?, outFileName: String, isCover: Boolean) {
            val zfile = ZipFile(sourceZip)
            //        String parentPath = null;
//        if(isCover){
//            File file = new File(sourceZip);
//            parentPath = file.getParent();
//        }
            val zList: Enumeration<*> = zfile.entries()
            var ze: ZipEntry? = null
            val buf = ByteArray(1024)
            while (zList.hasMoreElements()) {
                try {
                    // 从ZipFile中得到一个ZipEntry
                    ze = zList.nextElement() as ZipEntry
                    if (ze.isDirectory) {
                        // 如果压缩文件要解压的是目录，而文件已存在且非目录则删除垃圾文件，否则写入该目录文件时会报错。
                        // 后面解压缩代码getRealFileName()会重新创建目录
                        val dirFile = File(outFileName + File.separator + ze!!.name)
                        if (dirFile.exists() && !dirFile.isDirectory()) {
                            dirFile.delete()
                        }
                        continue
                    }
                    val realFile = getRealFileName(outFileName, ze!!.name)
                    if (isCover && realFile.exists()) {
                        try {
//                        FileUtil.deleteAll(realFile);
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                    // 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
                    val `is`: InputStream = BufferedInputStream(zfile.getInputStream(ze))
                    val os: OutputStream = BufferedOutputStream(FileOutputStream(realFile))
                    var readLen = 0
                    while (`is`.read(buf, 0, 1024).also { readLen = it } != -1) {
                        os.write(buf, 0, readLen)
                    }
                    `is`.close()
                    os.close()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            zfile.close()
        }

        /**
         * 给定根目录，返回一个相对路径所对应的实际文件名.
         *
         * @param baseDir     指定根目录
         * @param absFileName 相对路径名，来自于ZipEntry中的name
         * @return java.io.File 实际的文件
         */
        fun getRealFileName(baseDir: String?, absFileName: String): File {
            val dirs = absFileName.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var ret = File(baseDir)
            // 创建目录结构
            if (dirs.size > 1) {
                for (i in 0 until dirs.size - 1) {
                    ret = File(ret, dirs[i])
                }
            }
            if (!ret.exists()) {
                ret.mkdirs()
            }
            ret = File(ret, dirs[dirs.size - 1])
            return ret
        }
    }
}
