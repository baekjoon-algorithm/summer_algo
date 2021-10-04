import java.util.Comparator;
import java.util.stream.IntStream;

/** Leet Code ID: container-with-most-water, 11. Container With Most Water */
public class Solution {
    public int maxArea(int[] height) {
        int[] indices = IntStream.range(0, height.length)
                .boxed()
                .sorted(Comparator.comparingInt(a -> height[(int) a]).reversed())
                .mapToInt(Integer::valueOf)
                .toArray(); // reverse sorted

        int maxArea = 0;
        int min = -1;
        int max = -1;
        for (int i : indices) {
            int width = 0;
            if (min != -1) {
                width = Math.max(Math.abs(i - min), width);
            }
            if (max != -1) {
                width = Math.max(Math.abs(max - i), width);
            }
            maxArea = Math.max(width * height[i], maxArea);

            if (min == -1 || i < min) {
                min = i;
            }
            if (max == 1 || i > max) {
                max = i;
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int area = new Solution().maxArea(new int[]{2,1});
        System.out.println(area);
    }
}
