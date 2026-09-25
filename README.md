# Algorithms Lab

Java implementations from a Design & Analysis of Algorithms course at Binghamton University, comparing different algorithmic strategies (brute force, divide & conquer, dynamic programming, and greedy) for a few classic problems.

## Contents

**`FloydWarshall.java`** — All-pairs shortest paths in O(V^3), with full path reconstruction (not just distances) via a predecessor matrix. Run against `sample-graph.txt`:

```bash
javac FloydWarshall.java
java FloydWarshall sample-graph.txt
```

**`LongestCommonSubsequence.java`** — Classic O(m*n) dynamic-programming solution to LCS, reconstructing an actual longest common subsequence from the DP table.

```bash
javac LongestCommonSubsequence.java
java LongestCommonSubsequence ABCDEFGH ACBCEFHX
```

**`StrassenMatrixMultiplication.java`** — Strassen's divide-and-conquer matrix multiplication (~O(n^2.81) vs. standard O(n^3)), recursively splitting each matrix into quadrants and combining 7 sub-multiplications instead of 8. Verifies its result against standard multiplication on randomly generated matrices.

```bash
javac StrassenMatrixMultiplication.java
java StrassenMatrixMultiplication 8   # n must be a power of 2
```

**`knapsack/`** — The 0/1 knapsack problem solved three different ways, so the trade-offs between them are directly comparable on the same generated instance:

| Solver | Approach | Complexity | Guarantee |
|---|---|---|---|
| `BruteForceKnapsackSolver` | Try every include/exclude combination | O(2^n) | Exact |
| `DynamicProgrammingKnapsackSolver` | Bottom-up DP table | O(n × capacity) | Exact |
| `GreedyKnapsackSolver` | Sort by profit/weight ratio | O(n log n) | Heuristic only |

```bash
cd knapsack
javac *.java
java KnapsackProblemGenerator          # writes a random instance to knapsack01.txt
java BruteForceKnapsackSolver          # -> Output01.txt
java DynamicProgrammingKnapsackSolver  # -> Output02.txt
java GreedyKnapsackSolver              # -> Output03.txt
```

Comparing `Output01.txt`/`Output02.txt` (both exact) against `Output03.txt` (greedy) on the same instance shows how often — and by how much — the greedy heuristic falls short of the optimal solution.
