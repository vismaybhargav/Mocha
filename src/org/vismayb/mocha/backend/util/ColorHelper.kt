package org.vismayb.mocha.backend.util

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
        getNormalizedColorValue(blue),
        getNormalizedColorValue(green),
        getNormalizedColorValue(alpha)
    )
}

/**
 * @param red red value [0, 256)
 * @param blue blue value [0, 256)
 * @param green green value [0, 256)
 */
fun generateColor(red: Int, blue: Int, green: Int): Color {
    return Color.color(
        getNormalizedColorValue(red),
        getNormalizedColorValue(blue),
        getNormalizedColorValue(green),
    )
}

/**
 * @param color Color
 */
fun getHexString(color: Color): String {
    //TODO: Implement hex color parsing
    throw NotImplementedError()
}

/**
 * @param value a rgba value of a color [0, 256)
 */
private fun getNormalizedColorValue(value: Int): Double {
    return (value / 256).toDouble();
}