# Information Retrieval (IR) Search Engine - Complete Documentation

A bilingual (English/Arabic) document search engine with ranked retrieval, proximity search, and advanced NLP processing.

---

## 📋 Table of Contents

1. [Project Overview](#project-overview)
2. [Quick Start](#quick-start)
3. [Features](#features)
4. [Architecture](#architecture)
5. [System Components](#system-components)
6. [Documentation](#documentation)
7. [How to Run](#how-to-run)
8. [Project Structure](#project-structure)
9. [Recent Refactoring](#recent-refactoring)
10. [Support & Contributing](#support--contributing)

---

## 🎯 Project Overview

This IR system is a comprehensive, modular implementation of information retrieval concepts. It supports:

- **Bilingual Processing:** English and Arabic documents
- **Advanced Preprocessing:** Tokenization, normalization, stemming, stop word removal
- **Ranked Retrieval:** TF-IDF based scoring and ranking
- **Proximity Search:** Find terms within specified distances
- **Spelling Correction:** Automatic misspelling detection and correction
- **Extensible Architecture:** Clean, modular design for easy enhancement

### Key Dataset
- **20 documents:** 10 English + 10 Arabic
- **Located in:** `src/docs/` directory
- **Processed versions:** `src/docs/processed/` directory

---

## 🚀 Quick Start

### Prerequisites
```
Java 11+
Project structure intact (src/ directory with all files)
```

### Installation

```bash
# Clone/extract the project
cd IRproject

# Compile (if needed)
javac -cp src -d out src/Main.java src/SearchEngineController.java src/ConsoleHelper.java

# Run
java -cp out Main
```

### First Search
```
1. Application starts and initializes
2. Main menu appears
3. Select option 1 for Ranked Search
4. Type your query: "neural networks"
5. View ranked results
```

---

## ✨ Features

### 1. Ranked Search
- Full-text search across all documents
- Results ranked by TF-IDF relevance score
- Supports English, Arabic, and mixed queries
- Automatic spelling correction

### 2. Proximity Search
- Find terms within a specified distance
- Useful for phrase-based searching
- Positional index enables efficient search
- Examples:
  - `neural/3/networks` - "neural" within 3 words of "networks"
  - `البحث/2/المعلومات` - Arabic terms within 2 words

### 3. Index Inspection
- View the complete inverted index
- See term frequencies and document positions
- Understand how documents are indexed
- Useful for debugging and analysis

### 4. Advanced Text Processing

#### English Processing
- Tokenization (word splitting)
- Lowercasing
- Porter Stemmer (converts words to root form)
- Stop word removal (removes common words like "the", "a")

#### Arabic Processing
- Proper Unicode support
- Arabic normalization (diacritic removal)
- Arabic stemming (root extraction)
- Arabic stop word removal
- Handles complex morphology

### 5. Spelling Correction
- Edit distance based correction
- Suggests corrections for misspelled terms
- Integrates with search queries
- Supports both languages

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────┐
│            USER INTERFACE               │
│  (Main.java → SearchEngineController)   │
├─────────────────────────────────────────┤
│          CONSOLE UTILITIES              │
│         (ConsoleHelper.java)            │
├─────────────────────────────────────────┤
│        SEARCH ENGINE LOGIC              │
│  ┌──────────────────────────────────┐   │
│  │ QueryProcessor                   │   │
│  ├─ Ranked Query Processing        │   │
│  ├─ Proximity Query Processing     │   │
│  └─ Language Detection             │   │
│  ┌──────────────────────────────────┐   │
│  │ PositionalIndex                  │   │
│  ├─ Inverted Index Management      │   │
│  ├─ Document Storage              │   │
│  └─ Posting Lists with Positions   │   │
│  ┌──────────────────────────────────┐   │
│  │ RankedRetriever                  │   │
│  └─ TF-IDF Scoring & Ranking       │   │
├─────────────────────────────────────────┤
│        TEXT PREPROCESSING               │
│  ┌────────────────────┐                 │
│  │ EnglishTextProcessor   │                 │
│  │ - Tokenizer        │                 │
│  │ - StopWordRemover  │                 │
│  │ - PorterStemmer    │                 │
│  └────────────────────┘                 │
│  ┌────────────────────┐                 │
│  │ ArabicPipeline     │                 │
│  │ - Tokenizer        │                 │
│  │ - Normalizer       │                 │
│  │ - StopWordsRemover │                 │
│  │ - Stemmer          │                 │
│  └────────────────────┘                 │
├─────────────────────────────────────────┤
│      SPELLING CORRECTION                │
│  ┌────────────────────┐                 │
│  │ SpellingCorrector  │                 │
│  │ - EditDistance     │                 │
│  │ - Vocabulary Check │                 │
│  └────────────────────┘                 │
├─────────────────────────────────────────┤
│         UTILITIES                       │
│  ┌────────────────────┐                 │
│  │ FileReader/Writer  │                 │
│  └────────────────────┘                 │
└─────────────────────────────────────────┘
```

---

## 🔧 System Components

### Core Classes

| Component | Package | Purpose |
|-----------|---------|---------|
| `PositionalIndex` | `features.Indexing` | Manages inverted index with positions |
| `QueryProcessor` | `features.Query` | Processes user queries |
| `RankedRetriever` | `features.Rank` | Scores and ranks documents |
| `SpellingCorrector` | `features.Spelling_Correction` | Corrects misspellings |

### Text Processing

| Component | Language | Purpose |
|-----------|----------|---------|
| `EnglishTextProcessor` | English | Coordinates English processing |
| `Tokenizer` | English | Splits text into tokens |
| `PorterStemmer` | English | Reduces words to roots |
| `StopWordRemover` | English | Filters common words |
| `ArabicPipeline` | Arabic | Coordinates Arabic processing |
| `Tokenizer` | Arabic | Arabic-specific tokenization |
| `Normalizer` | Arabic | Removes diacritics |
| `Stemmer` | Arabic | Arabic morphological stemming |
| `StopWordsRemover` | Arabic | Arabic stop word filtering |

### User Interface

| Component | Purpose |
|-----------|---------|
| `Main` | Application entry point |
| `SearchEngineController` | Main orchestrator |
| `ConsoleHelper` | UI utilities and formatting |

---

## 📚 Documentation

### For Users
- **[USER_MANUAL.md](USER_MANUAL.md)** - Complete user guide with examples

### For Developers
- **[REFACTORING_GUIDE.md](REFACTORING_GUIDE.md)** - Architecture and design decisions
- **[REFACTORING_SUMMARY.md](REFACTORING_SUMMARY.md)** - Quick reference
- **[FLOW_DIAGRAMS.md](FLOW_DIAGRAMS.md)** - Visual system flows
- **[DEVELOPER_GUIDE.md](DEVELOPER_GUIDE.md)** - How to extend the system

### For Evaluation
- **[Evaluation.md](src/Evaluation.md)** - System evaluation metrics
- **[RESULTS_REPORT.md](src/features/Evaluation/RESULTS_REPORT.md)** - Evaluation results

---

## 🏃 How to Run

### Command Line (Windows)
```powershell
cd E:\7mhab\coding\IRproject
javac -cp src -d out src/Main.java src/SearchEngineController.java src/ConsoleHelper.java
java -cp out Main
```

### Command Line (Linux/Mac)
```bash
cd /path/to/IRproject
javac -cp src -d out src/Main.java src/SearchEngineController.java src/ConsoleHelper.java
java -cp out Main
```

### Using IDE
1. Open project in IDE (IntelliJ, Eclipse, etc.)
2. Right-click Main.java → Run
3. Menu appears in console

### Expected Output
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

  Please choose an option (1-4):
```

---

## 📁 Project Structure

```
IRproject/
├── README.md                          ← You are here
├── REFACTORING_GUIDE.md              (Architecture & Design)
├── REFACTORING_SUMMARY.md            (Quick Reference)
├── DEVELOPER_GUIDE.md                (Extension Guide)
├── FLOW_DIAGRAMS.md                  (Visual Flows)
├── USER_MANUAL.md                    (User Guide)
│
├── src/
│   ├── Main.java                     (Entry point)
│   ├── SearchEngineController.java   (Orchestrator)
│   ├── ConsoleHelper.java            (UI Utilities)
│   ├── HandlingMain.java             (Legacy)
│   │
│   ├── features/
│   │   ├── Indexing/
│   │   │   └── PositionalIndex.java
│   │   ├── Query/
│   │   │   ├── QueryProcessor.java
│   │   │   ├── QueryResponse.java
│   │   │   └── SearchResult.java
│   │   ├── Rank/
│   │   │   └── RankedRetriever.java
│   │   ├── Spelling_Correction/
│   │   │   ├── EditDistance.java
│   │   │   └── SpellingCorrector.java
│   │   ├── Text_Preprocessing/
│   │   │   ├── Arabic/
│   │   │   │   ├── ArabicPipeline.java
│   │   │   │   ├── Normalizer.java
│   │   │   │   ├── Stemmer.java
│   │   │   │   ├── StopWordsRemover.java
│   │   │   │   └── Tokenizer.java
│   │   │   └── english/
│   │   │       ├── EnglishTextProcessor.java
│   │   │       ├── PorterStemmer.java
│   │   │       ├── StopWordRemover.java
│   │   │       └── Tokenizer.java
│   │   └── Evaluation/
│   │       ├── PrecisionRecall.java  (Evaluation Metrics)
│   │       ├── README.md
│   │       └── RESULTS_REPORT.md
│   │
│   ├── utils/
│   │   ├── FileReader.java
│   │   └── FileWriter.java
│   │
│   ├── docs/                         (Original Documents)
│   │   ├── English/                  (10 English docs)
│   │   ├── arabic/                   (10 Arabic docs)
│   │   └── processed/
│   │       ├── english/              (Processed English)
│   │       └── arabic/               (Processed Arabic)
│   │
│   ├── resources/
│   │   ├── stopwords_ar.txt
│   │   └── stopwords_en.txt
│   │
│   └── Evaluation.md                 (Evaluation Metrics)
│
├── IRproject.iml                     (IDE Configuration)
└── out/                              (Compiled Files - Generated)
```

---

## 🔄 Recent Refactoring

### What Changed
- ✅ Centralized entry point into `Main.java`
- ✅ Created `SearchEngineController` for orchestration
- ✅ Extracted UI logic into `ConsoleHelper`
- ✅ Removed constructor-based initialization
- ✅ Single Scanner instance management
- ✅ Clean separation of concerns

### Why
- Better code organization
- Easier to test and maintain
- Clearer user experience
- Easy to extend with new features
- Follows SOLID principles

### Impact
- ✅ No changes to core search logic
- ✅ No changes to document processing
- ✅ All existing features 100% compatible
- ✅ Better user interface
- ✅ Production-ready architecture

For details, see [REFACTORING_GUIDE.md](REFACTORING_GUIDE.md)

---

## 💡 Key Technologies

### NLP Techniques
- **Tokenization:** Breaking text into meaningful units
- **Stemming:** Reducing words to their root form
- **Normalization:** Standardizing text representation
- **Stop word removal:** Filtering out common words
- **TF-IDF:** Ranking documents by relevance

### Data Structures
- **Inverted Index:** Maps terms to documents
- **Positional Index:** Stores term positions (enables phrase search)
- **Hash Maps:** Fast term lookup and posting retrieval
- **Lists:** Maintaining document rankings

### Algorithms
- **Edit Distance (Levenshtein):** Spelling correction
- **TF-IDF Scoring:** Document relevance ranking
- **Proximity Search:** Position-based filtering
- **Boolean AND:** Multi-term query combining

---

## 📊 Evaluation

The system has been evaluated on multiple queries:

### Performance Metrics
- **Mean Precision:** 86.4%
- **Mean Recall:** 93.2%
- **Test Queries:** 5 diverse queries (3 English + 2 Arabic)

### Evaluation Queries
1. "Neural Networks" - Information retrieval accuracy
2. "Supervised Learning" - Algorithm matching
3. "Evaluation Metrics" - Exact term search
4. "قواعد البيانات" (Databases) - Arabic support
5. "الأمن السيبراني" (Cybersecurity) - Arabic language processing

For detailed results, see:
- [Evaluation.md](src/Evaluation.md)
- [RESULTS_REPORT.md](src/features/Evaluation/RESULTS_REPORT.md)

---

## 🎓 Usage Examples

### Example 1: Basic Search
```
Menu Choice: 1
Query: "machine learning"
Result: Returns 3 documents ranked by relevance
```

### Example 2: Arabic Search
```
Menu Choice: 1
Query: "البحث عن المعلومات"
Result: Returns relevant Arabic documents
```

### Example 3: Proximity Search
```
Menu Choice: 2
Term 1: "information"
Term 2: "retrieval"
Distance: 3
Result: Documents where these terms appear close together
```

### Example 4: Index Analysis
```
Menu Choice: 3
Result: Complete inverted index displayed with all terms and positions
```

---

## 🔐 Language Support

| Feature | English | Arabic | Mixed |
|---------|---------|--------|-------|
| Tokenization | ✅ | ✅ | ✅ |
| Stemming | ✅ | ✅ | N/A |
| Stop Words | ✅ | ✅ | ✅ |
| Search | ✅ | ✅ | ✅ |
| Ranking | ✅ | ✅ | ✅ |
| Proximity | ✅ | ✅ | ✅ |
| Spelling Correction | ✅ | ✅ | ✅ |

---

## 🚀 Future Enhancements

- [ ] Advanced Boolean queries (AND, OR, NOT)
- [ ] Wildcard search support
- [ ] Query expansion with synonyms
- [ ] Result filtering and sorting options
- [ ] Search history and bookmarks
- [ ] Export results to file (CSV/JSON)
- [ ] Web-based UI
- [ ] Batch processing for large document collections
- [ ] Relevance feedback
- [ ] Machine learning ranking models

---

## ❓ FAQ

**Q: How do I search in Arabic?**
A: Just type your Arabic query. The system automatically detects the language and processes it accordingly.

**Q: Can I search in multiple languages at once?**
A: Yes! You can mix English and Arabic in a single query.

**Q: What if my query has typos?**
A: The system has automatic spelling correction. Common misspellings are detected and corrected.

**Q: How are documents ranked?**
A: Documents are ranked using TF-IDF (Term Frequency-Inverse Document Frequency), a standard IR metric that considers how often terms appear in a document and across all documents.

**Q: Can I add more documents?**
A: Yes, add them to `src/docs/English/` or `src/docs/arabic/` and run the application again. The indexing will automatically include them.

**Q: How do I modify the stop words list?**
A: Stop words are loaded from resource files. Modify `src/resources/stopwords_en.txt` or `src/resources/stopwords_ar.txt`.

---

## 📞 Support & Contributing

### Issues & Bug Reports
Document any issues you encounter. Include:
- Steps to reproduce
- Expected behavior
- Actual behavior
- Error messages

### Contributing
To extend the system:
1. Review [DEVELOPER_GUIDE.md](DEVELOPER_GUIDE.md)
2. Follow the architecture guidelines
3. Maintain separation of concerns
4. Add appropriate documentation
5. Test thoroughly before submitting

### Code Quality
- Follow Java conventions
- Add JavaDoc comments
- Use meaningful variable names
- Keep methods focused
- Test edge cases

---

## 📄 License & Attribution

This project is an Information Retrieval system implementation for educational purposes.

### Algorithms Used
- Porter Stemmer (for English)
- Edit Distance (Levenshtein) - for spelling correction
- TF-IDF (Term Frequency-Inverse Document Frequency) - for ranking
- Positional Indexing - for phrase search

### References
- "Introduction to Information Retrieval" by Manning, Raghavan, Schütze
- Arabic NLP best practices
- Java best practices and design patterns

---

## 🎉 Summary

This IR search engine demonstrates:
- ✅ Complete IR system implementation
- ✅ Bilingual text processing (English/Arabic)
- ✅ Sophisticated ranking algorithms
- ✅ Clean, modular architecture
- ✅ User-friendly interface
- ✅ Extensible design
- ✅ Production-quality code

**Happy Searching! 🔍**

For more information, see the documentation files:
- Users → [USER_MANUAL.md](USER_MANUAL.md)
- Developers → [DEVELOPER_GUIDE.md](DEVELOPER_GUIDE.md)
- Architecture → [REFACTORING_GUIDE.md](REFACTORING_GUIDE.md)

---

**Last Updated:** May 2026  
**Version:** 2.0 (Refactored)  
**Status:** Production Ready ✅

