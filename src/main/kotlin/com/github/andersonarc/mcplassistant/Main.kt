package com.github.andersonarc.mcplassistant

import com.github.andersonarc.mcplassistant.stage.build
import com.github.andersonarc.mcplassistant.stage.init
import com.github.andersonarc.mcplassistant.stage.load
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