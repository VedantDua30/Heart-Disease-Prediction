# 🫀 Heart Disease Predictor 

A command-line application that predicts the risk of heart disease using a **Random Forest classifier**, built from scratch in pure Java with no external libraries.

---

## 📌 About the Project

This project demonstrates the core concepts of the **Random Forest machine learning algorithm** applied to a medical use case — heart disease prediction. It was built as a college-level project to understand how ensemble learning works under the hood, without relying on any ML frameworks.

The model takes 9 patient health parameters as input and outputs either a **HIGH RISK** or **LOW RISK** prediction.

---

## 🧠 How It Works

The program implements a simplified Random Forest from scratch:

1. **Training Data** — A small hardcoded dataset of 20 patient records, inspired by the UCI Heart Disease dataset.
2. **Bootstrap Sampling** — Each tree in the forest is trained on a randomly sampled (with replacement) version of the data.
3. **Random Feature Subsets** — Each tree considers only 3 randomly chosen features out of 9, reducing correlation between trees.
4. **Decision Stumps** — Each tree is a single-level decision tree that splits data on one feature and predicts by majority class.
5. **Majority Voting** — 10 trees vote, and the class with more votes becomes the final prediction.

---

## 🩺 Input Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | Age | Patient's age in years |
| 2 | Sex | 1 = Male, 0 = Female |
| 3 | Chest Pain Type | 0 = Typical Angina, 1 = Atypical Angina, 2 = Non-Anginal, 3 = Asymptomatic |
| 4 | Resting Blood Pressure | In mm Hg |
| 5 | Cholesterol | Serum cholesterol in mg/dl |
| 6 | Fasting Blood Sugar | 1 = FBS > 120 mg/dl, 0 = otherwise |
| 7 | Resting ECG | 0 = Normal, 1 = ST-T Abnormality, 2 = Left Ventricular Hypertrophy |
| 8 | Max Heart Rate | Maximum heart rate achieved |
| 9 | Exercise Induced Angina | 1 = Yes, 0 = No |

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 8 or above installed
- A terminal / command prompt

### Installation

```bash
# Clone the repository
git clone https://github.com/your-username/heart-disease-predictor.git

# Navigate into the project directory
cd heart-disease-predictor
```

### Compile & Run

```bash
javac HeartDiseasePredictor.java
java HeartDiseasePredictor
```

---

## 💻 Sample Output

```
=== Heart Disease Predictor (Random Forest) ===

Enter Age: 55
Enter Sex (1 = Male, 0 = Female): 1
Enter Chest Pain Type (...): 2
Enter Resting Blood Pressure (in mm Hg): 140
Enter Cholesterol Level (in mg/dl): 250
Enter Fasting Blood Sugar > 120 mg/dl (1 = True, 0 = False): 0
Enter Resting ECG Results (...): 1
Enter Maximum Heart Rate Achieved: 155
Enter Exercise Induced Angina (1 = Yes, 0 = No): 1

=== Prediction Result ===
The model predicts: HIGH RISK of Heart Disease.
Please consult a cardiologist immediately.
```

---

## 📁 Project Structure

```
heart-disease-predictor/
│
├── HeartDiseasePredictor.java   # Main source file with full implementation
└── README.md                    # Project documentation
```

---

## ⚙️ Algorithm Details

| Parameter | Value |
|-----------|-------|
| Number of Trees | 10 |
| Features per Tree | 3 (randomly selected) |
| Tree Depth | 1 (Decision Stump) |
| Sampling Method | Bootstrap (with replacement) |
| Voting Strategy | Majority Vote |
| Random Seed | 42 (for reproducibility) |

---

## ⚠️ Disclaimer

> This tool is built purely for **educational and demonstration purposes**. It is **not** a substitute for professional medical advice, diagnosis, or treatment. Always consult a qualified healthcare provider for any medical concerns.

---

## 🔧 Limitations

- Training dataset is small (20 records) and hardcoded
- Decision trees are single-level stumps (not full trees)
- No input validation or preprocessing
- No cross-validation or accuracy measurement
- Not suitable for real-world clinical use

---

## 🌱 Future Improvements

- Load training data from a CSV file
- Implement full Decision Trees with Gini impurity
- Add input validation and error handling
- Display confidence score (vote percentage)
- Build a simple GUI using Java Swing

---

## 🎓 Concepts Demonstrated

- Random Forest Ensemble Learning
- Bootstrap Aggregating (Bagging)
- Decision Tree Classification
- Majority Voting
- Feature Subset Selection
- Object-Oriented Java Programming

---

## 📜 License

This project is open source and available under the [MIT License](LICENSE).

---
