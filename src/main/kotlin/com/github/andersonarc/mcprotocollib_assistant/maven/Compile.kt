package com.github.andersonarc.mcprotocollib_assistant.maven

import java.io.File

fun compile(directory: File) {
    val builder = ProcessBuilder("mvn", "clean", "install")
    builder.directory(directory)
    builder.redirectOutput(File(directory, "log.txt"))
    builder.start().waitFor()
}