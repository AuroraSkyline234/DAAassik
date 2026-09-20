public class MergeSort {
    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics metrics) {
        metrics.startTimer();
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1, 1, metrics);
        metrics.stopTimer();
    }

    private static void sort(int[] a, int[] aux, int lo, int hi, int depth, Metrics metrics) {
        metrics.updateDepth(depth);

        if (hi - lo + 1 <= CUTOFF) {
            insertionSort(a, lo, hi, metrics);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, depth + 1, metrics);
        sort(a, aux, mid + 1, hi, depth + 1, metrics);

        merge(a, aux, lo, mid, hi, metrics);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi, Metrics metrics) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else {
                metrics.incrementComparisons();
                if (aux[j] < aux[i]) {
                    a[k] = aux[j++];
                } else {
                    a[k] = aux[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics metrics) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo; j--) {
                metrics.incrementComparisons();
                if (a[j] < a[j - 1]) {
                    int temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
    }
}