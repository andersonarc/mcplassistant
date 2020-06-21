package com.github.andersonarc.mcplassistant.git

import com.github.andersonarc.mcplassistant.misc.process
import java.io.File
import java.io.FileInputStream

fun commit(directory: File): String {
    val current = git(
        directory,
        "rev-parse",
        "HEAD"
    ).inputStream.readAllBytes()
    return String(current).replace("\n", "")
}

fun version(directory: File): String {
    val file = FileInputStream(File(directory, "pom.xml")).readAllBytes()
    return String(file).substringAfter("<version>").substringBefore("</version>").substringBefore("-")
}

@Suppress("NOTHING_TO_INLINE")
fun git(directory: File, vararg options: String): Process {
    return process(directory, "git", *options)
}