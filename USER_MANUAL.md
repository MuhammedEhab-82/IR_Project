# Quick Start Guide - Search Engine User Manual

Welcome to our Search Engine! This guide will help you use the application.

---

## Getting Started

### Prerequisites
- Java 11 or higher installed
- Project compiled and ready

### Starting the Application

#### On Windows (PowerShell):
```powershell
cd E:\7mhab\coding\IRproject
javac -cp src -d out src/Main.java src/SearchEngineController.java src/ConsoleHelper.java
java -cp out Main
```

#### On Linux/Mac:
```bash
cd /path/to/IRproject
javac -cp src -d out src/Main.java src/SearchEngineController.java src/ConsoleHelper.java
java -cp out Main
```

### What You'll See
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
```

Then you'll see the main menu.

---

## Main Menu

```
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

## Option 1: Ranked Search

### What It Does
Searches for your query across all documents and returns results ranked by relevance.

### How to Use

1. **Select Option:** Type `1` and press Enter
2. **Enter Query:** Type your search terms (English or Arabic)
3. **View Results:** Results appear sorted by relevance score

### Example 1: English Query
```
Please choose an option (1-4): 1

─────────────────────────────────────────────────────────────
          RANKED SEARCH
─────────────────────────────────────────────────────────────

Enter your search query (English or Arabic): neural networks
⏳ Processing query: "neural networks"...

✅ Search results found: 3 result(s)

  1. doc0.txt
  2. doc1.txt
  3. doc5.txt
```

### Example 2: Arabic Query
```
Please choose an option (1-4): 1

─────────────────────────────────────────────────────────────
          RANKED SEARCH
─────────────────────────────────────────────────────────────

Enter your search query (English or Arabic): البحث عن المعلومات
⏳ Processing query: "البحث عن المعلومات"...

✅ Search results found: 2 result(s)

  1. doc3.txt
  2. doc7.txt
```

### Tips
- Use multiple terms for better results: `"machine learning algorithms"`
- Mix English and Arabic: `"neural networks الخوارزميات"`
- Results are ranked by relevance score (higher = more relevant)
- If no results found, try different terms

---

## Option 2: Proximity Search

### What It Does
Finds documents where two terms appear within a specified distance of each other.

### How to Use

1. **Select Option:** Type `2` and press Enter
2. **Enter First Term:** Type the first search term
3. **Enter Second Term:** Type the second search term
4. **Enter Distance:** Type the maximum distance between terms (as a number)
5. **View Results:** Matching documents are displayed

### Example 1: English Proximity Search
```
Please choose an option (1-4): 2

─────────────────────────────────────────────────────────────
          PROXIMITY SEARCH
─────────────────────────────────────────────────────────────

Enter two terms and the maximum distance between them.

Enter first term: neural
Enter second term: learning
Enter maximum distance (k): 5
⏳ Processing proximity query: "neural/5/learning"...

✅ Proximity results found: 2 result(s)

  1. doc0.txt
  2. doc2.txt
```

### Example 2: Arabic Proximity Search
```
Please choose an option (1-4): 2

─────────────────────────────────────────────────────────────
          PROXIMITY SEARCH
─────────────────────────────────────────────────────────────

Enter two terms and the maximum distance between them.

Enter first term: البحث
Enter second term: المعلومات
Enter maximum distance (k): 3
⏳ Processing proximity query: "البحث/3/المعلومات"...

✅ Proximity results found: 1 result(s)

  1. doc5.txt
```

### Distance Explanation
- **Distance 1:** Terms must be adjacent
- **Distance 2:** Maximum 1 word between them
- **Distance 3:** Maximum 2 words between them
- **Distance 5:** Maximum 4 words between them
- **Higher values:** Looser constraint

### Tips
- Smaller distance = stricter requirement
- Try distance between 3-5 for good results
- Both terms must exist in the document
- Works with both English and Arabic terms

---

## Option 3: Print Index

### What It Does
Displays the entire inverted index used by the search engine. Shows all terms and which documents contain them.

### How to Use

1. **Select Option:** Type `3` and press Enter
2. **View Index:** The complete index is displayed
3. **Analyze:** See which documents contain each term

### Example Output
```
Please choose an option (1-4): 3

─────────────────────────────────────────────────────────────
          INVERTED INDEX
─────────────────────────────────────────────────────────────

Term: "algorithm"
  Postings: doc0 (positions: 5, 12), doc2 (positions: 3, 8, 15)

Term: "البحث"
  Postings: doc3 (positions: 2), doc5 (positions: 1, 9)

Term: "learning"
  Postings: doc0 (positions: 8), doc1 (positions: 4, 11, 18)

... (more terms) ...
```

### What This Shows
- **Term:** The processed search term
- **Postings:** Documents containing the term
- **Positions:** Word positions within each document

### Tips
- This is useful for debugging
- Shows how documents were indexed
- Helps understand search results
- Large indexes may take time to display

---

## Option 4: Exit

### What It Does
Gracefully closes the application and displays a thank you message.

### How to Use
1. **Select Option:** Type `4` and press Enter
2. **Application Closes:** Exit message is displayed
3. **Done:** You're back to the command prompt

### Example
```
Please choose an option (1-4): 4

╔════════════════════════════════════════════════════════════╗
║  شكراً لاستخدامك محرك البحث - Thank you for using us! 😉 ║
║       ادينا الفل مارك احنا غلابة 😢                        ║
╚════════════════════════════════════════════════════════════╝

PS E:\7mhab\coding\IRproject>
```

---

## Common Tasks

