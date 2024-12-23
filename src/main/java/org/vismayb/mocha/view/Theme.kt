package org.vismayb.mocha.view

import javafx.scene.paint.Color
import org.vismayb.mocha.backend.token.Token.TokenType
import org.vismayb.mocha.view.util.generateColor

 class Theme(
     val editorTokenMap: Map<TokenType, Color>,
     val editorBackground: Color,
     val editorForeground: Color,
     val editorHighlightedBackground: Color,
     val gutterForeground: Color,
     val gutterHighlightedForeground: Color,
) {
    companion object {
        val XCodeTheme: Theme = Theme(
            mapOf(
                TokenType.NUMBER_LITERAL to generateColor(215, 199, 129),
                TokenType.STRING_LITERAL to generateColor(255, 127, 108),
                TokenType.KEYWORD        to generateColor(249, 122, 176),
                TokenType.COMMENT        to generateColor(122, 126, 133),
                TokenType.CALL           to generateColor(116, 194, 179),
                TokenType.CLASS          to generateColor(129, 230, 255)
            ),
            generateColor(41, 42, 47),
            generateColor(223, 223, 223),
            generateColor(38, 40, 46),
            generateColor(71, 76, 85),
            generateColor(161, 163, 171)
        )
    }
}