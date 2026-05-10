package features.Indexing;

import java.io.File;
import java.util.*;
import utils.FileReader;

public class PositionalIndex {

    // term → (docId → list of positions)
    private Map<String, Map<Integer, List<Integer>>> index;

    // docId → اسم الملف
    private Map<Integer, String> docNames;

    private int docCounter;

    public PositionalIndex() {
        index       = new HashMap<>();
        docNames   = new HashMap<>();
        docCounter = 0;
    }


    public Set<String> getVocabulary() {

        Set<String> vocab = new HashSet<>();
        for (String term : index.keySet()) {
            vocab.add(term);
        }

        return vocab;
    }

    public void buildIndex(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("الفولدر فاضي أو مش موجود: " + folderPath);
            return;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                String content = FileReader.readFile(files[i].getPath());
                addDocument(docCounter, files[i].getName(), content);
                docCounter++;
            }
        }
    }


    // إضافة document — الكلمات جاهزة، بس نقسمها ونخزنها

    private void addDocument(int docId, String docName, String content) {
        docNames.put(docId, docName);

        // تقسيم على المسافات بس — الكلمات معالجة خلاص
        String[] tokens = content.trim().split("\\s+");

        for (int position = 0; position < tokens.length; position++) {
            String term = tokens[position];

            if (term == null || term.isEmpty()) {
                continue;
            }

            if (!index.containsKey(term)) {
                index.put(term, new HashMap<>());
            }

            Map<Integer, List<Integer>> docMap = index.get(term);

            if (!docMap.containsKey(docId)) {
                docMap.put(docId, new ArrayList<>());
            }

            docMap.get(docId).add(position);
        }
    }


    // جيب الـ postings بتاعة كلمة

    public Map<Integer, List<Integer>> getPostings(String term) {
        if (index.containsKey(term)) {
            return index.get(term);
        }
        return new HashMap<>();
    }


    // Proximity Search — مثال: employment /3 place

    public List<Integer> proximitySearch(String term1, String term2, int k) {
        List<Integer> result = new ArrayList<>();

        Map<Integer, List<Integer>> postings1 = getPostings(term1);
        Map<Integer, List<Integer>> postings2 = getPostings(term2);

        for (int docId : postings1.keySet()) {
            if (!postings2.containsKey(docId)) {
                continue;
            }

            List<Integer> positions1 = postings1.get(docId);
            List<Integer> positions2 = postings2.get(docId);

            boolean found = false;
            for (int i = 0; i < positions1.size() && !found; i++) {
                for (int j = 0; j < positions2.size() && !found; j++) {
                    int distance = Math.abs(positions1.get(i) - positions2.get(j));
                    if (distance <= k) {
                        found = true;
                    }
                }
            }

            if (found) {
                result.add(docId);
            }
        }

        return result;
    }


    // Methods للـ TFIDF والـ QueryParser

    public Map<String, Map<Integer, List<Integer>>> getFullIndex() {
        return index;
    }

    public int getTotalDocs() {
        return docNames.size();
    }

    public String getDocName(int docId) {
        return docNames.getOrDefault(docId, "Unknown");
    }

    public Map<Integer, String> getAllDocNames() {
        return docNames;
    }


    public void printIndex() {
        for (String term : index.keySet()) {
            System.out.print(term + " → ");
            Map<Integer, List<Integer>> docMap = index.get(term);
            for (int docId : docMap.keySet()) {
                System.out.print("Doc" + docId +
                        "(" + docNames.get(docId) + ")" +
                        ": " + docMap.get(docId) + "  ");
            }
            System.out.println();
        }
    }
}