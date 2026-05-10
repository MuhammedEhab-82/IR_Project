# Search Engine Application - Refactoring Guide

## Overview

The Search Engine application has been refactored to provide a clean, modular, and maintainable architecture. The key improvement is separating UI/console logic from business logic through a controller-based approach.

---

## Architecture

### Before Refactoring
```
Main.java (minimal, calls HandlingMain)
└── HandlingMain (constructor does everything)
    ├── Document processing (Arabic + English)
    ├── Index building
    ├── Interactive menu loop
    └── Query handling
```

### After Refactoring
```
Main.java (clean entry point)
└── SearchEngineController (orchestrator)
    ├── Initialize → process docs + build index
    ├── start() → interactive menu loop
    ├── handleRankedSearch()
    ├── handleProximitySearch()
    ├── handlePrintIndex()
    └── handleExit()
    
ConsoleHelper (reusable UI utilities)
└── printHeader(), println(), prompt(), etc.
```

---

## Key Classes

### 1. Main.java
**Purpose:** Application entry point only.

**Responsibilities:**
- Create SearchEngineController instance
- Call `initialize()` to set up the engine
- Call `start()` to begin interactive loop
- Handle fatal errors

**Code Structure:**
```java
public class Main {
    public static void main(String[] args) {
        SearchEngineController controller = new SearchEngineController();
        controller.initialize();
        controller.start();
    }
}
```

### 2. SearchEngineController.java
**Purpose:** Main orchestrator for the entire application.

**Responsibilities:**
- Manage single Scanner instance (lifecycle)
- Initialize document processing and indexing
- Present interactive menu
- Route user choices to handlers
- Separate UI from business logic

**Key Methods:**
- `initialize()` - Set up documents and index
- `start()` - Main interactive loop
- `handleRankedSearch()` - Ranked search menu option
- `handleProximitySearch()` - Proximity search menu option
- `handlePrintIndex()` - Index printing menu option
- `handleExit()` - Graceful shutdown

### 3. ConsoleHelper.java
**Purpose:** Centralized console I/O utilities.

**Responsibilities:**
- Provide reusable output formatting methods
- Display menus and headers
- Handle user prompts
- Show consistent success/error/warning messages

**Key Methods:**
- `showMainMenu()` - Display menu options
- `println()` / `print()` - Standard output
- `printSuccess()` / `printError()` / `printWarning()` - Styled output
- `showResults()` - Format search results
- `showWelcome()` / `showGoodbye()` - Session start/end

---

## User Flow

```
1. User launches Main
   ↓
2. Main creates SearchEngineController
   ↓
3. Controller initializes:
   - Processes Arabic documents with ArabicPipeline
   - Processes English documents with EnglishTextProcessor
   - Builds PositionalIndex from processed docs
   ↓
4. Controller displays welcome message
   ↓
5. Controller enters interactive loop:
   
   ┌─────────────────────────────┐
   │   MAIN MENU (4 options)     │
   ├─────────────────────────────┤
   │ 1. Ranked Search            │
   │ 2. Proximity Search         │
   │ 3. Print Index              │
   │ 4. Exit                     │
   └─────────────────────────────┘
   ↓
6a. If Ranked Search:
    - Prompt for query string
    - Call QueryProcessor.rankedQuery()
    - Display results in ranked order
    - Return to menu
    
6b. If Proximity Search:
    - Prompt for term1
    - Prompt for term2
    - Prompt for distance k
    - Build query: term1/k/term2
    - Call QueryProcessor.proximityQuery()
    - Display matching documents
    - Return to menu
    
6c. If Print Index:
    - Call PositionalIndex.printIndex()
    - Display inverted index
    - Return to menu
    
6d. If Exit:
    - Close Scanner
    - Display goodbye message
    - Exit loop
```

---

## Design Principles

### 1. **Separation of Concerns**
- **UI Logic** → ConsoleHelper + SearchEngineController
- **Business Logic** → QueryProcessor, PositionalIndex, Ranker, etc.
- **No mixing** of console output with search algorithms

### 2. **Single Responsibility**
- `Main` - Entry point only
- `SearchEngineController` - Orchestration and menu routing
- `ConsoleHelper` - All console I/O
- `QueryProcessor` - Query processing (unchanged)
- `PositionalIndex` - Indexing logic (unchanged)

### 3. **DRY (Don't Repeat Yourself)**
- Single Scanner instance managed by controller
- Reusable console formatting in ConsoleHelper
- Modular handler methods for each menu option

