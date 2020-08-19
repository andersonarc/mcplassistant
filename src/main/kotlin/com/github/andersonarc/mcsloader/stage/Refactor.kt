package com.github.andersonarc.mcsloader.stage

import com.github.andersonarc.mcsloader.maven.replace
import com.github.andersonarc.mcsloader.misc.Arguments
import com.github.andersonarc.mcsloader.misc.startsWith
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

const val DESTINATION = "com.github.steveice10.mcprotocollib"

fun refactor(arguments: Arguments) {
    println("entering refactor stage")

    /**
     * unpack arguments
     */
    val directory = arguments.directory
    val executor = arguments.executor

    val futures = ArrayList<Future<*>>()
    for (source in Files.list(Paths.get(directory))) {
        /**
         * refactor all files in source directory
         */
        futures.add(executor.submit {
            println("refactoring $source")
            val string = source.fileName.toString()
            val replace = when {
                string.startsWith("1.7") -> "ch.spacebase.mc"
                string.startsWith("1.8", "1.9", "1.10") || string == "1.11" -> "org.spacehq.mc"
                else -> null /** replace not required */
            }
            if (replace != null) {
                replace(source.toFile(), replace, DESTINATION)
            }
        })
    }
    println("awaiting for all tasks to finish")
    futures.forEach { it.get(5, TimeUnit.MINUTES) }
    println("leaving refactor stage")
}