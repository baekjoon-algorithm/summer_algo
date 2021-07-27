import java.util.Scanner;

/** 백준 ID: 3687, 성냥개비 */
public class Main {
    static class Solution {
        final int[] counts = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6, 6};
        String[][] cache = new String[100 + 1][2];

        public String[] getMinMax(int total, boolean skipZero) {
            if (cache[total][0] != null && cache[total][1] != null) {
                return cache[total];
            }
            if (total == 0) {
                return new String[]{"", ""};
            }
            String min = null;
            String max = null;
            for (int i = 0; i < counts.length; i++) {
                if (skipZero && i == 0) {
                    continue;
                }
                String number = String.valueOf(i);
                int count = counts[i];
                int next = total - count;
                if (next >= 0) {
                    String[] minMax = getMinMax(next, false);
                    if (minMax != null) {
                        String subMin = minMax[0];
                        if (subMin == null) {
                            continue;
                        }
                        // order by asc
                        if (skipZero || subMin.length() == 0 || number.charAt(0) <= subMin.charAt(0)) {
                            subMin = number + subMin;
                            if (min == null || StringComparator.isSmaller(subMin, min)) {
                                min = subMin;
                            }
                        }
                        // order by desc
                        String subMax = minMax[1];
                        if (subMax == null) {
                            continue;
                        }
                        if (subMax.length() == 0 || number.charAt(0) >= subMax.charAt(0)) {
                            subMax = number + subMax;
                            if (max == null || StringComparator.isLarger(subMax, max)) {
                                max = subMax;
                            }
                        }
                    }
                }
            }

            if (min == null && max == null) {
                return null;
            }

            if (!skipZero) {
                cache[total] = new String[]{min, max};
            }

            return new String[]{min, max};
        }

        static class StringComparator {
            public static int compare(String x, String y) {
                int idx = 0;
                while (idx < x.length()) {
                    int compared = Character.compare(x.charAt(idx), y.charAt(idx));
                    if (compared != 0) {
                        return compared;
                    }
                    idx += 1;
                }
                return 0;
            }

            public static boolean isLarger(String x, String y) {
                return (x.length() > y.length()) || (x.length() == y.length() && compare(x, y) >= 0);
            }

            public static boolean isSmaller(String x, String y) {
                return (x.length() < y.length()) || (x.length() == y.length() && compare(x, y) <= 0);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int[] counts = new int[n];
        for (int i = 0; i < n; i++) {
            counts[i] = Integer.parseInt(scanner.nextLine());
        }
        for (int c : counts) {
            String[] minMax = new Solution().getMinMax(c, true);
            System.out.println(minMax[0] + " " + minMax[1]);
        }
    }
}
