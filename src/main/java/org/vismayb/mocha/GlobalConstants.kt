package org.vismayb.mocha

import java.io.File

/**
 * Yes I know these are not constants. Deal with it. >:P
 */
class GlobalConstants {
    companion object {
        var isLoggingEnabled = false
        var logToFile = false
        var isDevMode = false
        var width = 800
        var height = 600
        var defaultFile: File? = null
    }
}