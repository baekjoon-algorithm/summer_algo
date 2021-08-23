import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 프로그래머스 ID 72411, 메뉴 리뉴얼 */
class Solution {
    public List<String> getCombination(String order) {
        return combinationHelper(Stream.of(order.split(""))
                        .sorted().collect(Collectors.toList()), new ArrayList<>());
    }

    public List<String> combinationHelper(List<String> menus, List<String> combinations) {
        if (menus.size() == 0) {
            return combinations;
        }
        List<String> nCombinations = new ArrayList<>();
        String menu = menus.remove(0);
        combinations.forEach(comb -> {
            String newComb = comb + menu;
            nCombinations.add(newComb);
        });
        combinations.addAll(nCombinations);
        combinations.add(menu);
        return combinationHelper(menus, combinations);
    }

    public String[] solution(String[] orders, int[] course) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String order: orders) {
            List<String> combinations = getCombination(order);
            combinations.forEach(comb -> {
                int courseMenuSize = comb.length();
                boolean matched = false;
                for (int c: course) {
                    if (c == courseMenuSize) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    countMap.put(comb, countMap.getOrDefault(comb, 0) + 1);
                }
            });
        }

        Map<Integer, Integer> maxMap = new HashMap<>();
        countMap.forEach((key1, value) -> {
            int key = key1.length();
            maxMap.put(key, Math.max(maxMap.getOrDefault(key, 2), value));
        });

        List<String> resultList = new ArrayList<>();
        countMap.forEach((key1, value) -> {
            int key = key1.length();
            if (maxMap.get(key).intValue() == value.intValue()) {
                resultList.add(key1);
            }
        });
        Collections.sort(resultList);
        return resultList.toArray(String[]::new);
    }

    public static void main(String[] args) {
        String[] answer = new Solution().solution(new String[]{"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"},
                new int[]{2, 3, 4});
        System.out.println(String.join("\n", answer));
    }
}
