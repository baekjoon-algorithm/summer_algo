import java.util.stream.IntStream;

/** 프로그래머스 ID: P_43238, 입국심사 */
class Solution {
    class Search {
        long n;
        int[] times;

        Search(long n, int[] times) {
            this.n = n;
            this.times = times;
        }

        public long binarySearch() {
            long answer = 0;
            long min = IntStream.of(times).min().orElse(0);
            long max = IntStream.of(times).max().orElse(0) * n;

            while (min <= max) {
                long mid = (min + max) / 2;
                long sum = 0;
                for (long time: times) {
                    sum += mid / time;
                }
                if (sum >= n) {
                    max = mid - 1;
                    answer = mid;
                }
                if (sum < n) {
                    min = mid + 1;
                }
            }
            return answer;
        }
    }

    public long solution(int n, int[] times) {
        return new Search(n, times).binarySearch();
    }
}
