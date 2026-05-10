# Developer Guide - Extending the Search Engine

This guide explains how to extend the search engine with new features while maintaining the clean architecture.

---

## Common Extension Scenarios

### Scenario 1: Add a New Menu Option

**Example:** Add a "Search Statistics" option to the menu

**Step 1: Update SearchEngineController**

```java
public void start() {
    while (running) {
        ConsoleHelper.showMainMenu();
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1": handleRankedSearch(); break;
            case "2": handleProximitySearch(); break;
            case "3": handlePrintIndex(); break;
            case "4": handleSearchStatistics(); break;    // ← NEW
            case "5": handleExit(); break;                 // ← UPDATED
            default: ConsoleHelper.printWarning("Invalid choice. Please enter 1-5.");
        }
    }
}

// ← NEW HANDLER
private void handleSearchStatistics() {
    ConsoleHelper.printSubHeader("SEARCH STATISTICS");
    
    Map<String, Object> stats = new HashMap<>();
    stats.put("Total Terms in Index", index.getVocabulary().size());
    stats.put("Total Documents Indexed", index.getAllDocuments().size());
    // ... add more statistics ...
    
    ConsoleHelper.println("📊 Statistics:");
    stats.forEach((key, value) -> 
        ConsoleHelper.println(String.format("  %s: %s", key, value))
    );
    ConsoleHelper.println();
}
```

**Step 2: Update ConsoleHelper**

Add the menu item in `showMainMenu()`:

```java
public static void showMainMenu() {
    // ... existing menu items ...
    println("  4️⃣  Search Statistics   - View search index statistics");
    println("  5️⃣  Exit                - Exit the application");
    // ...
}
```

**Result:** New menu option appears; no changes to core logic.

---

### Scenario 2: Add Custom Output Formatting

**Example:** Create a "fancy" results formatter

**Step 1: Add to ConsoleHelper**

```java
public static void showFancyResults(List<SearchResult> results) {
    println("╔════════════════════════════════════════════════════════╗");
    println("║                    SEARCH RESULTS                      ║");
    println("╚════════════════════════════════════════════════════════╝");
    println();
    
    if (results.isEmpty()) {
        println("  No results found. Try a different query.");
        return;
    }
    
    for (int i = 0; i < results.size(); i++) {
        SearchResult result = results.get(i);
        String bar = "▓".repeat((int)(result.getScore() * 30));
        println(String.format("  %d. %s", i+1, result.getDocName()));
        println(String.format("     Score: [%s] %.2f", bar, result.getScore()));
        println();
    }
}
```

**Step 2: Use in controller**

```java
private void handleRankedSearch() {
    // ... existing validation ...
    List<SearchResult> results = queryProcessor.rankedQuery(query);
    ConsoleHelper.showFancyResults(results);  // ← Use new formatter
}
```

---

### Scenario 3: Add Input Validation

**Example:** Limit query length or check character restrictions

**Step 1: Add to ConsoleHelper**

```java
public static boolean isValidQuery(String query) {
    if (query == null || query.isEmpty()) {
        printWarning("Query cannot be empty.");
        return false;
    }
    if (query.length() > 200) {
        printWarning("Query too long. Maximum 200 characters.");
        return false;
    }
    return true;
}
```

**Step 2: Use in controller**

```java
private void handleRankedSearch() {
    ConsoleHelper.print("Enter your search query: ");
    String query = scanner.nextLine().trim();
    
    if (!ConsoleHelper.isValidQuery(query)) {
        return;
    }
    
    // ... continue with search ...
}
```

---

### Scenario 4: Add Search History

**Example:** Track and display previous queries

**Step 1: Add to SearchEngineController**

