/**
 * ConsoleHelper - Utility class for clean console output and input handling.
 * Centralizes all console I/O to keep the code organized and reusable.
 */
public class ConsoleHelper {

    private static final String SEPARATOR_LINE = "═".repeat(60);
    private static final String DASH_LINE = "─".repeat(60);

    // Output methods
    public static void printHeader(String title) {
        println();
        println(SEPARATOR_LINE);
        println("  " + title);
        println(SEPARATOR_LINE);
    }

    public static void printSubHeader(String title) {
        println();
        println(DASH_LINE);
        println("  " + title);
        println(DASH_LINE);
    }

    public static void printSuccess(String message) {
        println("✅ " + message);
    }

    public static void printError(String message) {
        System.err.println("❌ " + message);
    }

    public static void printInfo(String message) {
        println("ℹ️  " + message);
    }

    public static void printWarning(String message) {
        println("⚠️  " + message);
    }

    public static void println() {
        System.out.println();
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static String prompt(String question) {
        print(question);
        return new java.util.Scanner(System.in).nextLine().trim();
    }

    // Menu display
    public static void showMainMenu() {
        printSubHeader("═══════════════════════════════════════════════════════════");
        println("          🔍 SEARCH ENGINE - MAIN MENU");
        println("═══════════════════════════════════════════════════════════");
        println();
        println("  1️⃣  Ranked Search       - Search and rank documents by relevance");
        println("  2️⃣  Proximity Search    - Find terms within a specific distance");
        println("  3️⃣  Print Index         - Display the complete inverted index");
        println("  4️⃣  Exit                - Exit the application");
        println();
        print("  Please choose an option (1-4): ");
    }

    public static void showWelcome() {
        println();
        println("╔════════════════════════════════════════════════════════════╗");
        println("        ║ 🔎 WELCOME TO OUR POOR SEARCH ENGINE 🔎  ║");
        println("╚════════════════════════════════════════════════════════════╝");
        println();
    }

    public static void showGoodbye() {
        println();
        println("╔════════════════════════════════════════════════════════════╗");
        println("║               اي خدمة😉           ║          ");
        println("║     ادينا الفل مارك احنا غلابة 😢  ║          ");
        println("╚════════════════════════════════════════════════════════════╝");
        println();
    }

    public static void showInitializing() {
        println();
        printInfo("Initializing search engine...");
        printInfo("Processing Arabic documents...");
        printInfo("Processing English documents...");
        printInfo("Building positional index...");
        printSuccess("Search engine ready!");
    }

    public static void showResults(java.util.List<?> results, String resultType) {
        if (results.isEmpty()) {
            printWarning("No " + resultType + " found.");
        } else {
            printSuccess(resultType + " found: " + results.size() + " result(s)");
            println();
            int rank = 1;
            for (Object result : results) {
                System.out.printf("  %d. %s%n", rank, result.toString());
                rank++;
            }
        }
        println();
    }

    public static void showIndexPrinting() {
        printSubHeader("INVERTED INDEX");
    }

    public static void showProcessing(String message) {
        println("⏳ " + message);
    }
}

