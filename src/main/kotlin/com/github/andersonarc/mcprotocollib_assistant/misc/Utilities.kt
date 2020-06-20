package com.github.andersonarc.mcprotocollib_assistant.misc

import java.io.File
import java.util.concurrent.ExecutorService
import kotlin.system.exitProcess

@Suppress("NOTHING_TO_INLINE")
inline fun assert(condition: Boolean, message: String) {
    if (!condition) {
        System.err.println(message)
        exitProcess(1)
    }
}

fun process(directory: File, vararg options: String): Process {
    val builder = ProcessBuilder("git", *options)
    builder.directory(directory)
    val process = builder.start()
    process.waitFor()
    return process
}

class Arguments(val directory: String, val repository: String, val executor: ExecutorService)