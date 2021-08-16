import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** 프로그래머스 ID: 72414, 광고 삽입 */
public class Solution {
    // 왜 log의 시작점 혹은 끝점이 답이 아닐까 ??
    private static final int HOUR_TO_SECOND = 60 * 60;
    private static final int MINUTE_TO_SECOND = 60;
    private long[] views;

    public int getMaxViewStartTime(int play_time, int adv_time, List<Range> logs) {
        if (play_time == adv_time) {
            return 0;
        }
        views = new long[play_time + 1];
        countViewsAcc(logs);
        int maxStartTime = 0;
        long maxViewSum = 0;
        for (int i = 0; i <= play_time - adv_time; i++) {
            long viewSum = views[i + adv_time - 1] - (i == 0 ? 0 : views[i - 1]);
            if (viewSum > maxViewSum) {
                maxViewSum = viewSum;
                maxStartTime = i;
            }
        }
        return maxStartTime;
    }

    private void countViewsAcc(List<Range> logs) {
        for (Range log : logs) {
            int startTime = log.getStartTime();
            int endTime = log.getEndTime();
            for (int i = startTime; i < endTime; i++) {
                views[i] += 1;
            }
        }
        for (int i = 1; i < views.length; i++) {
            views[i] += views[i - 1];
        }
    }

    private String toTimeString(long time) {
        long hour = time / HOUR_TO_SECOND;
        time -= hour * HOUR_TO_SECOND;
        long minute = time / MINUTE_TO_SECOND;
        time -= minute * MINUTE_TO_SECOND;
        long seconds = time;
        return String.format("%02d:%02d:%02d", hour, minute, seconds);
    }

    private int toSeconds(String s) {
        String[] tokens = s.split(":");
        return HOUR_TO_SECOND * Integer.parseInt(tokens[0])
                + MINUTE_TO_SECOND * Integer.parseInt(tokens[1])
                + Integer.parseInt(tokens[2]);
    }

    private List<Range> parseLogs(String[] logs) {
        return Arrays.stream(logs)
                .map(Range::new)
                .sorted()
                .collect(Collectors.toList());
    }

    class Range implements Comparable<Range> {
        private final int startTime;
        private final int endTime;

        public Range(String s) {
            String[] tokens = s.split("-");
            this.startTime = toSeconds(tokens[0]);
            this.endTime = toSeconds(tokens[1]);
        }

        public int getStartTime() {
            return startTime;
        }

        public int getEndTime() {
            return endTime;
        }
        @Override
        public int compareTo(Range o) {
            return Integer.compare(this.getStartTime(), o.getStartTime());
        }

    }

    public String solution(String play_time, String adv_time, String[] logs) {
        return toTimeString(getMaxViewStartTime(toSeconds(play_time), toSeconds(adv_time), parseLogs(logs)));
    }

    public static void main(String[] args) {
        String answer = new Solution()
                .solution("99:59:59",
                        "25:00:00",
                        new String[] {"69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00"});
        System.out.println(answer);
    }
}
