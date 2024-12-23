package org.vismayb.mocha.view.component.editor;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

/**
 * This class contains every layer of the text reader pane
 * <br><br>
 * <h6>Layers going from bottom to top</h1>
 * <li>ScrollPane</li>
 * <li>HBox containing the gutter and the EditorLine(s)</li>
 * <li>Rectangle (Pane) that represents the cursor</li>
 */
public class ComponentContainer extends StackPane {
    public ComponentContainer() {
        super();
        createView();
    }

    private void createView() {
        var scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
    }
}

