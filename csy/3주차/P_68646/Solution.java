/** 프로그래머스 ID: 68646, 풍선 터트리기 */
class Solution {
    class RMQ {
        private int n;
        private int[] rangeMin;

        public RMQ(int[] array, int n) {
            this.n = n;
            this.rangeMin = new int[n * 4];
            init(array, 0, n - 1, 1);
        }

        int init(int[] array, int left, int right, int node) {
            if (left == right) {
                rangeMin[node] = array[left];
                return rangeMin[node];
            }

            int mid = (left + right) / 2;
            int leftMin = init(array, left, mid, node * 2);
            int rightMin = init(array, mid + 1, right, node * 2 + 1);
            return rangeMin[node] = Math.min(leftMin, rightMin);
        }

        int query(int left, int right) {
            return query(left, right, 1, 0, n - 1);
        }

        int query(int left, int right, int node, int nodeLeft, int nodeRight) {
            if (right < nodeLeft || nodeRight < left) {
                return Integer.MAX_VALUE;
            }

            if (left <= nodeLeft && nodeRight <= right) {
                return rangeMin[node];
            }

            int mid = (nodeLeft + nodeRight) / 2;
            return Math.min(query(left, right, node * 2, nodeLeft, mid),
                    query(left, right, node * 2 + 1, mid + 1, nodeRight));
        }

    }

    boolean checkIfLastOne(RMQ rmq, int[] a, int idx) {
        if (idx == a.length - 1 || idx == 0) {
            return true;
        }
        int left = rmq.query(0, idx - 1);
        int target = a[idx];
        int right = rmq.query(idx + 1, a.length - 1);
        if (left > target && right > target) {
            return true;
        }
        if ((left < target && target < right) || (left > target && target > right)) {
            return true;
        }
        return false;
    }

    public int solution(int[] a) {
        RMQ rmq = new RMQ(a, a.length);
        int answer = 0;
        for (int i = 0; i < a.length; i++) {
            if (checkIfLastOne(rmq, a, i)) {
                answer++;
            }
        }
        return answer;
    }
}
