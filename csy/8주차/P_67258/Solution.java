import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** 프로그래머스 ID: 67258, [카카오 인턴] 보석 쇼핑 */
public class Solution {
    private void increase(Map<String, Integer> countMap, String gem) {
        countMap.put(gem, countMap.getOrDefault(gem, 0) + 1);
    }

    private void decrease(Map<String, Integer> countMap, String gem) {
        countMap.put(gem, countMap.get(gem) - 1);
        if (countMap.get(gem) == 0) {
            countMap.remove(gem);
        }
    }

    private long getDistinctCount(String[] gems) {
        return Arrays.stream(gems).distinct().count();
    }

    private int[] buyWithTwoPointer(String[] gems, long total) {
        int[] buy = null;
        int end = 0;
        Map<String, Integer> countMap = new HashMap<>();
        for (int start = 0; start < gems.length; start++) {
            while ((countMap.size() < total || start > end) && end < gems.length) {
                increase(countMap, gems[end]);
                end++;
            }
            if (countMap.size() == total && (buy == null || end - start < buy[1] - buy[0])) {
                buy = new int[]{start, end};
            }
            decrease(countMap, gems[start]);
        }
        return new int[]{buy[0] + 1, buy[1]};
    }

    public int[] solution(String[] gems) {
        return buyWithTwoPointer(gems, getDistinctCount(gems));
    }

    public static void main(String[] args) {
        int[] answer = new Solution()
                .solution(new String[]{"A", "B" ,"B", "C", "A", "B", "C", "A","B","C"});
        System.out.println(Arrays.toString(answer));
    }
}
