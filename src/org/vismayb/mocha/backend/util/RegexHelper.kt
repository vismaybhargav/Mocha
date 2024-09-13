package org.vismayb.mocha.backend.util

import java.util.regex.Pattern

open class RegexHelper {
    companion object {
        fun generateKeywordPattern(keywords: Array<String>): Pattern {
            return Pattern.compile("\\b(" + keywords.joinToString("|")  + ")\\b")
        }
    }
}
