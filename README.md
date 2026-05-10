# IR Search Engine - Bilingual Information Retrieval System

A Java-based **Information Retrieval (IR) system** that enables ranked and proximity searching across bilingual (English & Arabic) document collections using advanced text processing, positional indexing, and TF-IDF ranking.

---

## Features

### ✅ **Core Search Capabilities**
- **Ranked Search**: Retrieve documents ranked by relevance using TF-IDF scoring and cosine similarity
- **Proximity Search**: Find documents where two terms occur within a specified distance (positional indexing)
- **Bilingual Support**: Seamlessly handle English and Arabic queries with language-specific preprocessing
- **Automatic Language Detection**: Distinguish between Arabic and English text within mixed-language queries

### ✅ **Text Processing Pipeline**
- **English Processing**: Tokenization → Stop word removal → Porter stemming
- **Arabic Processing**: Normalization → Tokenization → Stop word removal → Arabic stemming
- **Language-Specific Stemming**: Porter stemmer for English, custom Arabic stemmer for Arabic
- **Stop Word Lists**: Configurable stop word removal using external resource files

### ✅ **Indexing & Retrieval**
- **Positional Index**: Stores term positions within documents for proximity search support
- **Inverted Index**: Maps terms to documents with position lists for efficient retrieval
- **TF-IDF Ranking**: Combined term frequency (TF) and inverse document frequency (IDF) for relevance scoring
- **Cosine Similarity**: Vector-space model for ranking documents by query-document similarity

### ✅ **Spelling Correction**
- **Edit Distance (Levenshtein Distance)**: Detects and corrects misspelled query terms
- **Dictionary-Based Correction**: Suggests closest matches from the indexed vocabulary

### ✅ **Interactive CLI Menu**
- **Main Menu Interface**: Clean, numbered options for all search types
- **User-Friendly Prompts**: Clear instructions for query input and parameter selection
- **Formatted Results**: Numbered ranked results with relevance scores
- **Index Inspection**: Option to view the complete inverted index structure

---

## Project Structure

```
IRproject/
├── README.md                          # Project documentation
├── Evaluation.md                       # First evaluation report
├── Evaluation2.md                      # Second evaluation report
├── IRproject.iml                       # IntelliJ IDEA module file
│
├── src/
│   ├── Main.java                       # Application entry point
│   ├── SearchEngineController.java     # Main orchestrator & menu controller
│   ├── ConsoleHelper.java              # Console I/O utility class
│   │
│   ├── features/
│   │   ├── Indexing/
│   │   │   └── PositionalIndex.java    # Positional inverted index implementation
│   │   │
│   │   ├── Query/
│   │   │   ├── QueryProcessor.java     # Query parsing & processing (ranked + proximity)
│   │   │   ├── SearchResult.java       # Result object (docId, docName, score)
│   │   │   └── QueryResponse.java      # Response wrapper
│   │   │
│   │   ├── Rank/
│   │   │   └── RankedRetriever.java    # TF-IDF ranking & cosine similarity
│   │   │
│   │   ├── Spelling_Correction/
│   │   │   ├── EditDistance.java       # Levenshtein distance computation
│   │   │   └── SpellingCorrector.java  # Spelling correction engine
│   │   │
│   │   └── Text_Preprocessing/
│   │       ├── English/
│   │       │   ├── EnglishTextProcessor.java    # English pipeline orchestrator
│   │       │   ├── Tokenizer.java               # English tokenization
│   │       │   ├── StopWordRemover.java         # English stop word removal
│   │       │   └── PorterStemmer.java           # Porter stemming algorithm
│   │       └── Arabic/
│   │           ├── ArabicPipeline.java          # Arabic pipeline orchestrator
│   │           ├── Normalizer.java              # Arabic text normalization
│   │           ├── Tokenizer.java               # Arabic tokenization
│   │           ├── StopWordsRemover.java        # Arabic stop word removal
│   │           └── Stemmer.java                 # Arabic stemming algorithm
│   │
│   ├── utils/
│   │   ├── FileReader.java             # File input utility
│   │   └── FileWriter.java             # File output utility
│   │
│   ├── docs/
│   │   ├── English/                    # Original English documents (en_001.txt - en_005.txt)
│   │   ├── arabic/                     # Original Arabic documents (ar_001.txt - ar_005.txt)
│   │   └── processed/
│   │       ├── English/                # Preprocessed English documents
│   │       └── arabic/                 # Preprocessed Arabic documents
│   │
│   └── resources/
│       ├── stopwords_en.txt            # English stop word list
│       └── stopwords_ar.txt            # Arabic stop word list
```

---

## Application Flow