### Task 1: Search for a Topic
```
1. Select option 1 (Ranked Search)
2. Enter your search terms
3. Review results in order of relevance
4. For more searches, option 1 will appear in menu again
```

### Task 2: Find Terms Close Together
```
1. Select option 2 (Proximity Search)
2. Enter both terms you want to find together
3. Set distance (try 3-5 first)
4. Review matching documents
```

### Task 3: Understand What's Indexed
```
1. Select option 3 (Print Index)
2. Look for your terms of interest
3. See which documents contain them
```

### Task 4: Run Multiple Searches
```
1. Perform a search (option 1 or 2)
2. Results appear
3. Main menu appears again
4. Select a different option or search for something else
5. Repeat until done
```

### Task 5: Exit the Program
```
1. Select option 4
2. Thank you message appears
3. Program closes
```

---

## Tips & Tricks

### For Better Search Results

1. **Be Specific**
   - ❌ "good"
   - ✅ "machine learning"

2. **Use Multiple Terms**
   - ❌ "search"
   - ✅ "information retrieval search"

3. **Mix Languages (if needed)**
   - ✅ "neural networks الشبكات العصبية"

4. **Try Proximity Search for Exact Phrases**
   - ✅ "machine"/"3"/"learning" (finds "machine learning algorithms")

### Understanding Results

- **Higher in the list = More relevant**
- **Score indicates relevance level**
- **Results update with each new query**

### If No Results Found

1. **Check spelling** (system corrects some errors automatically)
2. **Try fewer terms** ("learning" instead of "deep neural networks")
3. **Try different terms** (synonyms might work better)
4. **Check the index** (option 3) to see available terms
5. **Try proximity search** (option 2) for phrase searches

### Understanding the Index

- **Terms are processed** (lowercased, stemmed)
- **Stop words removed** (a, the, for, etc.)
- **Arabic and English** are processed separately
- **Positions show** where terms appear in documents

---

## Supported Languages

### English
- ✅ Full support
- ✅ Multiple preprocessing options
- ✅ Porter Stemmer for stemming
- ✅ English stop words filtering

### Arabic
- ✅ Full support
- ✅ Arabic normalization
- ✅ Arabic stemming
- ✅ Arabic stop words filtering
- ✅ Proper Unicode handling

### Mixed Queries
- ✅ Can mix English and Arabic in one query
- ✅ System automatically detects language
- ✅ Each language processed separately
- ✅ Results combined intelligently

---

## Error messages & Solutions

### "Query cannot be empty"
- **Cause:** You pressed Enter without typing anything
- **Solution:** Type a search query and try again

### "Invalid choice. Please enter 1-4"
- **Cause:** You typed something other than 1, 2, 3, or 4
- **Solution:** Enter a valid menu number

### "Invalid distance value. Please enter a number"
- **Cause:** Distance in proximity search is not a number
- **Solution:** Enter a positive whole number (e.g., 3, 5, 10)

### "Both terms are required"
- **Cause:** You didn't enter both terms for proximity search
- **Solution:** Enter both term1 and term2

### "No results found"
- **Cause:** No documents match your query
- **Solution:** Try different search terms or check Option 3 (Print Index)

---

## FAQ

**Q: How many documents can I search?**
A: The system supports multiple documents from both English and Arabic folders (currently 20 documents).

**Q: Can I search in Arabic?**
A: Yes! You can search in Arabic, English, or a mix of both.

**Q: How are results ranked?**
A: Results are ranked using TF-IDF (Term Frequency-Inverse Document Frequency), a standard IR metric.

**Q: What does distance mean in proximity search?**
A: Distance is the maximum number of words allowed between your two terms.

**Q: Can I save search results?**
A: The current version displays results on screen. You can copy/paste results as needed.

**Q: Why was my query corrected?**
A: The system has automatic spelling correction for common misspellings.

**Q: What are stop words?**
A: Common words like "a", "the", "and" that are filtered out because they appear in most documents.

**Q: Can I search for exact phrases?**
A: Use Proximity Search (Option 2) to find terms within a specific distance.

**Q: How long will indexing take?**
A: Usually a few seconds for the sample documents provided.

**Q: What if I press Ctrl+C?**
A: The program will terminate. Run it again to restart.

---

## Keyboard Shortcuts

| Action | Keys |
|--------|------|
| Move to next line | Enter |
| Copy text | Ctrl+C |
| Paste text | Ctrl+V |
| Clear line | Ctrl+U (may depend on system) |
| Exit on prompt | Ctrl+C (will force quit) |

---

## Getting Help

If you encounter an issue:

1. **Check the error message** - It usually tells you what went wrong
2. **Review this guide** - Most common issues are addressed
3. **Check the index** (Option 3) - Verify what terms exist
4. **Try different search terms** - The original query may not match any documents

---

## Next Steps

After you're comfortable with the basics:

1. **Explore all three search types** - Get familiar with each
2. **Try different queries** - Learn what works best
3. **Review the index** - Understand the system
4. **Mix English and Arabic** - Test multilingual support
5. **Report any issues** - Help improve the system

---

## Support Resources

- **Refactoring Guide:** `REFACTORING_GUIDE.md` (for developers)
- **Developer Guide:** `DEVELOPER_GUIDE.md` (for adding features)
- **Flow Diagrams:** `FLOW_DIAGRAMS.md` (for understanding architecture)
- **Refactoring Summary:** `REFACTORING_SUMMARY.md` (for quick overview)

---

**Happy Searching! 🔍**

شكراً لاستخدامك محرك البحث بتاعنا 😉

