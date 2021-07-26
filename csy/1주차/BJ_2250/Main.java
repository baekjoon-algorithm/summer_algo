import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** 백준 ID: 2250, 트리의 높이와 너비 */
public class Main {
    private int n;
    private int root;
    private Map<Integer, List<Integer>> tree;
    private List<Integer> levels;

    Main(int n, String[] nodes) {
        this.n = n;
        createTree(nodes);
    }

    private int searchRoot() {
        return tree.keySet()
                .stream()
                .filter(key -> getSize(key) == this.n)
                .mapToInt(Integer::intValue)
                .findAny()
                .getAsInt();
    }

    private int getSize(int node) {
        if (node == -1) {
            return 0;
        }
        int left = tree.get(node).get(0);
        int right = tree.get(node).get(1);
        return getSize(left) + getSize(right) + 1;
    }

    private void createTree(String[] nodes) {
        tree = new HashMap<>();
        for (String node : nodes) {
            String[] tokens = node.split(" ");
            int key = Integer.parseInt(tokens[0]);
            int left = Integer.parseInt(tokens[1]);
            int right = Integer.parseInt(tokens[2]);
            tree.put(key, List.of(left, right));
        }
        root = searchRoot();
    }

    public int[] findMaxWidth() {
        levels = new ArrayList<>();
        inOrder(this.root, 1);

        int height = levels.stream().mapToInt(Integer::intValue).max().orElse(0);
        int[] maxWidth = new int[]{1, 1}; // level, width
        for (int i = 1; i <= height; i++) {
            int first = levels.indexOf(i);
            int last = levels.lastIndexOf(i);
            if (first == -1) {
                continue;
            }
            int width = last - first + 1;
            if (width > maxWidth[1]) {
                maxWidth[0] = i;
                maxWidth[1] = width;
            }
        }
        return maxWidth;
    }

    private void inOrder(int node, int y) {
        if (node == -1) {
            return;
        }
        int left = tree.get(node).get(0);
        int right = tree.get(node).get(1);
        inOrder(left, y + 1);
        levels.add(y);
        inOrder(right, y + 1);
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] nodes = new String[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = scanner.nextLine();
        }

        int[] maxWidth = new Main(n, nodes).findMaxWidth();
        System.out.println(maxWidth[0] + " " + maxWidth[1]);
    }
}
