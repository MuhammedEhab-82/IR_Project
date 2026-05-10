# Application Flow Diagrams

## 1. Startup Sequence

```
┌─────────────────────────────────────────────────────────────────┐
│                       USER LAUNCHES APP                         │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
        ┌────────────────────────────────────┐
        │    Main.main(String[] args)        │
        │  - Create Controller               │
        │  - Call initialize()               │
        │  - Call start()                    │
        └────────────────┬───────────────────┘
                         │
                         ▼
        ┌────────────────────────────────────────────┐
        │  SearchEngineController.initialize()       │
        │  1. ArabicPipeline.processFolder()        │
        │  2. EnglishTextProcessor.processFolder()  │
        │  3. PositionalIndex.buildIndex()          │
        │  4. Show welcome message                  │
        └────────────────┬─────────────────────────┘
                         │
                         ▼
        ┌────────────────────────────────────┐
        │ SearchEngineController.start()     │
        │ - Enter interactive menu loop      │
        └────────────────────────────────────┘
```

---

## 2. Main Menu Loop

```
                    ┌──────────────────────┐
                    │  Display Main Menu   │
                    │  (4 options)         │
                    └──────────┬───────────┘
                               │
                ┌──────────────┼──────────────┐
                │              │              │
                ▼              ▼              ▼
            (1)            (2)            (3,4,?)
            │              │              │
    ┌───────────────┐ ┌──────────────┐   │
    │ Ranked        │ │ Proximity    │   │
    │ Search        │ │ Search       │   │
    └───────┬───────┘ └──────┬───────┘   │
            │                │            │
            ▼                ▼            ▼
        ┌──────────┐   ┌──────────┐  ┌─────────┐
        │ Get      │   │ Get      │  │ Other   │
        │ Query    │   │ Terms+K  │  │ Options │
        └────┬─────┘   └────┬─────┘  └────┬────┘
             │              │             │
             ▼              ▼             ▼
        ┌──────────┐   ┌──────────┐  ┌──────────────┐
        │ Call     │   │ Build    │  │ Print Index  │
        │ rankedQ  │   │ t1/k/t2  │  │ or Exit      │
        │ uery()   │   │ string   │  │              │
        └────┬─────┘   └────┬─────┘  └──────┬───────┘
             │              │               │
             ▼              ▼               ▼
        ┌──────────┐   ┌──────────┐   (back to menu)
        │ Display  │   │ Call     │
        │ Results  │   │ proximQ  │
        └────┬─────┘   │ uery()   │
             │         └────┬─────┘
             │              │
             ▼              ▼
        (back to menu) (Display Results)
                            │
                            ▼
                       (back to menu)
```

---

## 3. Ranked Search Handler

```
┌─────────────────────────────────────────────────────┐
│ handleRankedSearch()                                │
├─────────────────────────────────────────────────────┤
│ 1. ConsoleHelper.printSubHeader("RANKED SEARCH")   │
│ 2. Prompt user: "Enter search query"               │
│ 3. Validate: query not empty                       │
│    └─ If empty: show warning, return              │
│ 4. Show: "Processing query..."                     │
│ 5. Call: QueryProcessor.rankedQuery(query)        │
│    └─ Returns: List<SearchResult>                 │
│ 6. ConsoleHelper.showResults()                     │
│    ├─ If empty: show "No results found"           │
│    └─ If found: display ranked list               │
│ 7. Return to main menu                            │
└─────────────────────────────────────────────────────┘
```

---

## 4. Proximity Search Handler

```
┌────────────────────────────────────────────────────┐
│ handleProximitySearch()                            │
├────────────────────────────────────────────────────┤
│ 1. ConsoleHelper.printSubHeader("PROXIMITY SEARCH")
│ 2. Prompt: "Enter first term"                     │
│ 3. Prompt: "Enter second term"                    │
│ 4. Prompt: "Enter maximum distance (k)"           │
│ 5. Validate inputs:                               │
│    ├─ Both terms required                         │
│    └─ k must be positive integer                  │
│    └─ If invalid: show warning, return            │
│ 6. Build query: "term1/k/term2"                   │
│ 7. Show: "Processing proximity query..."          │
│ 8. Call: QueryProcessor.proximityQuery(query)    │
│    └─ Returns: List<SearchResult>                 │
│ 9. ConsoleHelper.showResults()                    │
│ 10. Return to main menu                           │
└────────────────────────────────────────────────────┘
```

