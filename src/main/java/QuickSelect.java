import java.util.Random;

public class QuickSelect {
    private static final Random RANDOM = new Random();

    public static int select(int[] a, int k, Metrics metrics) {
        metrics.startTimer();

        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input: array is empty or k is out of range");
        }

        int result = select(a, 0, a.length - 1, k, 1, metrics);
        metrics.stopTimer();
        return result;
    }

    private static int select(int[] a, int lo, int hi, int k, int depth, Metrics metrics) {
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

            if (k < lt) {
                hi = lt - 1;
            } else if (k > gt) {
                lo = gt + 1;
            } else {
                return a[k];
            }
            depth++;
        }
        return a[lo];
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}