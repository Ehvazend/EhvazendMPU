package net.ehvazend.mpu

import net.ehvazend.mpu.data.HTML_DataModVersion
import org.jsoup.Jsoup

object HTML_Updater {
    // TODO: 27.06.2017 HTML Parser with processing Curse mods

    private val rootAddress = "https://minecraft.curseforge.com"

    fun checkMod(URL: String): ArrayList<HTML_DataModVersion> {
        return ArrayList<HTML_DataModVersion>().also { it_ ->
            Jsoup.connect(URL).get().getElementsByAttributeValue("class", "project-file-list-item").forEach { pageElement ->
                pageElement.getElementsByAttributeValue("class", "overflow-tip twitch-link").also {
                    it_.add(HTML_DataModVersion(rootAddress + it.attr("href"), it.attr("data-name")))
                }
            }
        }
    }

    fun updateMod() {
        // TODO: 01.07.2017 Updating mod with this function
    }
}