---

## 5. Console Output Structure

```
┌──────────────────────────────────────────────────┐
│                WELCOME SCREEN                    │
├──────────────────────────────────────────────────┤
│ ╔════════════════════════════════════════════╗  │
│ ║  🔎 WELCOME TO OUR SEARCH ENGINE 🔎      ║  │
│ ║     (محرك البحث المتواضع بتاعنا)          ║  │
│ ╚════════════════════════════════════════════╝  │
│                                                  │
│ ℹ️  Initializing search engine...                │
│ ℹ️  Processing Arabic documents...               │
│ ℹ️  Processing English documents...              │
│ ℹ️  Building positional index...                 │
│ ✅ Search engine ready!                          │
└──────────────────────────────────────────────────┘
        │
        ▼
┌──────────────────────────────────────────────────┐
│              MAIN MENU SCREEN                    │
├──────────────────────────────────────────────────┤
│ ─────────────────────────────────────────────    │
│     🔍 SEARCH ENGINE - MAIN MENU                │
│ ═════════════════════════════════════════════    │
│                                                  │
│ 1️⃣  Ranked Search       - Search by relevance   │
│ 2️⃣  Proximity Search    - Terms within distance │
│ 3️⃣  Print Index         - Display index         │
│ 4️⃣  Exit                - Exit application      │
│                                                  │
│ Please choose an option (1-4): _               │
└──────────────────────────────────────────────────┘
        │
        ▼
┌──────────────────────────────────────────────────┐
│            SEARCH RESULTS SCREEN                 │
├──────────────────────────────────────────────────┤
│ ─────────────────────────────────────────────    │
│     RANKED SEARCH                               │
│ ─────────────────────────────────────────────    │
│                                                  │
│ Enter your search query: neural networks        │
│ ⏳ Processing query: "neural networks"...       │
│                                                  │
│ ✅ Search results found: 3 result(s)            │
│                                                  │
│ 1. doc0.txt [Score: 0.95]                       │
│ 2. doc1.txt [Score: 0.87]                       │
│ 3. doc5.txt [Score: 0.72]                       │
│                                                  │
└──────────────────────────────────────────────────┘
        │
        ▼
    (back to main menu)
```

---

## 6. Class Interaction Diagram

```
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│  Main                                                        │
│  ├── creates ──► SearchEngineController                     │
│                  │                                          │
│                  ├── has ──► PositionalIndex (unchanged)    │
│                  │                                          │
│                  ├── has ──► QueryProcessor (unchanged)     │
│                  │                                          │
│                  ├── uses ──► Scanner                       │
│                  │                                          │
│                  ├── calls ──► ArabicPipeline (unchanged)   │
│                  │                                          │
│                  ├── calls ──► EnglishTextProcessor         │
│                  │              (unchanged)                  │
│                  │                                          │
│                  └── uses ──► ConsoleHelper                 │
│                               │                             │
│                               └── utilities for UI/output   │
│                                                              │
│  Core Logic (All Unchanged)                                 │
│  ├── features.Indexing.*                                    │
│  ├── features.Query.*                                       │
│  ├── features.Rank.*                                        │
│  ├── features.Spelling_Correction.*                         │
│  ├── features.Text_Preprocessing.*                          │
│  └── utils.*                                                │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

---

## 7. Data Flow for Ranked Search

```
User Input
    │
    ▼ (user enters: "neural networks")
    │
Scanner.nextLine()
    │
    ▼
handleRankedSearch()
    │
    ├─ Validate input
    │
    ▼
QueryProcessor.rankedQuery("neural networks")
    │
    ├─ preprocess()        (tokenize, stem, stop words)
    ├─ spelling correction
    └─ ranking algorithm
    │
    ▼
