package org.vismayb.mocha;

import java.util.Arrays;

public class Test {
    private static final double NEGATIVE_PIE = 3.1415;
    private static final long nanoseconds = -23423424324L;

    public static void main(String[] args) {
        System.out.println("String var Literal");

        // Here is a comment that is single line for if let var "Hello"
        Arrays.stream(args).anyMatch("Hi!"::equals);

        new String().length();

        var pi = Math.PI;
        pi = NEGATIVE_PIE;
    }

    /**
     * Prints a triangle according to certain requirements
     * for if let var "String inside javadoc"
     * @param base base width of the triangle
     * @return -1
     */
    public int printTriangle(int base) {
        for(int i = 0; i < base; i++) {
            for(int j = 0; j < i; j++) {
                System.out.println("* /*  Here is a comment inside the string literal  */");
            }
            System.out.println();
        }

        /*
         * // Comment here
         *  Here is a multi-line comment "String inside"
         * for if let var
         */
        return (int)-1.0;
    }
}
