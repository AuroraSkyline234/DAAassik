import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static void sort(int[] a, Metrics metrics) {
        metrics.startTimer();
        sort(a, 0, a.length - 1, 1, metrics);
        metrics.stopTimer();
    }

    private static void sort(int[] a, int lo, int hi, int depth, Metrics metrics) {
        while (lo < hi) {
            metrics.updateDepth(depth);

            int pivotIndex = lo + RANDOM.nextInt(hi - lo + 1);
            swap(a, lo, pivotIndex);

            int lt = lo;
            int gt = hi;
            int v = a[lo];
            int i = lo + 1;

            while (i <= gt) {
                metrics.incrementComparisons();
                if (a[i] < v) {
                    swap(a, lt++, i++);
                } else {
                    metrics.incrementComparisons();
                    if (a[i] > v) {
                        swap(a, i, gt--);
                    } else {
                        i++;
                    }
                }
            }

            int leftSize = lt - 1 - lo;
            int rightSize = hi - (gt + 1);

            if (leftSize < rightSize) {
                sort(a, lo, lt - 1, depth + 1, metrics);
                lo = gt + 1;
            } else {
                sort(a, gt + 1, hi, depth + 1, metrics);
                hi = lt - 1;
            }
            depth++;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}