/**
 * Main - Entry point for the IR Search Engine application.
 *
 * This class serves as the application's entry point. It creates and initializes
 * the SearchEngineController, which handles all orchestration and user interaction.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Create the search engine controller
            SearchEngineController controller = new SearchEngineController();

            // Initialize the search engine (process documents and build index)
            controller.initialize();

            // Start the interactive menu loop
            controller.start();

        } catch (Exception e) {
            System.err.println("❌ Fatal error: " + e.getMessage());
            System.exit(1);
        }
    }
}