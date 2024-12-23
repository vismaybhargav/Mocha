package org.vismayb.mocha.backend.util

import javafx.scene.control.Alert
import javafx.stage.FileChooser
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.lang.StringBuilder
import java.util.ArrayList
import java.util.Objects
import java.util.Scanner

fun readTextAsLines(file: File): String {
    val text = StringBuilder()
    try {
        val scanner = Scanner(file)
        while (scanner.hasNextLine()) {
            text.append(scanner.nextLine()).append("\n")
        }
        scanner.close()
    } catch (e: FileNotFoundException) {
        println("File not found" + e.message)
    }

    return text.toString()
}

fun getAllLines(file: File): ArrayList<String?> {
    val al = ArrayList<String?>()
    try {
        val scanner = Scanner(file)
        while (scanner.hasNextLine()) {
            al.add(scanner.nextLine())
        }
        scanner.close()
    } catch (e: FileNotFoundException) {
        println("File not found" + e.message)
    }

    return al
}

fun getAllLines(sb: StringBuilder): Array<String?> {
    return sb.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
}

fun getAllLinesAsStringBuilder(file: File): StringBuilder {
    val text = StringBuilder()
    try {
        val scanner = Scanner(file)
        while (scanner.hasNextLine()) {
            text.append(scanner.nextLine()).append("\n")
        }
    } catch (e: FileNotFoundException) {
        println("File not found" + e.message)
    }

    return text
}

fun getLineCount(file: File): Int {
    // get the number of lines in a file, this is more generic for LF and CRLF
    var count = 0
    try {
        val scanner = Scanner(file)
        while (scanner.hasNextLine()) {
            scanner.nextLine()
            count++
        }
        scanner.close()
    } catch (e: FileNotFoundException) {
        count = -1
    }
    return count
}

fun purgeDirectory(directory: File) {
    for (file in Objects.requireNonNull<Array<File>?>(directory.listFiles())) {
        if (file.isDirectory()) {
            purgeDirectory(file)
        } else {
            file.delete()
        }
    }
}

fun getLineCount(str: String): Int {
    // limit set to -1 to ensure lines with only \n are counted
    return str.split("\n".toRegex()).toTypedArray().size
}

fun getFileFromUser(): File? {
    val fc = FileChooser()
    fc.title = "Open File"
    return fc.showOpenDialog(null)
}

fun saveFile(file: File, textToSave: String) {
    try {
        FileWriter(file).use { fileWriter ->
            fileWriter.write(textToSave)
            showAlert("Saved Successfully", "File saved successfully")
        }
    } catch (e: IOException) {
        println("IOException occurred" + e.message)
    }
}

// shows an alert dialog used for saving files
private fun showAlert(title: String?, content: String?) {
    val alert = Alert(Alert.AlertType.INFORMATION)
    alert.title = title
    alert.headerText = null
    alert.contentText = content
    alert.showAndWait()
}
