package com.github.andersonarc.mcprotocollib_assistant.stage

import com.github.andersonarc.mcprotocollib_assistant.misc.Arguments
import com.github.andersonarc.mcprotocollib_assistant.misc.Mode
import com.github.andersonarc.mcprotocollib_assistant.misc.assert
import java.io.File
import java.util.concurrent.Executors
import kotlin.system.exitProcess

fun init(args: Array<String>): Arguments {
    println("entering init stage")
    assert(
        args.size > 1,
        "missing required arguments\nusage: ... working_directory git_repository [load|build|all] [threads_number]"
    )

    /**
     * initialize command line arguments
     */
    val directory = args[0]
    println("working_directory set to \"$directory\"")

    val repository = args[1]
    println("git_repository set to \"$repository\"")

    val modeN = args.getOrNull(2)
    val modeOffset = if (modeN == null) 0 else 1
    val mode = modeN?.let {
        try {
            Mode.valueOf(it.toUpperCase())
        } catch (e: EnumConstantNotPresentException) {
            println("mode \"$it\" is invalid")
            exitProcess(1)
        }
    } ?: Mode.ALL
    println("mode set to $mode")

    val threads = args.getOrNull(2 + modeOffset) ?: Runtime.getRuntime().availableProcessors().toString()
    assert(
        threads.toIntOrNull() != null,
        "threads_number should be an integer")
    println("threads_number set to $threads")

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
        mode,
        executor
    )
}