package org.vismayb.mocha.backend.util

import java.util.regex.Pattern

fun generateKeywordPattern(keywords: Array<String>): Pattern {
    return Pattern.compile("\\b(" + keywords.joinToString("|")  + ")\\b")
}
