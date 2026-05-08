# Search Engine Evaluation Report

This document outlines the performance evaluation of the Search Engine using standard Information Retrieval (IR) metrics. The testing was conducted on a dataset of 20 documents (10 English and 10 Arabic).

## 1. Evaluation Metrics

To measure the effectiveness of the ranked retrieval system, two primary metrics were used:

*   **Precision:** Measures the accuracy of the retrieved results. It is the ratio of relevant documents retrieved to the total documents retrieved by the system.
    $$Precision = \frac{\text{Relevant Documents Retrieved}}{\text{Total Documents Retrieved}}$$

*   **Recall:** Measures the ability of the system to find all relevant documents in the dataset. It is the ratio of relevant documents retrieved to the total number of relevant documents available.
    $$Recall = \frac{\text{Relevant Documents Retrieved}}{\text{Total Relevant Documents in Dataset}}$$

---

## 2. Test Results (0-Indexed)

The following table summarizes the system's performance across 5 diverse queries:

| Query | Relevant Documents (Ground Truth) | Retrieved Documents (Engine Output) | Precision (P) | Recall (R) |
| :--- | :--- | :--- | :--- | :--- |
| **Neural Networks** | 0, 1, 3, 5, 7 | 8, 9, 2, 3, 5, 0, 1 | 57% | 80% |
| **Supervised Learning** | 0, 1, 2 | 2, 0, 1, 6 | 75% | 100% |
| **Evaluation Metrics** | 8 | 8 | 100% | 100% |
| **قواعد البيانات** | 2, 3, 4, 5, 6, 8, 9 | 9, 3, 2, 5, 6, 8 | 100% | 86% |
| **الأمن السيبراني** | 4, 5, 8 | 8, 4, 5 | 100% | 100% |

---

## 3. Performance Analysis

### Query-Specific Observations:
*   **Neural Networks:** The system achieved a high recall (80%), successfully retrieving 4 out of 5 core documents. The lower precision was due to "noise" from other technical documents sharing similar vocabulary.
*   **Supervised Learning:** Near-perfect ranking. The top three results matched the ground truth exactly, ensuring a high-quality user experience.
*   **Arabic Queries:** The engine showed exceptional performance in Arabic text processing, particularly in the "Cybersecurity" (الأمن السيبراني) query, achieving a perfect score in both metrics.

---

## 4. Final Aggregated Results

Based on the test cases above, the overall system averages are:

*   **Mean Precision:** **86.4%**
*   **Mean Recall:** **93.2%**

**Conclusion:** The engine demonstrates a strong bias toward high recall, ensuring that users rarely miss relevant information. The precision remains robust, especially within specialized Computer Science domains.