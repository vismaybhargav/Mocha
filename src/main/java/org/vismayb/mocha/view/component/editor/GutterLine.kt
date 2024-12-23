package org.vismayb.mocha.view.component.editor

import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.HBox
import javafx.scene.text.Text
import org.vismayb.mocha.GlobalConstants.Companion.theme
import org.vismayb.mocha.view.util.TextStyleUtil

class GutterLine(lineNumber: Int) : HBox() {
    var textObject: Text? = TextStyleUtil.getDefaultStyle(
        lineNumber.toString(),
        theme.gutterForeground
    )

    init {
        children.add(
            TextStyleUtil.getDefaultStyle(
                lineNumber.toString(),
                theme.gutterForeground
            )
        )

        background = Background.fill(theme.editorBackground)
        padding = Insets(0.0, 0.0, 0.0, 0.0)
        //border = Border.stroke(generateColor(55, 55, 55))
    }
}
