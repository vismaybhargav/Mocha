package org.vismayb.mocha.backend.ast

import org.vismayb.mocha.backend.token.Token

class ASTNode(val token: Token, var parent: ASTNode?) {
    private val children: MutableList<ASTNode> = ArrayList()

    fun addChild(child: ASTNode) {
        children.add(child)
    }

    fun depth(): Int {
        if (parent == null) {
            return 0
        }

        return -1 //TODO: Implement depth
    }
}
