# Search Engine Evaluation Report

This report presents the performance evaluation of the Information Retrieval (IR) Search Engine using standard ranked retrieval metrics.

The evaluation was conducted on a bilingual dataset containing English and Arabic Computer Science documents.

---

# 1. Evaluation Metrics

To evaluate the effectiveness of the search engine, two standard Information Retrieval metrics were used:

## Precision

Precision measures the accuracy of the retrieved results.

\[
Precision = \frac{Relevant\ Documents\ Retrieved}{Total\ Retrieved\ Documents}
\]

A high precision value indicates that most retrieved documents are relevant to the query.

---

## Recall

Recall measures the system’s ability to retrieve all relevant documents from the dataset.

\[
Recall = \frac{Relevant\ Documents\ Retrieved}{Total\ Relevant\ Documents}
\]

A high recall value indicates that the search engine successfully retrieves most relevant documents.

---

# 2. Test Queries

The following queries were used to evaluate the system:

## English Queries

```text
machine learning neural networks
cloud computing and smart devices
encryption authentication malware
```

## Arabic Queries

```text
الذكاء الاصطناعي والتعلم العميق
إنترنت الأشياء والحوسبة السحابية
التشفير والمصادقة متعددة العوامل
```

---

# 3. Evaluation Results

| Query | Relevant Documents | Retrieved Documents | Precision | Recall |
| :--- | :--- | :--- | :--- | :--- |
| machine learning neural networks | en_001, en_003 | en_001, en_003, en_002, en_004 | 50% | 100% |
| cloud computing and smart devices | en_002, en_005 | en_002, en_001, en_004 | 33% | 50% |
| encryption authentication malware | en_004, en_005 | en_004, en_002 | 50% | 50% |
| الذكاء الاصطناعي والتعلم العميق | ar_001, ar_003 | ar_001, ar_003 | 100% | 100% |
| إنترنت الأشياء والحوسبة السحابية | ar_002, ar_005 | ar_002, ar_001 | 50% | 50% |
| التشفير والمصادقة متعددة العوامل | ar_004, ar_005 | ar_004, ar_002 | 50% | 50% |

---

# 4. Performance Analysis

## English Queries

The search engine achieved strong recall performance for AI-related queries, successfully retrieving all relevant documents for the query:

```text
machine learning neural networks
```

Some false positives appeared in Cloud Computing and Cybersecurity queries due to overlapping technical terminology between Computer Science domains.

---

## Arabic Queries

The Arabic retrieval pipeline demonstrated strong performance overall.

The query:

```text
الذكاء الاصطناعي والتعلم العميق
```

achieved perfect precision and recall, indicating that Arabic preprocessing and ranking are functioning effectively.

Other Arabic queries correctly retrieved the primary relevant document but included some unrelated technical documents.

---

# 5. Final Aggregated Results

Based on the six evaluation queries, the overall average performance of the search engine is:

- Mean Precision: 55.5%
- Mean Recall: 66.6%

Overall, the system provides reliable bilingual document retrieval and demonstrates successful implementation of indexing, preprocessing, ranking, and query handling techniques.