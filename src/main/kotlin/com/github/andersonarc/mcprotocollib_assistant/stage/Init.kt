package com.github.andersonarc.mcprotocollib_assistant.stage

import com.github.andersonarc.mcprotocollib_assistant.misc.Arguments
import com.github.andersonarc.mcprotocollib_assistant.misc.assert
import java.io.File
import java.util.concurrent.Executors

fun init(args: Array<String>): Arguments {
    println("entering init stage")
    assert(
        args.size > 1,
        "missing required arguments\nusage: ... output_directory git_repository [threads_number]"
    )

    /**
     * initialize command line arguments
     */
    val directory = args[0]
    println("working directory set to \"$directory\"")

    val repository = args[1]
    println("repository set to \"$repository\"")

    val threads = args.getOrNull(2) ?: Runtime.getRuntime().availableProcessors().toString()
    assert(
        threads.toIntOrNull() != null,
        "threads_number should be an integer")

    /**
     * clear working directory
     */
    val file = File(directory)
    file.deleteRecursively()
    file.mkdir()
    println("working directory cleaned")

    val executor = Executors.newFixedThreadPool(threads.toInt())
    println("leaving init stage")
    return Arguments(
        directory,
        repository,
        executor
    )
}