import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 프로그래머스 ID: 17676, 추석 트래픽 */
public class Solution {
    class Traffic {
        int interval; // milliseconds
        LocalDateTime startTime;
        LocalDateTime endTime;

        public Traffic(String information) {
            String[] tokens = information.split(" ");
            endTime = LocalDateTime.parse(tokens[0] + " " + tokens[1],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            interval = (int) (Double.parseDouble(tokens[2].replace("s", "")) * 1000);
            startTime = endTime.minus(interval - 1, ChronoUnit.MILLIS);
        }

        public LocalDateTime getStartTime() {
            return LocalDateTime.from(startTime);
        }

        public LocalDateTime getEndTime() {
            return LocalDateTime.from(endTime);
        }

        boolean isBetweenStartAndInterval(LocalDateTime startDt, int interval) {
            return isBetween(startDt, LocalDateTime.from(startDt)
                    .plus(interval - 1, ChronoUnit.MILLIS));
        }

        boolean isBetween(LocalDateTime from, LocalDateTime to) {
            return !(endTime.isBefore(from) || startTime.isAfter(to));
        }
    }

    final int INTERVAL = 1000;
    public int getMaxTrafficCnt(List<Traffic> traffics) {
        int maxCnt = 0;
        for (Traffic i : traffics) {
            LocalDateTime startTime = i.getStartTime();
            LocalDateTime endTime = i.getEndTime();
            int startCnt = 0;
            int endCnt = 0;
            for (Traffic j : traffics) {
                startCnt += j.isBetweenStartAndInterval(startTime, INTERVAL) ? 1 : 0;
                endCnt += j.isBetweenStartAndInterval(endTime, INTERVAL) ? 1 : 0;
            }
            maxCnt = Math.max(startCnt, maxCnt);
            maxCnt = Math.max(endCnt, maxCnt);
        }
        return maxCnt;
    }

    public int solution(String[] lines) {
        List<Traffic> traffics = Stream.of(lines)
                .map(Traffic::new)
                .collect(Collectors.toList());

        return getMaxTrafficCnt(traffics);
    }
}