### 4. **User Experience**
- Clear menu with numbered options
- Consistent visual formatting (headers, separators)
- Helpful prompts and error messages
- Bilingual support (Arabic + English)

---

## Integration with Existing Code

### Unchanged Packages
The following core packages remain **exactly unchanged**:
- `features.Indexing.PositionalIndex`
- `features.Query.QueryProcessor`
- `features.Query.SearchResult`
- `features.Rank.RankedRetriever`
- `features.Spelling_Correction.*`
- `features.Text_Preprocessing.*`
- `utils.*`

### Minimal Changes Required
1. No changes to QueryProcessor (supports rankedQuery and proximityQuery)
2. No changes to PositionalIndex
3. HandlingMain can remain for backward compatibility

---

## Example Menu Interaction

```
╔════════════════════════════════════════════════════════════╗
║     🔎 WELCOME TO OUR SEARCH ENGINE 🔎                    ║
║        (محرك البحث المتواضع بتاعنا)                        ║
╚════════════════════════════════════════════════════════════╝

ℹ️  Initializing search engine...
ℹ️  Processing Arabic documents...
ℹ️  Processing English documents...
ℹ️  Building positional index...
✅ Search engine ready!

─────────────────────────────────────────────────────────────
          🔍 SEARCH ENGINE - MAIN MENU
═════════════════════════════════════════════════════════════

  1️⃣  Ranked Search       - Search and rank documents by relevance
  2️⃣  Proximity Search    - Find terms within a specific distance
  3️⃣  Print Index         - Display the complete inverted index
  4️⃣  Exit                - Exit the application

  Please choose an option (1-4): 1

─────────────────────────────────────────────────────────────
          RANKED SEARCH
─────────────────────────────────────────────────────────────

Enter your search query (English or Arabic): neural networks
⏳ Processing query: "neural networks"...

✅ Search results found: 3 result(s)

  1. doc0.txt [Score: 0.95]
  2. doc1.txt [Score: 0.87]
  3. doc5.txt [Score: 0.72]
```

---

## Files Modified/Created

### Created Files
1. **ConsoleHelper.java** - Console I/O utilities
2. **SearchEngineController.java** - Main orchestrator

### Modified Files
1. **Main.java** - Now a clean entry point

### Unchanged Files
- All files in `features/` packages
- `utils/` files
- `HandlingMain.java` (can remain for backward compatibility)

---

## How to Run

### Using Main
```bash
# Compile
javac -cp src -d out src/Main.java src/SearchEngineController.java src/ConsoleHelper.java src/features/**/*.java src/utils/*.java

# Run
java -cp out Main
```

### What Happens
1. Application initializes and loads documents
2. Interactive menu is presented
3. User chooses search type
4. Results are displayed
5. User can perform multiple searches
6. User exits gracefully

---

## Future Enhancements

1. **Add Search History** - Track queries performed in session
2. **Save Results** - Export search results to file
3. **Filter Results** - Add result filtering by document type
4. **Advanced Queries** - Boolean AND/OR logic in UI
5. **Statistics** - Show index statistics (vocabulary size, doc count, etc.)
6. **Query Suggestions** - Auto-complete based on vocabulary
7. **Feedback** - Ask user if results were helpful

---

## Testing Checklist

- [ ] Application starts without errors
- [ ] Menu displays correctly
- [ ] Ranked search works with English query
- [ ] Ranked search works with Arabic query
- [ ] Proximity search works correctly
- [ ] Print index displays all terms
- [ ] Exit option closes cleanly
- [ ] Invalid menu choices are handled
- [ ] Empty queries are rejected
- [ ] Non-numeric distance input in proximity is rejected

---

## Troubleshooting

### Issue: Scanner resource warning
**Solution:** Single Scanner instance is managed in controller and closed in handleExit()

### Issue: Unclear menu flow
**Solution:** ConsoleHelper provides clear menus and prompts; controller routes to handlers

### Issue: Mixed UI and business logic
**Solution:** Core search logic unchanged; UI separated in ConsoleHelper and controller

---

## Conclusion

This refactoring maintains all existing functionality while providing:
- ✅ Clean, minimal entry point
- ✅ Modular, reusable controller
- ✅ Centralized console I/O
- ✅ Clear user experience
- ✅ Easy to extend and maintain
- ✅ Separation of concerns
- ✅ Single Scanner instance
- ✅ No changes to core search logic

