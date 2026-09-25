/**
 * Classic dynamic-programming solution to the Longest Common Subsequence
 * (LCS) problem: builds the O(m*n) DP table bottom-up, then walks it
 * backward to reconstruct one actual longest common subsequence (not just
 * its length).
 *
 * Usage:
 *   javac LongestCommonSubsequence.java
 *   java LongestCommonSubsequence <string1> <string2>
 */
public class LongestCommonSubsequence {

    public static String findLCS(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return reconstruct(s1, s2, dp);
    }

    private static String reconstruct(String s1, String s2, int[][] dp) {
        int lcsLength = dp[s1.length()][s2.length()];
        char[] lcs = new char[lcsLength];

        int i = s1.length(), j = s2.length(), index = lcsLength;
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs[--index] = s1.charAt(i - 1);
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return new String(lcs);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java LongestCommonSubsequence <string1> <string2>");
            System.exit(1);
        }

        String s1 = args[0];
        String s2 = args[1];
        String lcs = findLCS(s1, s2);

        System.out.println("Length of LCS: " + lcs.length());
        System.out.println("LCS: " + lcs);
    }
}
