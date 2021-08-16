import java.util.Scanner;

/** 백준 ID: 1275, 커피숍2 */
public class Main {
    int n;
    int q;
    int[][] updates;
    int[] array;
    SumTree sumTree;

    public Main(int n, int q, int[] array, int[][] updates) {
        this.n = n;
        this.q = q;
        this.updates = updates;
        this.array = array;
        this.sumTree = new SumTree(array, n);
    }

    class SumTree {
        private int n;
        private long[] rangeSum;

        public SumTree(int[] array, int n) {
            this.n = n;
            this.rangeSum = new long[n * 4];
            init(array, 0, n - 1, 1);
        }

        long init(int[] array, int left, int right, int node) {
            if (left == right) {
                rangeSum[node] = array[left];
                return rangeSum[node];
            }
            int mid = (left + right) / 2;
            long leftSum = init(array, left, mid, node * 2);
            long rightSum = init(array, mid + 1, right, node * 2 + 1);
            rangeSum[node] = leftSum + rightSum;
            return rangeSum[node];
        }

        long query(int left, int right) {
            return query(left, right, 1, 0, n - 1);
        }

        long query(int left, int right, int node, int nodeLeft, int nodeRight) {
            if (right < nodeLeft || nodeRight < left) {
                return 0;
            }
            if (left <= nodeLeft && nodeRight <= right) {
                return rangeSum[node];
            }
            int mid = (nodeLeft + nodeRight) / 2;
            return query(left, right, node * 2, nodeLeft, mid) +
                    query(left, right, node * 2 + 1, mid + 1, nodeRight);
        }

        long update(int index, int newValue, int node, int nodeLeft, int nodeRight) {
            if (index < nodeLeft || nodeRight < index) {
                return rangeSum[node];
            }
            if (nodeLeft == nodeRight) {
                rangeSum[node] = newValue;
                return rangeSum[node];
            }
            int mid = (nodeLeft + nodeRight) / 2;
            rangeSum[node] = update(index, newValue, node * 2, nodeLeft, mid) +
                    update(index, newValue, node * 2 + 1, mid + 1, nodeRight);
            return rangeSum[node];
        }

        long update(int index, int newValue) {
            return update(index, newValue, 1, 0, n - 1);
        }
    }

    public String[] getSumResult() {
        String[] sumResult = new String[q];
        for (int i = 0; i < q; i++) {
            int[] update = updates[i];
            int x = Math.min(update[0], update[1]) - 1;
            int y = Math.max(update[0], update[1]) - 1;
            int a = update[2] - 1;
            int b = update[3];
            sumResult[i] = String.valueOf(sumTree.query(x, y));
            sumTree.update(a, b);
        }
        return sumResult;
    }

    public static Main processInput() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int q = scanner.nextInt();
        scanner.nextLine();

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        scanner.nextLine();

        int[][] updates = new int[q][4];
        for (int i = 0; i < q; i++) {
            for (int j = 0; j < 4; j++) {
                updates[i][j] = scanner.nextInt();
            }
        }
        return new Main(n, q, array, updates);
    }

    public static void main(String[] args) {
        Main main = processInput();
        String[] sumResult = main.getSumResult();
        for (String result : sumResult) {
            System.out.println(result);
        }
    }
}
