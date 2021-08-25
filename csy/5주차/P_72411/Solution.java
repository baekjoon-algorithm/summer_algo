import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/** 프로그래머스 ID 72411, 메뉴 리뉴얼 */
public class Solution {
    public List<String> getCombination(String[] items, int maxCnt,
                                       BiFunction<String, String, String> mergeFunction) {
        return getCombination(items, maxCnt, mergeFunction, 0, 0);
    }

    public List<String> getCombination(String[] items, int maxCnt,
                                       BiFunction<String, String, String> mergeFunction, int idx, int cnt) {
        // base case
        if (idx >= items.length) {
            return null;
        }
        if (cnt == maxCnt - 1) {
            return new ArrayList<>(Arrays.asList(items).subList(idx, items.length));
        }
        List<String> result = new ArrayList<>();
        for (int i = idx; i < items.length; i++) {
            String order = items[i];
            List<String> combinations = getCombination(items, maxCnt, mergeFunction, i + 1, cnt + 1);
            if (combinations == null) {
                continue;
            }
            for (String combination : combinations) {
                String combined = mergeFunction.apply(combination, order);
                if (!combined.isEmpty() && !result.contains(combined)) {
                    result.add(combined);
                }
            }
        }
        return result;
    }

    String intersectMergeFunction(String a, String b) {
        return Arrays.stream(a.split(""))
                .filter(b::contains)
                .sorted()
                .collect(Collectors.joining());
    }

    String appendMergeFunction(String a, String b) {
        return a.charAt(a.length() - 1) < b.charAt(b.length() - 1) ? a + b : b + a;
    }

    public String[] solution(String[] orders, int[] course) {
        Map<Integer, List<String>> maxCourseMap = new HashMap<>();
        for (int i = 2; i < orders.length; i++) {
            List<String> combinations = getCombination(orders, i, this::intersectMergeFunction);
            Map<Integer, List<String>> courseMap = new HashMap<>();
            for (String combination : combinations) {
                for (int j = 0; j < course.length; j++) {
                    if (course[j] <= combination.length()) {
                        courseMap.putIfAbsent(j, new ArrayList<>());
                        courseMap.get(j).addAll(getCombination(combination.split(""),
                                course[j], this::appendMergeFunction));
                    }
                }
            }
            maxCourseMap.putAll(courseMap);
        }
        return maxCourseMap.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted()
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        String[] answer = new Solution().solution(new String[]{"ABCD", "ABCD", "ABCD"},
                new int[]{2, 3, 4});
        System.out.println(String.join("\n", answer));
    }
}
