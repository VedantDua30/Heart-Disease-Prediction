import java.util.*;

public class HeartDiseasePredictor {

    static int[][] trainingData = {
        {63, 1, 3, 145, 233, 1, 0, 150, 0, 0},
        {37, 1, 2, 130, 250, 0, 1, 187, 0, 0},
        {41, 0, 1, 130, 204, 0, 0, 172, 0, 0},
        {56, 1, 1, 120, 236, 0, 1, 178, 0, 0},
        {57, 0, 0, 120, 354, 0, 1, 163, 1, 1},
        {57, 1, 0, 140, 192, 0, 1, 148, 0, 1},
        {56, 0, 1, 140, 294, 0, 0, 153, 0, 1},
        {44, 1, 1, 120, 263, 0, 1, 173, 0, 1},
        {52, 1, 2, 172, 199, 1, 1, 162, 0, 1},
        {57, 1, 2, 150, 168, 0, 1, 174, 0, 1},
        {54, 1, 0, 140, 239, 0, 1, 160, 0, 1},
        {48, 0, 2, 130, 275, 0, 1, 139, 0, 1},
        {49, 1, 1, 130, 266, 0, 1, 171, 0, 1},
        {64, 1, 3, 110, 211, 0, 0, 144, 1, 0},
        {58, 0, 3, 150, 283, 1, 0, 162, 0, 0},
        {50, 1, 2, 129, 196, 0, 1, 163, 0, 0},
        {58, 1, 2, 112, 230, 0, 1, 165, 0, 0},
        {66, 0, 3, 150, 226, 0, 1, 114, 0, 1},
        {43, 1, 0, 150, 247, 0, 1, 171, 0, 1},
        {69, 0, 3, 140, 239, 0, 1, 151, 0, 1}
    };

    static int[] labels = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1};

    static Random random = new Random(42);

    static int predictWithTree(int[][] data, int[] treeLabels, int[] sample, int[] featureSubset) {
        int bestFeature = featureSubset[0];
        int bestThreshold = data[0][bestFeature];
        int leftCount0 = 0, leftCount1 = 0, rightCount0 = 0, rightCount1 = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i][bestFeature] <= bestThreshold) {
                if (treeLabels[i] == 0) leftCount0++;
                else leftCount1++;
            } else {
                if (treeLabels[i] == 0) rightCount0++;
                else rightCount1++;
            }
        }

        if (sample[bestFeature] <= bestThreshold) {
            return leftCount1 > leftCount0 ? 1 : 0;
        } else {
            return rightCount1 > rightCount0 ? 1 : 0;
        }
    }

    static int[] bootstrapSampleLabels() {
        int[] sampledLabels = new int[trainingData.length];
        for (int i = 0; i < trainingData.length; i++) {
            int idx = random.nextInt(trainingData.length);
            sampledLabels[i] = labels[idx];
        }
        return sampledLabels;
    }

    static int[] getRandomFeatureSubset(int totalFeatures, int subsetSize) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < totalFeatures; i++) indices.add(i);
        Collections.shuffle(indices, random);
        int[] subset = new int[subsetSize];
        for (int i = 0; i < subsetSize; i++) subset[i] = indices.get(i);
        return subset;
    }

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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Heart Disease Predictor (Random Forest) ===\n");

        System.out.print("Enter Age: ");
        int age = sc.nextInt();

        System.out.print("Enter Sex (1 = Male, 0 = Female): ");
        int sex = sc.nextInt();

        System.out.print("Enter Chest Pain Type (0 = Typical Angina, 1 = Atypical Angina, 2 = Non-Anginal, 3 = Asymptomatic): ");
        int cp = sc.nextInt();

        System.out.print("Enter Resting Blood Pressure (in mm Hg): ");
        int trestbps = sc.nextInt();

        System.out.print("Enter Cholesterol Level (in mg/dl): ");
        int chol = sc.nextInt();

        System.out.print("Enter Fasting Blood Sugar > 120 mg/dl (1 = True, 0 = False): ");
        int fbs = sc.nextInt();

        System.out.print("Enter Resting ECG Results (0 = Normal, 1 = ST-T Abnormality, 2 = Left Ventricular Hypertrophy): ");
        int restecg = sc.nextInt();

        System.out.print("Enter Maximum Heart Rate Achieved: ");
        int thalach = sc.nextInt();

        System.out.print("Enter Exercise Induced Angina (1 = Yes, 0 = No): ");
        int exang = sc.nextInt();

        int[] sample = {age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang};

        int result = predict(sample);

        System.out.println("\n=== Prediction Result ===");
        if (result == 1) {
            System.out.println("The model predicts: HIGH RISK of Heart Disease.");
            System.out.println("Please consult a cardiologist immediately.");
        } else {
            System.out.println("The model predicts: LOW RISK of Heart Disease.");
            System.out.println("Maintain a healthy lifestyle and regular checkups.");
        }

        sc.close();
    }
}
