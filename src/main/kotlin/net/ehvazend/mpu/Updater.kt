package net.ehvazend.mpu

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class Updater : Thread() {
    // TODO: 27.06.2017 HTML Parser with processing Curse mods

    override fun run() {
        checkMod()
    }

    fun checkMod() : String{
        val page: Document = Jsoup.connect("https://minecraft.curseforge.com/projects/just-enough-items-jei/files").get()
        return page.title()
    }
}