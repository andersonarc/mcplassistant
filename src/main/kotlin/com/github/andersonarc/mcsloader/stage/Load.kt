package com.github.andersonarc.mcsloader.stage

import com.github.andersonarc.mcsloader.git.commits
import com.github.andersonarc.mcsloader.git.filter
import com.github.andersonarc.mcsloader.git.git
import com.github.andersonarc.mcsloader.git.version
import com.github.andersonarc.mcsloader.misc.Arguments
import java.io.File
import java.util.concurrent.Future
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
     * clear working directory
     */
    val file = File(directory)
    if (file.exists()) {
        if (file.isDirectory) {
            file.deleteRecursively()
        } else {
            file.delete()
        }
    }
    file.mkdirs()
    println("cleaned $directory")

    /**
     * last version cloned from github and copied as source to other versions
     */
    val cloned = File(directory, "source")
    git(
        cloned.parentFile,
        "clone",
        repository,
        cloned.name
    )
    println("cloned $repository from github to $cloned")
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
    val futures = ArrayList<Future<*>>()
    for (entry in filtered) {
        futures.add(executor.submit {
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
        })
    }
    println("awaiting for all tasks to finish")
    futures.forEach { it.get(60, TimeUnit.SECONDS) }
    println("leaving load stage")
}