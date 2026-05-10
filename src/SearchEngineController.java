import features.Indexing.PositionalIndex;
import features.Query.QueryProcessor;
import features.Query.SearchResult;
import features.Text_Preprocessing.Arabic.ArabicPipeline;
import features.Text_Preprocessing.english.EnglishTextProcessor;

import java.util.List;
import java.util.Scanner;

/**
 * SearchEngineController - Main orchestrator for the search engine application.
 *
 * Responsibilities:
 * - Initialize the search engine (document processing, indexing)
 * - Present interactive menu to the user
 * - Route user choices to appropriate handlers
 * - Manage Scanner lifecycle
 * - Separate UI logic from business logic
 */
public class SearchEngineController {

    private final Scanner scanner;
    private final PositionalIndex index;
    private QueryProcessor queryProcessor;
    private boolean running;

    // =========================
    // Constructor & Initialization
    // =========================

    public SearchEngineController() {
        this.scanner = new Scanner(System.in);
        this.index = new PositionalIndex();
        this.running = true;
    }

    /**
     * Initialize the search engine by processing documents and building index.
     */
    public void initialize() {
        try {
            ConsoleHelper.showWelcome();
            ConsoleHelper.showInitializing();

            // Process Arabic documents
            ArabicPipeline arabicPipeline = new ArabicPipeline();
            arabicPipeline.processFolder(
                    "src/docs/arabic/",
                    "src/docs/processed/arabic/"
            );

            // Process English documents
            EnglishTextProcessor englishPipeline = new EnglishTextProcessor();
            englishPipeline.processFolder(
                    "src/docs/English/",
                    "src/docs/processed/english/"
            );

            // Build index from processed documents
            index.buildIndex("src/docs/processed/arabic/");
            index.buildIndex("src/docs/processed/english/");
            queryProcessor = new QueryProcessor(index);


        } catch (Exception e) {
            ConsoleHelper.printError("Failed to initialize search engine: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // =========================
    // Main Application Loop
    // =========================

    /**
     * Start the interactive search engine loop.
     */
    public void start() {
        while (running) {
            ConsoleHelper.showMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleRankedSearch();
                    break;
                case "2":
                    handleProximitySearch();
                    break;
                case "3":
                    handlePrintIndex();
                    break;
                case "4":
                    handleExit();
                    break;
                default:
                    ConsoleHelper.printWarning("Invalid choice. Please enter 1-4.");
            }
        }
    }

    // =========================
    // Menu Option Handlers
    // =========================

    /**
     * Handler for ranked search menu option.
     * Asks user for query and displays ranked results.
     */
    private void handleRankedSearch() {
        ConsoleHelper.printSubHeader("RANKED SEARCH");

        // Get query from user
        ConsoleHelper.print("Enter your search query (English or Arabic): ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            ConsoleHelper.printWarning("Query cannot be empty. Please try again.");
            return;
        }

        // Process query
        ConsoleHelper.showProcessing("Processing query: \"" + query + "\"...");
        try {
            List<SearchResult> results = queryProcessor.rankedQuery(query);
            ConsoleHelper.showResults(results, "Search results");
        } catch (Exception e) {
            ConsoleHelper.printError("Error processing query: " + e.getMessage());
        }
    }

    /**
     * Handler for proximity search menu option.
     * Asks user for term1, distance k, and term2.
     */
    private void handleProximitySearch() {
        ConsoleHelper.printSubHeader("PROXIMITY SEARCH");

        // Get proximity search parameters
        ConsoleHelper.println("Enter your proximity Query.");
        String proximityQuery = scanner.nextLine().trim();

        // Process query
        ConsoleHelper.showProcessing("Processing proximity query: \"" + proximityQuery + "\"...");
        try {
            List<SearchResult> results = queryProcessor.proximityQuery(proximityQuery);
            ConsoleHelper.showResults(results, "Proximity results");
        } catch (Exception e) {
            ConsoleHelper.printError("Error processing proximity query: " + e.getMessage());
        }
    }

    /**
     * Handler for print index menu option.
     * Displays the complete inverted index.
     */
    private void handlePrintIndex() {
        ConsoleHelper.printSubHeader("INVERTED INDEX");
        ConsoleHelper.println();
        try {
            index.printIndex();
        } catch (Exception e) {
            ConsoleHelper.printError("Error printing index: " + e.getMessage());
        }
        ConsoleHelper.println();
    }

    /**
     * Handler for exit menu option.
     * Gracefully exits the application.
     */
    private void handleExit() {
        running = false;
        ConsoleHelper.showGoodbye();
        scanner.close();
    }

    // =========================
    // Utility Methods
    // =========================




}