```java
public class SearchEngineController {
    // ... existing fields ...
    private List<String> searchHistory = new ArrayList<>();
    
    // ... existing code ...
    
    private void handleRankedSearch() {
        // ... get query ...
        
        searchHistory.add(query);  // ← Track query
        
        List<SearchResult> results = queryProcessor.rankedQuery(query);
        ConsoleHelper.showResults(results, "Search results");
    }
    
    private void showSearchHistory() {
        ConsoleHelper.printSubHeader("SEARCH HISTORY");
        if (searchHistory.isEmpty()) {
            ConsoleHelper.println("  No searches yet.");
            return;
        }
        for (int i = 0; i < searchHistory.size(); i++) {
            ConsoleHelper.println(String.format("  %d. %s", i+1, searchHistory.get(i)));
        }
        ConsoleHelper.println();
    }
}
```

**Step 2: Add menu option**

```java
case "5": showSearchHistory(); break;
case "6": handleExit(); break;
```

---

### Scenario 5: Add Result Filtering

**Example:** Filter results by document type

**Step 1: Create filter utility**

```java
public class SearchResultFilter {
    
    public static List<SearchResult> filterByDocType(
            List<SearchResult> results, 
            String docType) {
        return results.stream()
            .filter(r -> r.getDocName().endsWith("." + docType))
            .collect(Collectors.toList());
    }
    
    public static List<SearchResult> filterByScoreThreshold(
            List<SearchResult> results, 
            double minScore) {
        return results.stream()
            .filter(r -> r.getScore() >= minScore)
            .collect(Collectors.toList());
    }
}
```

**Step 2: Use in controller**

```java
private void handleRankedSearch() {
    // ... get results ...
    List<SearchResult> results = queryProcessor.rankedQuery(query);
    
    // Ask for filter
    ConsoleHelper.print("Filter by document type (leave blank for all): ");
    String docType = scanner.nextLine().trim();
    
    if (!docType.isEmpty()) {
        results = SearchResultFilter.filterByDocType(results, docType);
    }
    
    ConsoleHelper.showResults(results, "Filtered results");
}
```

---

### Scenario 6: Add Result Export

**Example:** Save results to a file

**Step 1: Create exporter**

```java
import utils.FileWriter;

public class ResultsExporter {
    
    public static void exportToCSV(
            List<SearchResult> results, 
            String filename) throws IOException {
        StringBuilder csv = new StringBuilder();
        csv.append("Rank,Document,Score\n");
        
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            csv.append(String.format("%d,%s,%.4f\n", 
                i+1, result.getDocName(), result.getScore()));
        }
        
        FileWriter.writeToFile(filename, csv.toString());
    }
}
```

**Step 2: Use in controller**

```java
private void handleRankedSearch() {
    // ... get results ...
    
    ConsoleHelper.print("Save results to file? (y/n): ");
    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
        ConsoleHelper.print("Enter filename: ");
        String filename = scanner.nextLine().trim();
        try {
            ResultsExporter.exportToCSV(results, filename);
            ConsoleHelper.printSuccess("Results saved to " + filename);
        } catch (IOException e) {
            ConsoleHelper.printError("Failed to save: " + e.getMessage());
        }
    }
}
```

---

## Best Practices for Extensions

### 1. **Keep Separation of Concerns**
- 🚫 **Don't:** Add business logic to SearchEngineController
- ✅ **Do:** Create separate utility classes (Filter, Exporter, Stats)

### 2. **Use ConsoleHelper for All Output**
- 🚫 **Don't:** `System.out.println()` scattered in controller
- ✅ **Do:** Use `ConsoleHelper.println()`, `printSuccess()`, etc.

### 3. **Single Responsibility Per Method**
- 🚫 **Don't:** Handler methods that do 5 things
- ✅ **Do:** Break into small focused methods

### 4. **Input Validation at Entry**
- 🚫 **Don't:** Pass unchecked user input to core logic
- ✅ **Do:** Validate in handler before processing

### 5. **Reuse ConsoleHelper Methods**
- 🚫 **Don't:** Create your own output formatting
- ✅ **Do:** Use existing or add new ConsoleHelper methods

### 6. **Document New Features**
```java
/**
 * Export search results to CSV format.
 * 
 * @param results List of SearchResult objects
 * @param filename Output filename for the CSV
 * @throws IOException if file writing fails
 */
public static void exportToCSV(List<SearchResult> results, String filename) 
    throws IOException {
    // ...
}
```

