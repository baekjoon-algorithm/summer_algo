import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** 프로그래머스 ID: 81303, 표 편집 */
public class Solution {
    Stack<Node> history;
    int[] deleted;

    class Node {
        Node prev;
        Node next;
        int index;

        public Node(int index) {
            this.index = index;
        }
    }

    Node up(Node node, int x) {
        for (int i = 0; i < x; i++) {
            node = node.prev;
        }
        return node;
    }

    Node down(Node node, int x) {
        for (int i = 0; i < x; i++) {
            node = node.next;
        }
        return node;
    }

    Node delete(Node node) {
        history.push(node);
        deleted[node.index] = 1;

        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        return node.next != null ? node.next : node.prev;
    }

    void recover() {
        Node node = history.pop();
        deleted[node.index] = 0;
        if (node.next != null)
            node.next.prev = node;
        if (node.prev != null)
            node.prev.next = node;
    }

    Node init(int n) {
        this.history = new Stack<>();
        this.deleted = new int[n];
        Node root = new Node(0);
        Node prev = root;
        for (int i = 1; i < n; i++) {
            Node node = new Node(i);
            prev.next = node;
            node.prev = prev;
            prev = node;
        }
        return root;
    }

    public String solution(int n, int k, String[] cmd) {
        Node root = init(n);
        Node node = down(root, k);
        for (String c : cmd) {
            String[] tokens = c.split(" ");
            String type = tokens[0];
            if ("U".equals(type)) {
                node = up(node, Integer.parseInt(tokens[1]));
            }
            if ("D".equals(type)) {
                node = down(node, Integer.parseInt(tokens[1]));
            }
            if ("C".equals(type)) {
                node = delete(node);
            }
            if ("Z".equals(type)) {
                recover();
            }
        }
        return IntStream.of(deleted)
                .mapToObj(i -> i == 0 ? "O" : "X")
                .collect(Collectors.joining());
    }

    public static void main(String[] args) {
        String answer = new Solution().solution(8, 2, new String[]{"D 2","C","U 3","C","D 4","C","U 2","Z","Z"});
        System.out.println(answer);
    }
}
