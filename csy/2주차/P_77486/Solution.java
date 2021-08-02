import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** 프로그래머스 ID: 77486, 다단계 칫솔 판매 */
public class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        Map<String, Integer> nameToIdx = IntStream.range(0, enroll.length)
                .boxed()
                .collect(Collectors.toMap(i -> enroll[i], Function.identity()));
        int[] answer = new int[enroll.length];

        for (int i = 0; i < seller.length; i++) {
            String currentSeller = seller[i];
            int currentAmount = amount[i] * 100;

            while (!"-".equals(currentSeller)) {
                int idx = nameToIdx.get(currentSeller);
                int fee = (int) (currentAmount * 0.1);
                answer[idx] += (currentAmount - fee);
                if (fee == 0) {
                    break;
                }
                currentSeller = referral[idx];
                currentAmount = fee;
            }
        }
        return answer;
    }
}
