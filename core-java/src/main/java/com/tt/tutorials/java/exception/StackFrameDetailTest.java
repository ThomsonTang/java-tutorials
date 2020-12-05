package com.tt.tutorials.java.exception;

/**
 * A Sample Program That Prints the Details of the Stack Frames of a Thread.
 *
 * @author Thomson Tang
 */
public class StackFrameDetailTest {
    public static void m1() {
        m2();
    }

    public static void m2() {
        m3();
    }

    public static void m3() {
        // Create a Throwable object that will hold the stack state at this point for the thread that executes the following statement.
        Throwable t = new Throwable();

        // Get stack trace elements
        StackTraceElement[] frames = t.getStackTrace();

        // Print details about the stack frames.
        printStackDetails(frames);
    }

    public static void printStackDetails(StackTraceElement[] frames) {
        System.out.println("stack trace frame count: " + frames.length);

        for (int i = 0; i < frames.length; i++) {
            // Get frame details.
            int frameIndex = i;
            String fileName = frames[i].getFileName();
            String className = frames[i].getClassName();
            String methodName = frames[i].getMethodName();
            int lineNumber = frames[i].getLineNumber();

            // Print frame details
            System.out.println("Frame Index: " + frameIndex);
            System.out.println("File Name: " + fileName);
            System.out.println("Class Name: " + className);
            System.out.println("Method Name: " + methodName);
            System.out.println("Line Number: " + lineNumber);
            System.out.println("----------------------------");
        }
    }

    public static void main(String[] args) {
        m1();
    }
}
