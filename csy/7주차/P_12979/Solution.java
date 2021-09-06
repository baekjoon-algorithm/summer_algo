/** 프로그래머스 ID: 12979, 기지국 설치 */
public class Solution {
    private int fill(int n, int[] stations, int w) {
        int idx = 1;
        int sIdx = 0;
        for (int i = 0; i <= n; i++) {
            // find next empty
            while (sIdx < stations.length && idx >= stations[sIdx] - w) {
                idx = stations[sIdx++] + w + 1;
            }
            if (idx > n) {
                return i;
            }
            idx += w * 2 + 1;
        }
        return 0;
    }

    public int solution(int n, int[] stations, int w) {
        return fill(n, stations, w);
    }

    public static void main(String[] args) {
        int answer = new Solution().solution(16, new int[]{9}, 2);
        System.out.println(answer);
    }
}
