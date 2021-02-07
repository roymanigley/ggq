package ch.hftm.ggq.cli;

public class CliPrinter {

    public static void info(String message) {
        print("[+] " + message);
    }

    private static void print(String message) {
        System.out.println(message);
    }

    public static void error(String message) {
        print("[!] " + message);
    }

    public static void question(String message) {
        print("[?] " + message);
    }
}
