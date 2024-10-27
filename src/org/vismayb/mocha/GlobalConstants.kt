package org.vismayb.mocha

import java.io.File

/**
 * Yes I know these are not constants. Deal with it. >:P
 */
class GlobalConstants {
    companion object {
        var loggingEnabled = false
        var logToFile = false
        var devMode = false
        var width = 800
        var height = 600
        var defaultFile: File? = null
    }
}