### 7. **Handle Exceptions Gracefully**
```java
try {
    // ... operation ...
} catch (Exception e) {
    ConsoleHelper.printError("Operation failed: " + e.getMessage());
    // Return to menu, don't crash
}
```

### 8. **Test New Features**
Add test queries in `main()` before integrating:

```java
public static void main(String[] args) {
    try {
        SearchEngineController controller = new SearchEngineController();
        controller.initialize();
        
        // Test new feature before starting menu
        // controller.testNewFeature();
        
        controller.start();
    } catch (Exception e) {
        System.err.println("Fatal error: " + e.getMessage());
    }
}
```

---

## Extension Checklist

Before implementing a new feature:

- [ ] Identify responsibility: Where does this belong?
- [ ] Create separate class/method if possible
- [ ] Use ConsoleHelper for all UI output
- [ ] Add input validation
- [ ] Handle exceptions gracefully
- [ ] Add comments/JavaDoc
- [ ] Test with various inputs
- [ ] Update menu in SearchEngineController
- [ ] Update menu display in ConsoleHelper
- [ ] Verify core logic unchanged
- [ ] Test backward compatibility

---

## Extension Examples

### Adding Different Search Ranking Algorithms

```java
// In SearchEngineController
private void handleAdvancedSearch() {
    ConsoleHelper.println("Choose ranking algorithm:");
    ConsoleHelper.println("  1. TF-IDF (default)");
    ConsoleHelper.println("  2. BM25");
    ConsoleHelper.println("  3. Binary");
    
    String choice = scanner.nextLine().trim();
    String query = ConsoleHelper.prompt("Enter query: ");
    
    List<SearchResult> results = switch(choice) {
        case "1" -> queryProcessor.rankedQuery(query);
        case "2" -> queryProcessor.rankedQueryBM25(query);
        case "3" -> queryProcessor.binarySearch(query);
        default -> null;
    };
    
    if (results != null) {
        ConsoleHelper.showResults(results, "Results");
    }
}
```

### Adding Query Spell-Check Confirmation

```java
private void handleRankedSearch() {
    String query = ConsoleHelper.prompt("Enter query: ");
    
    // Check for potential misspellings
    List<String> corrections = queryProcessor.suggestCorrections(query);
    
    if (!corrections.isEmpty()) {
        ConsoleHelper.println("Did you mean?");
        for (int i = 0; i < corrections.size(); i++) {
            ConsoleHelper.println(String.format("  %d. %s", i+1, corrections.get(i)));
        }
        String choice = ConsoleHelper.prompt("Enter choice (or press Enter to continue): ");
        if (!choice.isEmpty()) {
            query = corrections.get(Integer.parseInt(choice) - 1);
        }
    }
    
    List<SearchResult> results = queryProcessor.rankedQuery(query);
    ConsoleHelper.showResults(results, "Results");
}
```

---

## File Organization Reference

```
src/
├── Main.java                          (Entry point)
├── SearchEngineController.java        (Orchestrator)
├── ConsoleHelper.java                 (UI utilities)
├── extensions/                        (NEW - Add here)
│   ├── ResultsExporter.java
│   ├── SearchResultFilter.java
│   ├── SearchManager.java
│   └── StatisticsGenerator.java
└── features/
    └── (unchanged, core logic)
```

---

## Testing Your Extension

Before committing, test:

1. **Happy Path:** Normal usage
2. **Edge Cases:** Empty input, very long query, special characters
3. **Error Handling:** Invalid menu choice, file not found
4. **Integration:** Make sure core search still works
5. **Backward Compatibility:** Old features still work
6. **Multiple Searches:** Run 2-3 searches in a session
7. **Exit:** Graceful cleanup

---

## Summary

Extending the search engine is simple:

1. **Identify where it belongs** (controller method, utility class, or helper)
2. **Follow the architecture** (separate concerns)
3. **Use existing utilities** (ConsoleHelper)
4. **Add menu option** if user-facing
5. **Validate inputs** before processing
6. **Handle errors** gracefully
7. **Test thoroughly** before deploying

The architecture makes it easy to add features without touching core logic!

