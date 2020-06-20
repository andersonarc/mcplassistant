import java.io.File
import java.io.FileInputStream
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.last
import kotlin.collections.reversed
import kotlin.system.exitProcess


fun main(args: Array<String>) {
     assert(args.size > 1, "2 arguments required - path to working directory and git repository")

     val directory = args[0]
     println("working directory set to \"$directory\"")
     val repository = args[1]
     println("repository set to \"$repository\"")

     val file = File(directory)
     file.deleteRecursively()
     file.mkdir()
     println("working directory cleaned")

     println("mcprotocollib assistant started")

     /** last version cloned from git and copied as source to other versions */
     val cloned = File(directory, "source")
     git(file, "clone", repository, "source")
     println("cloned $repository from git to $cloned")
     val latest = version(cloned)
     val source = File(directory, latest)
     cloned.renameTo(source)
     println("latest version $latest detected")

     val list = load(source)
     println("loaded ${list.size} commits")

     val filtered = filter(list)
     println("found ${filtered.size} versions")

     for (entry in filtered) {
          println("loading " + entry.second)
          val current = File(directory, entry.second)
          if (current.exists()) current.deleteRecursively()
          source.copyRecursively(current)
          git(current, "reset", "--hard", entry.first)
     }

     println("leaving")
     exitProcess(0)
}

fun load(source: File): List<Pair<String, String>> {
     assert(source.exists(), "source directory \"$source\" must exist")
     val list = ArrayList<Pair<String, String>>()
     var last: String? = null
     while (true) {
          /** revert to previous commit */
          git(source, "reset", "--hard", "HEAD~")

          val version = version(source)
          val commit = commit(source)

          /** check if no more commits */
          if (last == commit) break
          last = commit
          list.add(Pair(commit, version))
     }
     return list
}

fun filter(list: List<Pair<String, String>>): List<Pair<String, String>> {
     val result = ArrayList<Pair<String, String>>()
     var last = list.last()
     for (element in list.reversed().subList(1, list.size)) {
          if (last.second != element.second) {
               result.add(last)
          }
          last = element
     }
     return result
}

fun commit(directory: File): String {
     val current = git(directory, "rev-parse", "HEAD").inputStream.readAllBytes()
     return String(current).replace("\n", "")
}

fun version(directory: File): String {
     val file = FileInputStream(File(directory, "pom.xml")).readAllBytes()
     return String(file).substringAfter("<version>").substringBefore("</version>").substringBefore("-")
}

fun git(directory: File, vararg options: String): Process {
     val builder = ProcessBuilder("git", *options)
     builder.directory(directory)
     val process = builder.start()
     process.waitFor()
     return process
}

fun assert(condition: Boolean, message: String) {
     if (!condition) {
          System.err.println(message)
          exitProcess(1)
     }
}
