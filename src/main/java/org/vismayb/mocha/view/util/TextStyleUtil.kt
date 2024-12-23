package org.vismayb.mocha.view.util

import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontSmoothingType
import javafx.scene.text.FontWeight
import javafx.scene.text.Text

class TextStyleUtil(text: String?) : Text(text) {
    companion object {
        @JvmStatic
        fun getDefaultStyle(message: String, color: Color): Text {
            val text = TextStyleUtil(message)
            text.isUnderline = false
            text.font = Font.font("JetBrains Mono", FontWeight.SEMI_BOLD, 15.0)
            text.fill = color
            text.fontSmoothingType = FontSmoothingType.LCD
            return text
        }
    }
}
