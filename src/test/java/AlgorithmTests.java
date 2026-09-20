import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTests {
    private static final Random RANDOM = new Random();

    @Test
    void testMergeSortRandomArrays() {
        for (int i = 0; i < 100; i++) {
            int n = RANDOM.nextInt(1000) + 1; // размер от 1 до 1000
            int[] original = generateRandomArray(n);
            int[] expected = original.clone();
            int[] actual = original.clone();

            Arrays.sort(expected);
            MergeSort.sort(actual, new Metrics());

            assertArrayEquals(expected, actual, "MergeSort failed on random array");
        }
    }

    @Test
    void testQuickSortRandomArrays() {
        for (int i = 0; i < 100; i++) {
            int n = RANDOM.nextInt(1000) + 1;
            int[] original = generateRandomArray(n);
            int[] expected = original.clone();
            int[] actual = original.clone();

            Arrays.sort(expected);
            QuickSort.sort(actual, new Metrics());

            assertArrayEquals(expected, actual, "QuickSort failed on random array");
        }
    }

    @Test
    void testQuickSelectRandomArrays() {
        for (int i = 0; i < 100; i++) {
            int n = RANDOM.nextInt(1000) + 1;
            int k = RANDOM.nextInt(n);
            int[] original = generateRandomArray(n);
            int[] sorted = original.clone();
            Arrays.sort(sorted);

            int actualKth = QuickSelect.select(original.clone(), k, new Metrics());
            assertEquals(sorted[k], actualKth, "QuickSelect failed to find k-th element");
        }
    }

    @Test
    void testEdgeCases() {
        Metrics m = new Metrics();

        int[] empty = {};
        MergeSort.sort(empty, m);
        QuickSort.sort(empty, m);
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(empty, 0, m));

        int[] single = {42};
        MergeSort.sort(single, m);
        assertArrayEquals(new int[]{42}, single);

        int[] equal = {5, 5, 5, 5, 5};
        int[] equalClone = equal.clone();
        QuickSort.sort(equalClone, m);
        assertArrayEquals(equal, equalClone);

        int[] sorted = {1, 2, 3, 4, 5};
        int[] sortedClone = sorted.clone();
        MergeSort.sort(sortedClone, m);
        assertArrayEquals(sorted, sortedClone);
    }

    @Test
    void testQuickSortMaxDepth() {
        int n = 100000;
        int[] sortedArray = new int[n];
        for (int i = 0; i < n; i++) {
            sortedArray[i] = i;
        }

        Metrics metrics = new Metrics();
        QuickSort.sort(sortedArray, metrics);

        double log2n = Math.log(n) / Math.log(2);
        int maxAllowedDepth = (int) Math.ceil(2 * log2n);

        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "Max depth exceeded! Actual: " + metrics.getMaxDepth() + ", Allowed: " + maxAllowedDepth);
    }

    private int[] generateRandomArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = RANDOM.nextInt(10000);
        }
        return a;
    }
}