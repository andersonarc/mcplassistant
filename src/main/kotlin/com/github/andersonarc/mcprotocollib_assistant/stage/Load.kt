package com.github.andersonarc.mcprotocollib_assistant.stage

import com.github.andersonarc.mcprotocollib_assistant.git.commits
import com.github.andersonarc.mcprotocollib_assistant.git.filter
import com.github.andersonarc.mcprotocollib_assistant.git.git
import com.github.andersonarc.mcprotocollib_assistant.git.version
import com.github.andersonarc.mcprotocollib_assistant.misc.Arguments
import java.io.File
import java.util.concurrent.TimeUnit

fun load(arguments: Arguments) {
    println("entering load stage")

    /**
     * unpack arguments
     */
    val directory = arguments.directory
    val repository = arguments.repository
    val executor = arguments.executor

    /**
     * last version cloned from git and copied as source to other versions
     */
    val cloned = File(directory, "source")
    git(
        File(directory),
        "clone",
        repository,
        "source"
    )
    println("cloned $repository from git to $cloned")
    val latest = version(cloned)
    println("latest version $latest detected")
    val source = File(directory, latest)
    cloned.renameTo(source)

    /**
     * load and filter commits
     */
    val list = commits(source)
    println("loaded ${list.size} commits")
    val filtered = filter(list)
    println("found ${filtered.size} versions")

    /**
     * save each commit to separate folder
     */
    for (entry in filtered) {
        executor.execute {
            println("loading " + entry.second)
            val current = File(directory, entry.second)
            if (current.exists()) current.deleteRecursively()
            source.copyRecursively(current)
            git(
                current,
                "reset",
                "--hard",
                entry.first
            )
        }
    }
    println("awaiting for all tasks to finish")
    executor.awaitTermination(10, TimeUnit.MINUTES)
    println("leaving load stage")
}