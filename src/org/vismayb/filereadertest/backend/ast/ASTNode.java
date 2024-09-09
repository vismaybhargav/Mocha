package org.vismayb.filereadertest.backend.ast;

import org.vismayb.filereadertest.backend.token.Token;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    final Token token;
    ASTNode parent;
    private final List<ASTNode> children;

    public ASTNode(Token token, ASTNode parent) {
        this.token = token;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }

    public int depth() {
        if(parent == null) {
            return 0;
        }

        return -1; //TODO: Implement depth
    }
}
