package com.github.andersonarc.mcsloader

import com.github.andersonarc.mcsloader.stage.build
import com.github.andersonarc.mcsloader.stage.init
import com.github.andersonarc.mcsloader.stage.load
import kotlin.system.exitProcess


fun main(args: Array<String>) {
     println("mcprotocollib builder started")
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