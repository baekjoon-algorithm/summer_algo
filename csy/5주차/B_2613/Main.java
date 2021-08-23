import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** 백준 ID 2613, 구간 합 구하기 성공 */
public class Main {
    int n;
    int m;
    int[] numbers;
    int[] preSum;
    int[][][] cache;

    public Main(int n, int m, int[] numbers) {
        this.n = n;
        this.m = m;
        this.numbers = numbers;
        this.cache = new int[n][m][m + 1];
        init();
    }

    private void init() {
        preSum = new int[n];
        preSum[0] = numbers[0];
        for (int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + numbers[i];
        }
    }

    private int sum(int from, int to) {
        if (from == 0){
            return preSum[to];
        }
        return preSum[to] - preSum[from - 1];
    }

    public String[] getMinGroup() {
        int[] group = getMinGroup(0, 0);
        return new String[]{
                String.valueOf(group[0]),
                IntStream.range(1, m + 1).mapToObj(i -> String.valueOf(group[i])).collect(Collectors.joining(" "))
        };
    }

    public int[] getMinGroup(int idx, int count) {
        int[] group = new int[m + 1]; // value + sizes
        if (count == m - 1) {
            group[0] = sum(idx, n - 1);
            group[count + 1] = (n - idx);
            return group;
        }
        if (cache[idx][count][0] != 0) {
            return cache[idx][count];
        }
        group[0] = Integer.MAX_VALUE;
        for (int i = idx; i < n - 1; i++) {
            int[] nextGroup = Arrays.copyOf(getMinGroup(i + 1, count + 1), m + 1);
            nextGroup[0] = Math.max(nextGroup[0], sum(idx, i));
            nextGroup[count + 1] = (i - idx + 1);
            if (nextGroup[0] <= group[0]) {
                group = Arrays.copyOf(nextGroup, m + 1);
            }
        }
        return cache[idx][count] = group;
    }

    public static Main processInput() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++){
            numbers[i] = scanner.nextInt();
        }
        scanner.close();
        return new Main(n, m, numbers);
    }

    public static void main(String[] args) {
        Main main = processInput();
        System.out.println(String.join("\n", main.getMinGroup()));
    }
}
