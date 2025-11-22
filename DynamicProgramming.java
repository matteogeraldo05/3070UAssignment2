public class DynamicProgramming {

    // Calculates edit distance (Levenshtein distance) between two strings
    public static int distance(String s1, String s2){
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        // Initialize distances for empty strings
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        // Fill dp table
        for (int i = 1; i <= m; i++){
            for (int j = 1; j <= n; j++){
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(
                        dp[i-1][j] + 1,           // deletion
                        Math.min(dp[i][j-1] + 1,  // insertion
                                dp[i-1][j-1] + cost) // substitution
                );
            }
        }

        return dp[m][n]; // Result is in bottom-right cell
    }

    public static void main(String[] args) {
        String[][] tests = {
                {"kitten", "sitting"},
                {"sunday", "saturday"},
                {"book", "back"},
                {"intention", "execution"},
                {"car", "race"}
        };

        int[] expected = {3, 3, 2, 5, 3};

        // Test and print results
        for (int i = 0; i < tests.length; i++) {
            String s1 = tests[i][0];
            String s2 = tests[i][1];
            int dist = distance(s1, s2);
            System.out.println("Distance between \"" + s1 + "\" and \"" + s2 + "\": " + dist
                    + " (expected: " + expected[i] + ")");
        }
    }
}
