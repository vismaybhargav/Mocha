package org.vismayb.mocha.logging

import java.io.File

class LoggingService(
    private val filePath: String = "",
) {
    private val file: File = safeCreateFile(filePath)
    private val totalBuffer: StringBuilder = StringBuilder()
    private val intermediateBuffer: StringBuilder = StringBuilder()

    private fun safeCreateFile(filePath: String): File {
        val file = File(filePath)
        if(!file.exists()) file.createNewFile()
        file.writeText("")
        return file
    }

    fun writeLog(shouldAppend: Boolean) {
        if(filePath.isEmpty()) {
            println(intermediateBuffer)
        } else if(shouldAppend) {
            file.appendText("${intermediateBuffer}\n")
        } else {
            file.writeText(totalBuffer.toString())
        }
    }

    fun rewriteIntermediateBuffer(content: String) {
        totalBuffer.append(intermediateBuffer.toString())
        clearIntermediateBuffer()
        intermediateBuffer.append(content)
    }

    private fun clearIntermediateBuffer() = intermediateBuffer.setLength(0)
}