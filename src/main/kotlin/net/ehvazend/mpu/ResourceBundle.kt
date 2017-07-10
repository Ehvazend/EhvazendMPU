package net.ehvazend.mpu

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

object ResourceBundle : java.util.ResourceBundle.Control() {
    @Throws(IllegalAccessException::class, InstantiationException::class, IOException::class)
    override fun newBundle(baseName: String, locale: Locale, format: String, loader: ClassLoader, reload: Boolean): java.util.ResourceBundle {
        val bundleName = toBundleName(baseName, locale)
        val resourceName = toResourceName(bundleName, "properties")
        var bundle: java.util.ResourceBundle? = null
        var stream: InputStream? = null

        if (reload) {
            val url = loader.getResource(resourceName)
            if (url != null) {
                val connection = url.openConnection()
                if (connection != null) {
                    connection.useCaches = false
                    stream = connection.getInputStream()
                }
            }
        } else stream = loader.getResourceAsStream(resourceName)

        if (stream != null) try {
            // Only this line is changed to make it to read properties files as UTF-8.
            bundle = PropertyResourceBundle(InputStreamReader(stream, "UTF-8"))
        } finally {
            stream.close()
        }
        return bundle!!
    }

    fun getBundle(baseName: String, locale: Locale): java.util.ResourceBundle {
        return ResourceBundle.newBundle(baseName, locale, "java.properties", javaClass.classLoader, false)
    }
}