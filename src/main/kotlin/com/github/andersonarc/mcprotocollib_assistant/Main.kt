package com.github.andersonarc.mcprotocollib_assistant

import com.github.andersonarc.mcprotocollib_assistant.stage.build
import com.github.andersonarc.mcprotocollib_assistant.stage.init
import com.github.andersonarc.mcprotocollib_assistant.stage.load
import kotlin.system.exitProcess


fun main(args: Array<String>) {
     println("mcprotocollib assistant started")
     val arguments = init(args)

     if (arguments.mode.canLoad()) {
          load(arguments)
     }
     if (arguments.mode.canBuild()) {
          build(arguments)
     }

     println("exit")
     exitProcess(0)
}