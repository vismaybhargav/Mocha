package org.vismayb.mocha

import org.vismayb.mocha.view.Theme
import java.io.File

/**
 * Implementation Note: These are set as var because they are more like a data storage
 */
class GlobalConstants {
    companion object {
        var isLoggingEnabled = false
        var logToFile = false
        var isDevMode = false
        var width = 800
        var height = 600
        var defaultFile: File? = null
        var theme: Theme = Theme.XCodeTheme
    }
}