List<SearchResult>
    │
    ├─ result[0]: Score 0.95
    ├─ result[1]: Score 0.87
    └─ result[2]: Score 0.72
    │
    ▼
ConsoleHelper.showResults()
    │
    ▼ (formatted output)
System.out.println()
    │
    ▼
Display to User
    │
    ▼
Back to Main Menu
```

---

## 8. Error Handling Flow

```
                    ┌━━━━━━━━━━━━━━┓
                    ┃  User Input  ┃
                    └───────┬──────┘
                            │
                ┌───────────┴───────────┐
                │                       │
                ▼                       ▼
            Valid?               Constructor Error?
           /      \              (e.g., file not found)
         Yes       No                   │
          │         │                   ▼
          │         ▼            ConsoleHelper.printError()
          │    printWarning()           │
          │         │                   ▼
          │         │           System.err.println()
          │         │                   │
          ▼         ▼                   ▼
       Process   Try Again         Exit (System.exit(1))
       Query     OR
          │      Return
          │
          ▼
    Display Results
```

---

## 9. Session Lifecycle

```
START
  │
  ├─ User runs: java -cp out Main
  │
  ├─ Main.main() creates SearchEngineController
  │
  ├─ controller.initialize()
  │  ├─ Process documents (Arabic + English)
  │  ├─ Build index
  │  └─ Show welcome
  │
  ├─ controller.start() ◄──────┐
  │  │                        │ (loop)
  │  ├─ Show menu            │
  │  │                        │
  │  ├─ Get user choice      │
  │  │                        │
  │  ├─ Handle choice:       │
  │  │  ├─ 1: rankedSearch   │
  │  │  ├─ 2: proximitySearch
  │  │  ├─ 3: printIndex     │
  │  │  ├─ 4: exit ─────┐    │
  │  │  └─ ?: warning   │    │
  │  │                  │    │
  │  └──────────────────┼────┘
  │                     │
  │                     ▼
  ├─ controller.handleExit()
  │  ├─ Close Scanner
  │  ├─ Show goodbye
  │  └─ Set running = false
  │
  ├─ Exit loop
  │
  └─ END
```

---

## 10. Package Structure

```
src/
├── Main.java                          [REFACTORED ✅]
├── SearchEngineController.java        [NEW ✅]
├── ConsoleHelper.java                 [NEW ✅]
├── HandlingMain.java                  [Legacy, optional]
│
└── features/
    ├── Indexing/
    │   └── PositionalIndex.java       [UNCHANGED ✅]
    ├── Query/
    │   ├── QueryProcessor.java        [UNCHANGED ✅]
    │   ├── QueryResponse.java         [UNCHANGED ✅]
    │   └── SearchResult.java          [UNCHANGED ✅]
    ├── Rank/
    │   └── RankedRetriever.java       [UNCHANGED ✅]
    ├── Spelling_Correction/
    │   ├── EditDistance.java          [UNCHANGED ✅]
    │   └── SpellingCorrector.java     [UNCHANGED ✅]
    ├── Text_Preprocessing/
    │   ├── Arabic/
    │   │   ├── ArabicPipeline.java    [UNCHANGED ✅]
    │   │   ├── Normalizer.java        [UNCHANGED ✅]
    │   │   ├── Stemmer.java           [UNCHANGED ✅]
    │   │   ├── StopWordsRemover.java  [UNCHANGED ✅]
    │   │   └── Tokenizer.java         [UNCHANGED ✅]
    │   └── english/
    │       ├── EnglishTextProcessor.java [UNCHANGED ✅]
    │       ├── PorterStemmer.java     [UNCHANGED ✅]
    │       ├── StopWordRemover.java   [UNCHANGED ✅]
    │       └── Tokenizer.java         [UNCHANGED ✅]
    │
    └── utils/
        ├── FileReader.java            [UNCHANGED ✅]
        └── FileWriter.java            [UNCHANGED ✅]
```

---

End of Flow Diagrams