### **1. Startup Phase** (Main.java)
```
Main.main()
  ↓
Creates SearchEngineController
  ↓
controller.initialize()
  ├─ ArabicPipeline.processFolder()
  │  ├─ Read raw Arabic documents
  │  ├─ Normalize text (remove diacritics, etc.)
  │  ├─ Tokenize into words
  │  ├─ Remove Arabic stop words
  │  ├─ Apply Arabic stemming
  │  └─ Write processed output
  ├─ EnglishTextProcessor.processFolder()
  │  ├─ Read raw English documents
  │  ├─ Tokenize into words
  │  ├─ Remove English stop words
  │  ├─ Apply Porter stemming
  │  └─ Write processed output
  ├─ PositionalIndex.buildIndex() ×2
  │  ├─ Read processed documents
  │  ├─ Index terms with positions
  │  └─ Build inverted index
  └─ Create QueryProcessor with index

controller.start()
  ↓
Enter interactive menu loop
```

### **2. Runtime: Ranked Search**
```
User selects "1" → handleRankedSearch()
  ↓
Get query from user
  ↓
QueryProcessor.rankedQuery(query)
  ├─ Auto-detect language (Arabic vs English)
  ├─ Apply language-specific preprocessing
  │  ├─ Tokenize
  │  ├─ Remove stop words
  │  └─ Apply stemming
  ├─ Spelling correction (optional)
  ├─ RankedRetriever.rank(terms)
  │  ├─ Calculate TF for each term in each document
  │  ├─ Calculate IDF for each term
  │  ├─ Compute TF-IDF vectors
  │  ├─ Calculate query magnitude
  │  ├─ Calculate cosine similarity scores
  │  └─ Sort by score
  └─ Return List<SearchResult>
  ↓
ConsoleHelper.showResults() - Display ranked results
```

### **3. Runtime: Proximity Search**
```
User selects "2" → handleProximitySearch()
  ↓
Get proximity query from user (format: "term1/k/term2")
  ↓
Parse: term1, k, term2
  ↓
QueryProcessor.proximityQuery(query)
  ├─ Preprocess term1 and term2
  ├─ Apply spelling correction
  ├─ PositionalIndex.proximitySearch(term1, term2, k)
  │  ├─ Get posting lists for term1 and term2
  │  ├─ For each document containing both:
  │  │  ├─ Compare term positions
  │  │  └─ If distance ≤ k: add to results
  │  └─ Return matching document IDs
  ├─ RankedRetriever.rank(terms, allowedDocs)
  │  ├─ Rank only documents from proximity search
  │  └─ Return ranked results
  └─ Return List<SearchResult>
  ↓
ConsoleHelper.showResults() - Display proximity results
```

### **4. Runtime: Print Index**
```
User selects "3" → handlePrintIndex()
  ↓
PositionalIndex.printIndex()
  ├─ For each term in index:
  │  ├─ Print term
  │  ├─ For each document:
  │  │  └─ Print doc_id: [position_list]
  │  └─ Print newline
  └─ Return
```

### **5. Shutdown**
```
User selects "4" → handleExit()
  ↓
Set running = false
  ↓
Exit menu loop
  ↓
ConsoleHelper.showGoodbye()
  ↓
scanner.close()
  ↓
Program terminates
```

---

## Search Types

### **Ranked Search**
- **Purpose**: Find all documents relevant to a query, ranked by relevance score
- **Input**: Query string (English or Arabic)
- **Processing**: 
  - Automatic language detection
  - Language-specific preprocessing
  - TF-IDF ranking using cosine similarity
- **Output**: Sorted list of documents with relevance scores
- **Use Case**: General information retrieval, document discovery

**Example:**
```
Query: "machine learning neural networks"
Result:
  1. en_001.txt (score: 8.4523)
  2. en_003.txt (score: 7.2341)
  3. en_005.txt (score: 5.1234)
```

### **Proximity Search**
- **Purpose**: Find documents where two terms occur within a specified distance
- **Input**: `term1/k/term2` where k is maximum distance (in positions)
- **Processing**:
  - Parse term1, k, term2 from query
  - Preprocess both terms
  - Check positional index for term proximity
  - Rank matching documents
- **Output**: Documents ranked by relevance (among those containing both terms in proximity)
- **Use Case**: Phrase detection, context-aware search

**Example:**
```
Query: "neural/5/networks"
Result: Documents where "neural" and "networks" appear within 5 positions
```

---

## Text Processing Pipeline

