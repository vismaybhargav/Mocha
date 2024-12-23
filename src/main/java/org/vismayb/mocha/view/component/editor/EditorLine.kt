package org.vismayb.mocha.view.component.editor

import javafx.scene.layout.Background
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import org.vismayb.mocha.GlobalConstants.Companion.theme
import org.vismayb.mocha.backend.token.Token

/**
 * This class represents one viewable line in the editor including the Gutter and TextLine
 */
class EditorLine(text: String, tokens: MutableList<Token>, lineNumber: Int) : HBox() {
    var gutterLine: GutterLine = GutterLine(lineNumber)
    var textLine: TextLine = TextLine(text, tokens, lineNumber)
    var isHighlighted: Boolean = false

    init {
        generateView()
    }

    private fun generateView() {
        children.addAll(gutterLine, textLine)
    }

    private fun setEditorLineBackground(color: Color) {
        gutterLine.background = Background.fill(color)
        textLine.background = Background.fill(color)
    }

    private fun highlightLine() {
        setEditorLineBackground(theme.editorHighlightedBackground)
        gutterLine.textObject?.fill = theme.gutterHighlightedForeground
    }

    private fun unHighlightLine() {
        setEditorLineBackground(theme.editorBackground)
        gutterLine.textObject?.fill = theme.gutterForeground
    }

    fun highlight(should: Boolean) {
        if (should) {
            highlightLine()
        } else {
            unHighlightLine()
        }
        isHighlighted = should
    }
}
