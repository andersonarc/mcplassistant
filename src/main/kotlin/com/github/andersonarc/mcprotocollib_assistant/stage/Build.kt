package com.github.andersonarc.mcprotocollib_assistant.stage

import com.github.andersonarc.mcprotocollib_assistant.maven.compile
import com.github.andersonarc.mcprotocollib_assistant.misc.Arguments
import java.nio.file.Files
import java.nio.file.Paths

fun build(arguments: Arguments) {
    println("entering build stage")

    /**
     * unpack arguments
     */
    val directory = arguments.directory
    val executor = arguments.executor

    for (source in Files.list(Paths.get(directory))) {
        /**
         * source is a directory with project
         * it should be built with maven
         */
        executor.execute {
            println("compiling $source")
            compile(source.toFile())
        }
    }

    println("leaving build stage")
}