package com.github.andersonarc.mcprotocollib_assistant.maven

import com.github.andersonarc.mcprotocollib_assistant.misc.process
import java.io.File

fun compile(directory: File) {
    process(directory, "mvn", "clean", "install")
}