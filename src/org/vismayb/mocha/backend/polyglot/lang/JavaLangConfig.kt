package org.vismayb.mocha.backend.polyglot.lang

import org.vismayb.mocha.backend.token.Token
import org.vismayb.mocha.backend.util.generateKeywordPattern
import org.vismayb.mocha.view.util.generateColor
import javafx.scene.paint.Color
import java.util.regex.Pattern

private val keywords: Array<String> = arrayOf(
    "abstract", "assert", "boolean", "break", "case",
    "catch", "class", "const", "continue", "default", "do",
    "else", "enum", "extends", "false", "final", "finally",
    "for", "goto", "if", "implements", "import",
    "instanceof", "interface", "native", "new",
    "package", "private", "protected", "public", "return",
    "static", "strictfp", "super", "switch", "synchronized", "this",
    "throw", "throws", "transient", "try", "volatile",
    "while", "var"
)

public val theme: Map<Token.TokenType, Color> = mapOf(
    Token.TokenType.NUMBER_LITERAL to generateColor(215, 199, 129),
    Token.TokenType.STRING_LITERAL to generateColor(255, 127, 108),
    Token.TokenType.KEYWORD        to generateColor(249, 122, 176),
    Token.TokenType.COMMENT        to generateColor(122, 126, 133),
    Token.TokenType.CALL           to generateColor(116, 194, 179),
    Token.TokenType.CLASS          to generateColor(129, 230, 255)
)

val primitiveTypes: Array<String> = arrayOf(
    "void", "int", "double", "short", "long", "byte", "char"
)

val instanceModifiers: Array<String> = arrayOf(
    "this", "super"
)

val keywordPattern: Pattern = generateKeywordPattern(keywords.plus(primitiveTypes).plus(instanceModifiers))
val callPattern: Pattern = Pattern.compile("\\b\\w+(?=\\s*\\()")
val classPattern: Pattern = Pattern.compile("\\b[A-Z][a-zA-Z0-9]*\\b")
val singleCommentPattern: Pattern = Pattern.compile("//[^\n]*")
val multiCommentPattern: Pattern = Pattern.compile("/\\*[\\s\\S]*?\\*/\n")

val stringPattern: Pattern = Pattern.compile("\"(?:\\\\.|[^\"\\\\])*\"");

/**
 * Works with +/-, f/F, l/L, decimals and integers. OPEN-AI o1 is SO SMART!!
 */
val numberPattern: Pattern = Pattern.compile("([-]?(\\d+(\\.\\d+)?|\\.\\d+)[FfLl]?)")
