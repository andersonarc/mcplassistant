package com.github.andersonarc.mcplassistant.misc

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
    val builder = ProcessBuilder(*options)
    builder.directory(directory)
    val process = builder.start()
    process.waitFor()
    return process
}

class Arguments(val directory: String, val repository: String, val mode: Mode, val executor: ExecutorService)

@Suppress("NOTHING_TO_INLINE")
enum class Mode {
    LOAD, BUILD, ALL;

    inline fun canLoad(): Boolean {
        return this == LOAD || this == ALL
    }

    inline fun canBuild(): Boolean {
        return this == BUILD || this == ALL
    }
}