import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 백준 ID 2613, 숫자구슬 */
public class Main {
    private static final long UPDATE_COMMAND = 1;
    private static final long SUM_COMMAND = 2;

    private long[][] commands;
    private RangeSumTree sumTree;

    Main(long[] numbers, long[][] commands) {
        this.commands = commands;
        this.sumTree = new RangeSumTree(numbers);
    }

    public String[] execute() {
        List<String> outputs = new ArrayList<>();
        for (int i = 0; i < commands.length; i++) {
            long a = commands[i][0];
            long b = commands[i][1];
            long c = commands[i][2];
            if (a == UPDATE_COMMAND) {
                sumTree.update(b, c);
            }
            if (a == SUM_COMMAND) {
                outputs.add(String.valueOf(sumTree.get(b, c)));
            }
        }
        return outputs.toArray(String[]::new);
    }

    private static Main processInput() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        scanner.nextLine();

        long[] numbers = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            numbers[i] = scanner.nextLong();
            scanner.nextLine();
        }

        long[][] commands = new long[m + k][3];
        for (int i = 0; i < m + k; i++) {
            commands[i][0] = scanner.nextLong();
            commands[i][1] = scanner.nextLong();
            commands[i][2] = scanner.nextLong();
            scanner.nextLine();
        }
        return new Main(numbers, commands);
    }

    class RangeSumTree {
        int n;
        long[] rangeSum;

        RangeSumTree(long[] numbers) {
            this.n = numbers.length;
            this.rangeSum = new long[numbers.length * 4];
            init(numbers, 1, 0, n - 1);
        }

        private long init(long[] numbers, int node, int from, int to) {
            if (from == to) {
                return rangeSum[node] = numbers[from];
            }
            int mid = (from + to) / 2;
            long left = init(numbers, 2 * node, from, mid);
            long right = init(numbers, 2 * node + 1, mid + 1, to);
            return rangeSum[node] = left + right;
        }

        public long update(long idx, long number) {
            return update(idx, number, 1, 0, n - 1);
        }

        public long update(long idx, long number, int node, int nodeFrom, int nodeTo) {
            if (nodeFrom > idx || nodeTo < idx) {
                return rangeSum[node];
            }
            if (nodeFrom == nodeTo) {
                return rangeSum[node] = number;
            }
            int mid = (nodeFrom + nodeTo) / 2;
            long left = update(idx, number, node * 2, nodeFrom, mid);
            long right = update(idx, number, node * 2 + 1, mid + 1, nodeTo);
            return rangeSum[node] = left + right;
        }

        public long get(long from, long to) {
            return get(from, to, 1, 0, n - 1);
        }

        public long get(long from, long to, int node, int nodeFrom, int nodeTo) {
            if (to < nodeFrom || from > nodeTo) {
                return 0;
            }
            if (nodeFrom >= from && nodeTo <= to) {
                return rangeSum[node];
            }
            int mid = (nodeFrom + nodeTo) / 2;
            return get(from, to, node * 2, nodeFrom, mid)
                    + get(from, to, node * 2 + 1, mid + 1, nodeTo);
        }
    }

    public static void main(String[] args) {
        Main main = processInput();
        String[] output = main.execute();
        System.out.println(String.join("\n", output));
    }
}
