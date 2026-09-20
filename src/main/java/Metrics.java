public class Metrics {
    private long comparisons = 0;
    private int maxDepth = 0;
    private long startTime = 0;
    private long timeMs = 0;

    public void incrementComparisons() {
        comparisons++;
    }

    public void updateDepth(int currentDepth) {
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        timeMs = (System.nanoTime() - startTime) / 1000000;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getTimeMs() {
        return timeMs;
    }
}