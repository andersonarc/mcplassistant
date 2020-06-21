package com.github.andersonarc.mcplassistant.git

import java.io.File

fun commits(source: File): List<Pair<String, String>> {
    com.github.andersonarc.mcplassistant.misc.assert(
        source.exists(),
        "source directory \"$source\" must exist"
    )
    val list = ArrayList<Pair<String, String>>()
    var last: String? = null
    while (true) {
        /** revert to previous com.github.andersonarc.`mcprotocollib-assistant`.git.commit */
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