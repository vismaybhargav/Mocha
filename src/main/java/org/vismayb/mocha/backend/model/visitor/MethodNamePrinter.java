package org.vismayb.mocha.backend.model.visitor;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodNamePrinter extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
    }
}
