package com.github.andersonarc.mcprotocollib_assistant

import com.github.andersonarc.mcprotocollib_assistant.stage.build
import com.github.andersonarc.mcprotocollib_assistant.stage.init
import com.github.andersonarc.mcprotocollib_assistant.stage.load
import kotlin.system.exitProcess


fun main(args: Array<String>) {
     println("mcprotocollib assistant started")
     val arguments = init(args)

     load(arguments)
     build(arguments)

     println("leaving")
     exitProcess(0)
}