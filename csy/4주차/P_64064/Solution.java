import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** 프로그래머스 ID: 64064, 불량 사용자 */
public class Solution {
    int bannedSize;
    String[] bannedId;
    String[] userId;
    int[] counts;
    List<String> strings;

    private int findBanned(String[] user_id, String[] banned_id) {
        strings = new ArrayList<>();
        userId = user_id;
        List<String> bannedList = Arrays.stream(banned_id).distinct().collect(Collectors.toList());
        bannedSize = bannedList.size();
        bannedId = new String[bannedSize];
        counts = new int[bannedSize];
        for (String banned : banned_id) {
            int idx = bannedList.indexOf(banned);
            bannedId[idx] = banned.replaceAll("\\*", ".");
            counts[idx] += 1;
        }
        findBanned(0, new int[bannedSize], "");
        return strings.size();
    }

    private void findBanned(int user_idx, int[] matched, String str) {
        if (Arrays.stream(matched).sum() == Arrays.stream(counts).sum()) {
            if (!strings.contains(str)) {
                strings.add(str);
            }
            return;
        }
        if (user_idx >= userId.length) {
            return;
        }
        String value = userId[user_idx];
        for (int i = 0; i < bannedId.length; i++) {
            if (counts[i] == matched[i]) {
                continue;
            }
            String pattern = bannedId[i];
            if (Pattern.matches(pattern, value)) {
                matched[i] += 1;
                findBanned(user_idx + 1, matched, str + " " + value);
                matched[i] -= 1;
            }
        }
        findBanned(user_idx + 1, matched, str);
    }

    public int solution(String[] user_id, String[] banned_id) {
        return findBanned(user_id, banned_id);
    }

    public static void main(String[] args) {
        int result = new Solution()
                .solution(new String[]{"frodo", "fradi", "crodo", "abc123", "frodoc"},
                new String[]{"*rodo", "*rodo", "******"});
        System.out.println(result);
    }
}
