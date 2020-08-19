package com.github.andersonarc.mcsloader.maven

import java.io.File

fun replace(directory: File, source: String, destination: String) {
    var current = File(directory, "src/main/java")
    val split = destination.split('.')
    //todo copy SOURCE to TMP, then delete SOURCE package, and copy TMP to DESTINATION
    println(source)
    for ((i, element) in source.split('.').withIndex()) {
        current = File(current, element)
        if (i < split.size) {
            println("$directory process $current to ${split[i]}")
            current.renameTo(File(current, split[i]))
        } else {
            println("$directory process $current delete")
            current.renameTo(current.parentFile)
        }
    }
    directory.walk().onEnter {
        it.isFile
    }.onEach {
        it.writeText(it.readText().replace(source, destination))
    }
}