### **English Pipeline**
```
Raw English Document
  ↓
Tokenization
  ├─ Split on whitespace and punctuation
  └─ Convert to lowercase
  ↓
Stop Word Removal
  ├─ Remove articles (a, an, the)
  ├─ Remove pronouns (i, you, he, she, it, etc.)
  ├─ Remove prepositions (in, on, at, by, for, etc.)
  ├─ Remove conjunctions (and, or, but, etc.)
  ├─ Remove auxiliary verbs (is, are, be, been, etc.)
  └─ Remove common adverbs (very, quite, not, never, etc.)
  ↓
Porter Stemming
  ├─ Remove common suffixes
  └─ Example: "running" → "run", "organization" → "organ"
  ↓
Processed Document (indexed terms)
```

### **Arabic Pipeline**
```
Raw Arabic Document
  ↓
Normalization
  ├─ Remove diacritical marks (tashkeel)
  ├─ Standardize letter forms
  └─ Handle Unicode variations
  ↓
Tokenization
  ├─ Split on whitespace
  └─ Maintain Arabic text integrity
  ↓
Stop Word Removal
  ├─ Remove common Arabic particles
  ├─ Remove Arabic prepositions
  └─ Remove Arabic conjunctions
  ↓
Arabic Stemming
  ├─ Remove Arabic prefixes and suffixes
  └─ Example: "والمدارس" (and-the-schools) → root form
  ↓
Processed Document (indexed terms)
```

---

## Ranking & Retrieval Logic

### **TF-IDF Scoring**

**Term Frequency (TF):**
```
TF(term, doc) = 1 + log₁₀(raw_count)
```
- Logarithmic scaling avoids bias toward long documents
- Minimum value of 1 for present terms

**Inverse Document Frequency (IDF):**
```
IDF(term) = log₁₀(total_docs / document_frequency)
```
- Higher IDF for rare terms
- Lower IDF for common terms

**TF-IDF Weight:**
```
Weight(term, doc) = TF(term, doc) × IDF(term)
```

### **Cosine Similarity Ranking**

1. **Create TF-IDF vectors**:
   - For each query term, calculate combined TF-IDF
   - For each document, calculate TF-IDF for all query terms

2. **Calculate magnitudes**:
   - Query magnitude: √(Σ query_weight²)
   - Document magnitude: √(Σ document_weight²)

3. **Compute cosine similarity**:
   ```
   Similarity(query, doc) = (query · doc) / (|query| × |doc|)
   ```

4. **Rank documents**:
   - Sort all documents by similarity score (descending)
   - Return top results first

### **Example Calculation**
```
Query: "neural networks" (after preprocessing)
Document containing: neural(3×), networks(2×)

TF("neural", doc) = 1 + log₁₀(3) ≈ 1.477
TF("networks", doc) = 1 + log₁₀(2) ≈ 1.301

IDF("neural") = log₁₀(10 / 4) ≈ 0.398
IDF("networks") = log₁₀(10 / 3) ≈ 0.523

Weight("neural", doc) = 1.477 × 0.398 ≈ 0.588
Weight("networks", doc) = 1.301 × 0.523 ≈ 0.680

Score = 0.588 + 0.680 = 1.268 (approx, after normalization)
```

---

## Positional Indexing

### **Index Structure**
```
term → {
  doc_id_1 → [pos1, pos2, pos3, ...],
  doc_id_2 → [pos1, pos2, ...],
  ...
}
```

### **Example**
```
"learning" → {
  doc_0 → [5, 12, 28],
  doc_2 → [3, 7],
  doc_5 → [1, 15]
}
```

### **Proximity Search Algorithm**
```
proximitySearch(term1, term2, k):
  result = []
  for each document:
    if document contains both term1 and term2:
      for each position p1 of term1:
        for each position p2 of term2:
          if |p1 - p2| ≤ k:
            add document to result
            break inner loops
  return result
```

---

## Console Interface

### **Main Menu**
```
═══════════════════════════════════════════════════════════
          🔍 SEARCH ENGINE - MAIN MENU
═══════════════════════════════════════════════════════════

  1️⃣  Ranked Search       - Search and rank documents by relevance
  2️⃣  Proximity Search    - Find terms within a specific distance
  3️⃣  Print Index         - Display the complete inverted index
  4️⃣  Exit                - Exit the application

  Please choose an option (1-4): 
```

### **Ranked Search Flow**
```
Ranked Search prompt
  ↓
Enter your search query (English or Arabic): machine learning
  ↓
Processing query: "machine learning"...
  ↓
✅ Search results:
  1. en_001.txt (score: 8.4523)
  2. en_003.txt (score: 7.2341)
  3. en_005.txt (score: 5.1234)
  
[Return to main menu]
```

### **Proximity Search Flow**
```
Proximity Search prompt
  ↓
Enter your proximity Query: neural/5/networks
  ↓
Processing proximity query: "neural/5/networks"...
  ↓
✅ Proximity results:
  1. en_001.txt (score: 8.1234)
  2. en_002.txt (score: 5.5678)

[Return to main menu]
```

---

## Technologies & Concepts Used

