import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final int RUNS = 5;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("algorithm,input,n,time_ms,comparisons,max_depth\n");

            for (int n : SIZES) {
                System.out.println("start for n = " + n);
                runTests(writer, "random", generateRandomArray(n));
                runTests(writer, "sorted", generateSortedArray(n));
                runTests(writer, "duplicates", generateDuplicatesArray(n));
            }
            System.out.println("Finished results in results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runTests(FileWriter writer, String inputType, int[] originalArray) throws IOException {
        int n = originalArray.length;

        long[] mergeTimes = new long[RUNS];
        Metrics mergeMetrics = new Metrics();
        for (int i = 0; i < RUNS; i++) {
            int[] a = originalArray.clone();
            mergeMetrics = new Metrics();
            MergeSort.sort(a, mergeMetrics);
            mergeTimes[i] = mergeMetrics.getTimeMs();
        }
        Arrays.sort(mergeTimes);
        writer.write(String.format("MergeSort,%s,%d,%d,%d,%d\n",
                inputType, n, mergeTimes[RUNS / 2], mergeMetrics.getComparisons(), mergeMetrics.getMaxDepth()));

        long[] quickTimes = new long[RUNS];
        Metrics quickMetrics = new Metrics();
        for (int i = 0; i < RUNS; i++) {
            int[] a = originalArray.clone();
            quickMetrics = new Metrics();
            QuickSort.sort(a, quickMetrics);
            quickTimes[i] = quickMetrics.getTimeMs();
        }
        Arrays.sort(quickTimes);
        writer.write(String.format("QuickSort,%s,%d,%d,%d,%d\n",
                inputType, n, quickTimes[RUNS / 2], quickMetrics.getComparisons(), quickMetrics.getMaxDepth()));

        long[] selectTimes = new long[RUNS];
        Metrics selectMetrics = new Metrics();
        for (int i = 0; i < RUNS; i++) {
            int[] a = originalArray.clone();
            selectMetrics = new Metrics();
            QuickSelect.select(a, n / 2, selectMetrics);
            selectTimes[i] = selectMetrics.getTimeMs();
        }
        Arrays.sort(selectTimes);
        writer.write(String.format("QuickSelect,%s,%d,%d,%d,%d\n",
                inputType, n, selectTimes[RUNS / 2], selectMetrics.getComparisons(), selectMetrics.getMaxDepth()));
    }

    private static int[] generateRandomArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = RANDOM.nextInt();
        }
        return a;
    }

    private static int[] generateSortedArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
    }

    private static int[] generateDuplicatesArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = RANDOM.nextInt(10);
        }
        return a;
    }
}