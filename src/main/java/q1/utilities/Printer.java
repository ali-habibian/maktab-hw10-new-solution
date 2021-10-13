package q1.utilities;

public class Printer {
    public static String formatter(Object str) {
        return String.format("%-5s", str + "");
    }

    public static String formatter(Object str, int spaces) {
        return String.format("%-" + spaces + "s", str + "");
    }

    public static void printErrorMessage(String msg) {
        System.out.println();
        System.out.println("| Error: " + msg + " |");
        printWaitingMessage();
    }

    public static void printInfoMessage(String msg) {
        System.out.println();
        System.out.println(">>> " + msg + " <<<");
        printWaitingMessage();
    }

    public static void printWaitingMessage() {
        System.out.println("_____________ press Enter to continue...");
        Input.getOptionalStringInputValue("");
    }
}
