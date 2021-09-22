import java.util.Arrays;

/** 프로그래머스 ID: 12914, 멀리 뛰기 */
public class Solution {
    private static final long MOD_VALUE = 1234567L;
    private long[] picked;

    private long jump(int target) {
        this.picked = new long[target];
        Arrays.fill(picked, -1L);
        return jump(0, target);
    }

    private long jump(int sum, int target) {
        if (sum == target) {
            return 1;
        }
        if (sum > target) {
            return 0;
        }
        if (picked[sum] != -1) {
            return picked[sum];
        }
        picked[sum] = (jump(sum + 1, target) + jump(sum + 2, target)) % 1234567;
        return picked[sum];
    }

    public long solution(int n) {
        return jump(n);
    }

    public static void main(String[] args) {
        long count = new Solution().solution(4);
        System.out.println(count);
    }
}
