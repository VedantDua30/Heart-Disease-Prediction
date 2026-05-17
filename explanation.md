# 📖 Code Explanation — Heart Disease Predictor

This document provides a complete walkthrough of `HeartDiseasePredictor.java`, explaining every section of the code in detail.

---

## Table of Contents

1. [Overview](#overview)
2. [Training Data](#1-training-data)
3. [Random Forest Concept](#2-random-forest-concept)
4. [bootstrapSampleLabels()](#3-bootstrapsamplelabels)
5. [getRandomFeatureSubset()](#4-getrandomfeaturesubset)
6. [predictWithTree()](#5-predictwithtreet)
7. [predict()](#6-predict)
8. [main()](#7-main)
9. [Key Simplifications](#8-key-simplifications)

---

## Overview

This program implements a **Random Forest classifier** entirely from scratch in Java — no external libraries. It predicts whether a patient is at **HIGH RISK** or **LOW RISK** of heart disease based on 9 medical inputs.

A Random Forest is an **ensemble method**: instead of training one model, we train many small models (Decision Trees) and combine their predictions through majority voting. This reduces the chance of overfitting compared to a single tree.

---

## 1. Training Data

```java
static int[][] trainingData = {
    {63, 1, 3, 145, 233, 1, 0, 150, 0, 0},
    ...
};
static int[] labels = {0, 0, 0, 0, 1, 1, ...};
```

- `trainingData` is a 2D array where each row is one patient record.
- Each row contains 9 feature values followed by nothing — the label is stored separately in `labels[]`.
- `labels[i] = 0` means no heart disease; `labels[i] = 1` means heart disease is present.
- The dataset is inspired by the [UCI Heart Disease Dataset](https://archive.ics.uci.edu/ml/datasets/heart+disease) and is hardcoded for simplicity.

**Column Mapping:**

| Index | Feature |
|-------|---------|
| 0 | Age |
| 1 | Sex |
| 2 | Chest Pain Type |
| 3 | Resting Blood Pressure |
| 4 | Cholesterol |
| 5 | Fasting Blood Sugar |
| 6 | Resting ECG |
| 7 | Max Heart Rate |
| 8 | Exercise Induced Angina |

---

## 2. Random Forest Concept

A **Random Forest** builds multiple Decision Trees and lets them vote. Two key sources of randomness make the trees diverse:

| Technique | Purpose |
|-----------|---------|
| Bootstrap Sampling | Each tree trains on a randomly re-sampled version of data |
| Random Feature Subset | Each tree only looks at a random subset of features |

By making each tree slightly different, the forest as a whole is more accurate and robust than any single tree.

---

## 3. bootstrapSampleLabels()

```java
static int[] bootstrapSampleLabels() {
    int[] sampledLabels = new int[trainingData.length];
    for (int i = 0; i < trainingData.length; i++) {
        int idx = random.nextInt(trainingData.length);
        sampledLabels[i] = labels[idx];
    }
    return sampledLabels;
}
```

**What it does:**
- Creates a new label array of the same size as the training set.
- Randomly picks indices (with replacement) — meaning the same row can be picked more than once, and some rows may be skipped entirely.
- This is called **Bootstrap Sampling** or **Bagging** (Bootstrap Aggregating).

**Why it matters:**
Each tree sees a slightly different version of the labels, so they don't all make identical predictions.

---

## 4. getRandomFeatureSubset()

```java
static int[] getRandomFeatureSubset(int totalFeatures, int subsetSize) {
    List<Integer> indices = new ArrayList<>();
    for (int i = 0; i < totalFeatures; i++) indices.add(i);
    Collections.shuffle(indices, random);
    int[] subset = new int[subsetSize];
    for (int i = 0; i < subsetSize; i++) subset[i] = indices.get(i);
    return subset;
}
```

**What it does:**
- Builds a list of all feature indices (0 through 8 for 9 features).
- Shuffles the list randomly using `Collections.shuffle()`.
- Returns the first `subsetSize` (3) indices as the feature subset for one tree.

**Why it matters:**
If all trees used all 9 features, they would tend to split on the same dominant feature every time, making them highly correlated and reducing the benefit of having multiple trees. Using random subsets forces diversity.

---

## 5. predictWithTree()

```java
static int predictWithTree(int[][] data, int[] treeLabels, int[] sample, int[] featureSubset) {
    int bestFeature = featureSubset[0];
    int bestThreshold = data[0][bestFeature];
    ...
}
```

**What it does:**
This is a **Decision Stump** — the simplest possible Decision Tree with just one split:

1. Picks the first feature from the random subset as the splitting feature.
2. Uses the value of that feature from the first training row as the **threshold**.
3. Counts how many training samples fall on the left (`<= threshold`) and right (`> threshold`) side, separately for label 0 and label 1.
4. When predicting for a new sample, it checks which side the sample falls on and returns the majority class for that side.

**Simplified illustration:**
```
Feature[X] <= threshold ?
    YES → predict majority class of left group
    NO  → predict majority class of right group
```

**Note:** A full Decision Tree would search for the *best* feature and *best* threshold using metrics like Gini Impurity or Information Gain. This simplified version is intentional for college-level clarity.

---

## 6. predict()

```java
static int predict(int[] sample) {
    int numTrees = 10;
    int numFeatures = 9;
    int subsetSize = 3;
    int votes0 = 0, votes1 = 0;

    for (int t = 0; t < numTrees; t++) {
        int[] sampledLabels = bootstrapSampleLabels();
        int[] featureSubset = getRandomFeatureSubset(numFeatures, subsetSize);
        int vote = predictWithTree(trainingData, sampledLabels, sample, featureSubset);
        if (vote == 0) votes0++;
        else votes1++;
    }

    return votes1 > votes0 ? 1 : 0;
}
```

**What it does:**
- Runs 10 trees (the "forest").
- For each tree, generates a new bootstrap label set and a new random feature subset, then calls `predictWithTree()`.
- Tallies votes for class 0 and class 1.
- Returns whichever class received more votes — this is **majority voting**.

**This is the heart of the Random Forest algorithm.**

---

## 7. main()

```java
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    ...
    int[] sample = {age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang};
    int result = predict(sample);
    ...
}
```

**What it does:**
- Uses `Scanner` to collect 9 integer inputs from the user interactively.
- Packages all inputs into a `sample[]` array in the same column order as the training data.
- Calls `predict()` to get the result (0 or 1).
- Prints a human-readable result with a recommendation.

---

## 8. Key Simplifications

This is a college-level implementation. Here's what was intentionally simplified and what a production version would do differently:

| Aspect | This Implementation | Production Version |
|--------|--------------------|--------------------|
| Dataset | 20 hardcoded rows | Thousands of rows from CSV/DB |
| Tree depth | Single split (stump) | Full recursive tree |
| Split criterion | First feature, first value | Best feature via Gini/Entropy |
| Preprocessing | None | Normalization, encoding |
| Validation | None | Cross-validation, accuracy score |
| Libraries | None (pure Java) | Weka, scikit-learn, etc. |

These simplifications keep the code readable, discussable, and appropriate for a college-level ML demonstration.
