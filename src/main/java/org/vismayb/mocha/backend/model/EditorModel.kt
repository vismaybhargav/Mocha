package org.vismayb.mocha.backend.model

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.AnnotationDeclaration
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.EnumDeclaration
import com.github.javaparser.ast.body.RecordDeclaration
import com.github.javaparser.ast.body.TypeDeclaration

import org.vismayb.mocha.backend.polyglot.lang.*
import org.vismayb.mocha.backend.token.DeclarationType

import org.vismayb.mocha.backend.token.Token

import java.io.File
import java.util.regex.Pattern

class EditorModel(private val file: File) {
    private var tokens: MutableList<Token> = mutableListOf()
    var lines: List<String> = file.readLines() // Use a more robust approach
    var compilationUnit: CompilationUnit = StaticJavaParser.parse(file)
    var declType: DeclarationType = determineFileDeclarationType()

    init {
        tokenizeFile()
        tokens.sort()
        filterContainedTokensByPriority()
    }

    /**
     * Tokenizes the entire file and adds to the list of tokens
     */
    private fun tokenizeFile(){
        var i = 0
        file.forEachLine {
            matchAllTokens(it, keywordPattern,       Token.TokenType.KEYWORD,        i)
            matchAllTokens(it, numberPattern,        Token.TokenType.NUMBER_LITERAL, i)
            matchAllTokens(it, singleCommentPattern, Token.TokenType.COMMENT,        i)
            matchAllTokens(it, stringPattern,        Token.TokenType.STRING_LITERAL, i)
            matchAllTokens(it, callPattern,          Token.TokenType.CALL,           i)
            matchAllTokens(it, classPattern,         Token.TokenType.CLASS,          i)
            i++
        }
    }

    /**
     * Matches all the tokens based on the pattern and tokenType. (Adds to the token array)
     * @param pattern Pattern to match
     * @param tokenType type of the token that is being matched
     * @param text text to match
     */
    private fun matchAllTokens(text: String, pattern: Pattern, tokenType: Token.TokenType, lineNumber: Int) {
        val matcher = pattern.matcher(text)

        while(matcher.find()) {
            tokens.add(Token(matcher.start(), matcher.end(), matcher.group(), tokenType, lineNumber))
        }
    }


    /**
     * Removes tokens from the token list if they are contained within a higher priority token
     */
    private fun filterContainedTokensByPriority() {
        // get all the high priority tokens (string and comment)
        val highPriorityTokens = getTokensByType(Token.TokenType.COMMENT)
            .plus(getTokensByType(Token.TokenType.STRING_LITERAL))
            .plus(getTokensByType(Token.TokenType.KEYWORD))
            .plus(getTokensByType(Token.TokenType.CALL))

        // Remove if a token is contained within a high priority token
        highPriorityTokens.forEach { highPriorityToken ->
            tokens.removeIf { token ->
                // We check if the token is not the same because we removed the check for
                // priority to be below the string level
                highPriorityToken != token && token.isContainedWithin(highPriorityToken)
            }
        }
    }

    private fun getTokensByType(type: Token.TokenType): List<Token> = tokens.filter { it.type == type }

    private fun getTokensByLineNumber(lineNumber: Int): List<Token> = tokens.filter { it.lineNumber == lineNumber }

    private fun determineFileDeclarationType(): DeclarationType {
        val mainType: TypeDeclaration<*>
        if(compilationUnit.types.size == 1) {
            mainType = compilationUnit.types[0]
        } else {
            mainType = compilationUnit.types.firstOrNull { it.nameAsString == file.nameWithoutExtension } ?: compilationUnit.types[0]
        }

        return determineTypeDeclaration(mainType)
    }

    private fun determineTypeDeclaration(type: TypeDeclaration<*>): DeclarationType {
        return when(type) {
            is ClassOrInterfaceDeclaration -> {
                if(type.isInterface) {
                    DeclarationType.INTERFACE
                } else {
                    if(doesTypeExtend(type, "java.lang.Exception")) {
                        DeclarationType.EXCEPTION
                    } else {
                        DeclarationType.CLASS
                    }
                }
            }
            is RecordDeclaration -> DeclarationType.RECORD
            is EnumDeclaration -> DeclarationType.ENUM
            is AnnotationDeclaration -> DeclarationType.ANNOTATION
            else -> DeclarationType.CLASS
        }
    }

    private fun doesTypeExtend(type: TypeDeclaration<*>, qualifiedName: String): Boolean {
        return type.resolve().asClass().allAncestors.firstOrNull { it.qualifiedName == qualifiedName } != null
    }
}