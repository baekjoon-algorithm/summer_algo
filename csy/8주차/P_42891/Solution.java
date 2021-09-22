/** 프로그래머스 ID: 42891, 무지의 먹방 라이브 */
public class Solution {
    private int n;
    private int[] food_times;

    private void init(int[] food_times) {
        this.food_times = food_times;
        this.n = food_times.length;
    }

    private boolean isBetweenCurrentAndNext(int index, int current, int next) {
        return (current <= index && index < next)
                || (current > next && (index >= current || index < next));
    }

    private int increaseOne(int index) {
        return (index + 1) % n;
    }

    private int getNextCurrent(int current, int add) {
        int count = 0;
        int index = current;
        while (count < add) {
            index = increaseOne(index);
            if (food_times[index] == 0) {
                continue;
            }
            count++;
        }
        return index;
    }

    private int eat(int current, long count, int total) {
        if (total == 0) {
            return -1;
        }
        while (food_times[current] == 0) {
            current = increaseOne(current);
        }
        if (count == 0) {
            return current;
        }

        int divided = (int) (count / total);
        int mod = (int) (count % total);

        int nextCurrent = getNextCurrent(current, mod);
        long nextCount = 0;
        int nextTotal = 0;
        for (int i = 0; i < n; i++) {
            int food_time = food_times[i];
            if (food_time == 0) {
                continue;
            }
            int delta = isBetweenCurrentAndNext(i, current, nextCurrent) ? 1 : 0;
            food_time -= (divided + delta);
            if (food_time <= 0) {
                nextCount -= food_time;
                food_time = 0;
            } else {
                nextTotal++;
            }
            food_times[i] = food_time;
        }
        return eat(nextCurrent, nextCount, nextTotal);
    }

    public int solution(int[] food_times, long k) {
        init(food_times);
        int next = eat(0, k, food_times.length);
        return next == -1 ? next : next + 1;
    }

    public static void main(String[] args) {
        int answer = new Solution().solution(new int[]{3, 1, 2}, 5);
        System.out.println(answer);
    }
}
