package net.ehvazend.mpu

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

object Updater{
    // TODO: 27.06.2017 HTML Parser with processing Curse mods

    private val rootAddress = "https://minecraft.curseforge.com"

    fun checkMod(URL: String): List<Array<String>> {
        val list = ArrayList<Array<String>>()

        val page: Document = Jsoup.connect(URL).get()
        val pageElements: Elements = page.getElementsByAttributeValue("class", "project-file-list-item")

        pageElements.forEach { pageElement: Element ->
            val pageData: Elements = pageElement.getElementsByAttributeValue("class", "overflow-tip")
            val pageVersions: Elements = pageElement.getElementsByAttributeValue("class", "version-label")

            list.add(arrayOf(rootAddress + pageData.attr("href"), pageData.attr("data-name"), pageVersions.text()))
        }

        return list
    }

    fun updateMod() {
        // TODO: 01.07.2017 Updating mod with this function
    }
}