import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** 프로그래머스 ID: 42895, N으로 표현 */
class Solution {
    private final int MAX_CNT = 8;
    private final int MAX_NUMBER = 32_000;
    List<List<Integer>> numbers = new ArrayList<>();

    public int getMinCount(int N, int number) {
        for (int i = 1; i <= MAX_CNT; i++) {
            numbers.add(calculateNumbers(N, i));
            if (numbers.get(i - 1).contains(number)) {
                return i;
            }
        }
        return -1;
    }

    public List<Integer> calculateNumbers(int N, int cnt) {
        if (cnt < 0) {
            return null;
        }
        if (cnt == 0) {
            List<Integer> numbers = new ArrayList<>();
            numbers.add(0);
            return numbers;
        }
        if (cnt == 1) {
            List<Integer> numbers = new ArrayList<>();
            numbers.add(N);
            return numbers;
        }
        if (cnt <= numbers.size()) {
            return numbers.get(cnt - 1);
        }
        List<Integer> answer = new ArrayList<>();
        int n = 0;
        for (int i = 1; i < cnt; i++) {
            n = 10 * n  + N;
            List<Integer> numbersA = calculateNumbers(N, i);
            List<Integer> numbersB = calculateNumbers(N, cnt - i);
            for (int numberA : numbersA) {
                for (int numberB : numbersB) {
                    answer.add(numberA + numberB);
                    answer.add(numberA - numberB);
                    answer.add(numberA * numberB);
                    answer.add(numberA / numberB);
                }
            }
        }
        answer.add(10 * n + N);
        return answer.stream()
                .filter(number -> number > 0 && number <= MAX_NUMBER)
                .distinct().collect(Collectors.toList());
    }

    public int solution(int N, int number) {
        return getMinCount(N, number);
    }

    public static void main(String[] args) {
        int result = new Solution().solution(8, 53);
        System.out.println(result);
    }
}
