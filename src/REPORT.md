# Design and Analysis of Algorithms - Assignment 1
**Student:** Islam Bolat
**Group:** SE-2521

## 1. Asymptotic Bounds

| Algorithm | Best Case | Average Case | Worst Case | Reason (One-line explanation) |
| :--- | :--- | :--- | :--- | :--- |
| **MergeSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\mathcal{O}(n \log n)$ | Always divides the array exactly in half and takes $\Theta(n)$ to merge, regardless of input. |
| **QuickSort** | $\Omega(n)$ | $\Theta(n \log n)$ | $\mathcal{O}(n^2)$ | Best: All elements equal (3-way partition handles in linear time). Avg: Random pivot gives balanced splits. Worst: Unlucky pivots (e.g., max/min chosen every time). |
| **QuickSelect** | $\Omega(n)$ | $\Theta(n)$ | $\mathcal{O}(n^2)$ | Best: Pivot is the k-th element or all equal. Avg: Random pivot discards a fraction of the array. Worst: Unlucky pivots reduce size by 1. |
| **Insertion Sort**| $\Omega(n)$ | $\Theta(n^2)$ | $\mathcal{O}(n^2)$ | Best: Array is already sorted (only 1 comparison per element). Worst: Reverse sorted array. |

## 2. Recurrences and Master Theorem

### MergeSort
*   **Recurrence:** $T(n) = 2T(n/2) + \Theta(n)$
*   **Parameters:** $a = 2$, $b = 2$, $f(n) = \Theta(n)$
*   **Master Theorem Case:** $n^{\log_b a} = n^{\log_2 2} = n^1$. Since $f(n) = \Theta(n^{\log_b a})$, this is **Case 2**.
*   **Result:** $T(n) = \Theta(n \log n)$

### QuickSort (Assuming balanced split)
*   **Recurrence:** $T(n) = 2T(n/2) + \Theta(n)$
*   **Parameters:** $a = 2$, $b = 2$, $f(n) = \Theta(n)$
*   **Master Theorem Case:** **Case 2**, yielding $T(n) = \Theta(n \log n)$.
*   **Random Pivot Explanation:** While the worst-case split is $T(n) = T(n-1) + \Theta(n)$, a random pivot ensures that on average, the array is divided into proportional constant fractions (e.g., $1/4$ and $3/4$). This keeps the recursion tree depth bounded to $\mathcal{O}(\log n)$, and with $\mathcal{O}(n)$ work per level, the average expected running time remains $\mathcal{O}(n \log n)$.

### QuickSelect (Assuming balanced split)
*   **Recurrence:** $T(n) = 1T(n/2) + \Theta(n)$ (since we only recurse into one half)
*   **Parameters:** $a = 1$, $b = 2$, $f(n) = \Theta(n)$
*   **Master Theorem Case:** $n^{\log_b a} = n^{\log_2 1} = n^0 = 1$. Since $f(n) = \Omega(n^{\log_b a + \epsilon})$ for $\epsilon = 1$, and the regularity condition holds ($a \cdot f(n/b) \le c \cdot f(n) \Rightarrow 1 \cdot (n/2) \le c \cdot n$ for $c=1/2 < 1$), this is **Case 3**.
*   **Result:** $T(n) = \Theta(n)$

## 3. Plots and Ratio Check


*   `![Time vs n](time_plot.png)`
*   `![Max recursion depth vs n]()`
*   `![Ratio vs n](ratio_plot.png)`

**Ratio Check ($\Theta$ definition):**
According to the plots, the ratio of operations to expected growth becomes almost constant as $n$ grows. For large $n \ge n_0$ (where $n_0 \approx 10000$), the curve stabilizes between a lower bound $c_1 \approx 0.3$ and an upper bound $c_2 \approx 6.5$ (with sorting algorithms tightly bounded below $2.0$). This confirms the formal definition of Big-Theta: $c_1 \cdot g(n) \le f(n) \le c_2 \cdot g(n)$ for all $n \ge n_0$.

## 4. Discussion
Overall, the empirical measurements closely match the theoretical asymptotic bounds. QuickSort demonstrated $\mathcal{O}(n \log n)$ behavior across all inputs, avoiding $\mathcal{O}(n^2)$ on arrays with duplicates thanks to the 3-way partitioning scheme. Minor deviations in execution time for smaller $n$ can be attributed to JVM warm-up and JIT compilation, though taking the median of 5 runs heavily mitigated this. Memory churn and Garbage Collector pauses were minimized in MergeSort by using a single, reusable auxiliary array instead of allocating memory at each recursive step. Additionally, the CPU cache utilization was highly efficient, and the 15-element cutoff for Insertion Sort noticeably reduced the recursion overhead for base cases, exploiting Insertion Sort's fast execution on small datasets.