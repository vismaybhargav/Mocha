package org.vismayb.mocha.view.util

import javafx.scene.paint.Color

/**
 * @param red red value [0, 256)
 * @param blue blue value [0, 256)
 * @param green green value [0, 256)
 * @param alpha alpha valye [0, 256)
 */
fun generateColor(red: Int, blue: Int, green: Int, alpha: Int): Color {
    return Color.color(
        getNormalizedColorValue(red),
        getNormalizedColorValue(green),
        getNormalizedColorValue(blue),
        getNormalizedColorValue(alpha)
    )
}

/**
 * @param red red value [0, 256)
 * @param blue blue value [0, 256)
 * @param green green value [0, 256)
 */
fun generateColor(red: Int, green: Int, blue: Int): Color {
    return Color.color(
        getNormalizedColorValue(red),
        getNormalizedColorValue(green),
        getNormalizedColorValue(blue),
    )
}

/**
 * @param color Color
 */
fun getHexString(color: Color): String {
    var hex = "#"
    //hex += color.
    throw NotImplementedError()
}

fun getReadableColorString(color: Color): String = "RGB(${(color.red * 255).toInt()}, ${(color.green * 255).toInt()}, ${(color.blue * 255).toInt()})"

/**
 * @param value a rgba value of a color [0, 256)
 */
private fun getNormalizedColorValue(value: Int): Double = (value / 255.0)