### **Information Retrieval**
- Inverted indexing
- Positional indexing
- Ranked retrieval
- TF-IDF weighting
- Cosine similarity
- Vector space model

### **Text Processing**
- Tokenization
- Stop word removal
- Stemming (Porter for English, custom for Arabic)
- Text normalization (Arabic diacritics)

### **Natural Language Processing**
- Language detection (Arabic vs English)
- Language-specific text processing pipelines
- Edit distance for spelling correction
- Term position tracking

### **Data Structures**
- HashMap (inverted index)
- HashSet (document sets)
- ArrayList (position lists)
- Priority Queue (ranking results)

### **Algorithms**
- Levenshtein distance (spelling correction)
- Logarithmic TF calculation
- Cosine similarity computation
- Proximity distance calculation

---

## How to Run

### **Prerequisites**
- Java 9+ (uses `void main()` syntax)
- File system with `src/` structure intact
- Access to `src/docs/` and `src/resources/`

### **Compile**
```bash
cd E:\7mhab\coding\IRproject
javac -d out src/**/*.java
```

### **Run**
```bash
cd E:\7mhab\coding\IRproject
java -cp out Main
```

### **Interactive Usage**
1. Application initializes (processes and indexes documents)
2. Main menu appears with 4 options
3. Choose option:
   - **1**: Enter query for ranked search
   - **2**: Enter proximity query (format: term1/k/term2)
   - **3**: View complete inverted index
   - **4**: Exit application

---

## Evaluation Results

The system was evaluated on 6 test queries (3 English + 3 Arabic) using precision and recall metrics against manually judged relevance.

| Query | Relevant Docs | Retrieved Docs | Precision | Recall |
|:------|:---|:---|:---:|:---:|
| machine learning neural networks | en_001, en_003 | en_001, en_003, en_002, en_004 | 50% | 100% |
| cloud computing and smart devices | en_002, en_005 | en_002, en_001, en_004 | 33% | 50% |
| encryption authentication malware | en_004, en_005 | en_004, en_002 | 50% | 50% |
| الذكاء الاصطناعي والتعلم العميق | ar_001, ar_003 | ar_001, ar_003 | 100% | 100% |
| إنترنت الأشياء والحوسبة السحابية | ar_002, ar_005 | ar_002, ar_001 | 50% | 50% |
| التشفير والمصادقة متعددة العوامل | ar_004, ar_005 | ar_004, ar_002 | 50% | 50% |

### **Summary Metrics**
- **Mean Precision**: 55.5% - Most retrieved results are relevant, with some false positives in English queries
- **Mean Recall**: 66.6% - System retrieves about 2/3 of all relevant documents
- **Arabic Performance**: Perfect precision and recall on AI query; competitive performance on other Arabic queries
- **English Performance**: High recall for machine learning query; lower precision on cloud computing query due to overlapping terminology

### **Key Observations**
- Arabic preprocessing pipeline is highly effective
- English pipeline shows strong recall but moderate precision
- Cross-domain vocabulary overlap causes some false positives
- Proximity search successfully filters irrelevant results

---

## Future Improvements

### **Short Term**
- [ ] Boolean search operators (AND, OR, NOT)
- [ ] Wildcard search (term*)
- [ ] Query expansion via thesaurus/synonyms
- [ ] Configurable IDF formula variants
- [ ] User-level relevance feedback

### **Medium Term**
- [ ] Database backend for indexing (instead of in-memory)
- [ ] Distributed indexing across documents
- [ ] Query caching for performance
- [ ] Advanced Arabic NLP (morphological analysis)
- [ ] Support for additional languages

### **Long Term**
- [ ] Machine learning ranking (LambdaMART, etc.)
- [ ] Web crawler for document collection
- [ ] Named entity recognition
- [ ] Deep learning embeddings (Word2Vec, BERT)
- [ ] Real-time indexing pipeline
- [ ] Web-based UI (REST API + frontend)

---

## Implementation Notes

### **Architecture Decisions**
- **Separation of Concerns**: UI logic in `ConsoleHelper`, business logic in `QueryProcessor` and `PositionalIndex`
- **Language Abstraction**: Separate pipelines allow independent optimization for each language
- **Single Scanner**: Centralized input handling in `SearchEngineController` to prevent resource leaks
- **Factory Pattern**: `ConsoleHelper` provides static factory methods for UI operations

### **Known Limitations**
- In-memory indexing limits scalability to small-medium document collections
- Single preprocessing pass (documents indexed as-is after processing)
- No distributed search capability
- Edit distance spelling correction can be slow for large vocabularies

### **Testing**
- Manual evaluation on bilingual test queries
- Precision/Recall metrics computed against ground truth relevance judgments
- Console interface manually verified for usability
