package com.github.andersonarc.mcprotocollib_assistant.stage

import com.github.andersonarc.mcprotocollib_assistant.maven.compile
import com.github.andersonarc.mcprotocollib_assistant.misc.Arguments
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

fun build(arguments: Arguments) {
    println("entering build stage")

    /**
     * unpack arguments
     */
    val directory = arguments.directory
    val executor = arguments.executor

    val futures = ArrayList<Future<*>>()
    for (source in Files.list(Paths.get(directory))) {
        /**
         * source is a directory with project
         * it should be built with maven
         */
        futures.add(executor.submit {
            println("compiling $source")
            compile(source.toFile())
        })
    }
    println("awaiting for all tasks to finish")
    futures.forEach { it.get(5, TimeUnit.MINUTES) }
    println("leaving build stage")
}