import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

/** 백준 ID : 9370, 미확인 도착지 */
public class Main {
    static class Graph {
        int n;
        private int[] passed;
        Map<Integer, List<Edge>> graph;

        Graph(int n, String[] edges, int g, int h) {
            this.n = n;
            createGraph(n, edges, g, h);
        }

        private void createGraph(int n, String[] edges, int g, int h) {
            graph = new HashMap<>();

            for (int i = 1; i <= n; i++) {
                graph.put(i, new ArrayList<>());
            }

            for (String edge : edges) {
                String[] tokens = edge.split(" ");
                int from = Integer.parseInt(tokens[0]);
                int to = Integer.parseInt(tokens[1]);
                int cost = Integer.parseInt(tokens[2]);

                boolean target = (to == h && from == g) || (to == g && from == h);
                graph.get(from).add(new Edge(to, cost, target ? 0 : 1));
                graph.get(to).add(new Edge(from, cost, target ? 0 : 1));
            }
        }

        public List<Integer> getValidDests(int start, int[] dests) {
            dijkstra(start);
            List<Integer> filteredDest = new ArrayList<>();
            for (int dest : dests) {
                if (passed[dest] == 0) {
                    filteredDest.add(dest);
                }
            }
            filteredDest.sort(Comparator.naturalOrder());
            return filteredDest;
        }

        private void dijkstra(int start) {
            long[] costs = new long[n + 1];
            Arrays.fill(costs, -1);
            passed = new int[n + 1];
            Arrays.fill(passed, 1);

            PriorityQueue<Edge> queue = new PriorityQueue<>();
            int[] visited = new int[this.n + 1];
            queue.add(new Edge(start, 0)); // add dummy edge to start while

            while (!queue.isEmpty()) {
                Edge edge = queue.poll();
                int node = edge.to;
                if (visited[node] == 1) {
                    continue;
                }
                visited[node] = 1;
                costs[node] = edge.cost;
                passed[node] = edge.target;
                for (Edge b : graph.get(node)) {
                    int to = b.to;
                    int target = b.target;
                    long addCost = costs[node] + b.cost;
                    if (costs[to] == -1 || addCost <= costs[to]) {
                        queue.add(new Edge(to, addCost, target == 0 ? 0 : passed[node]));
                    }
                }
            }
        }

        class Edge implements Comparable<Edge> {
            private final int to;
            private final long cost;
            private final int target;

            public Edge(int to, long cost) {
                this.to = to;
                this.cost = cost;
                this.target = 1;
            }

            public Edge(int to, long cost, int target) {
                this.to = to;
                this.cost = cost;
                this.target = target;
            }

            @Override
            public int compareTo(Edge o) {
                int compared = Long.compare(this.cost, o.cost);
                if (compared == 0) {
                    return Long.compare(this.cost + this.target, o.cost + o.target);
                }
                return compared;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = Integer.parseInt(scanner.nextLine());
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < T; i++) {
            String[] tokens = scanner.nextLine().split(" ");
            int n = Integer.parseInt(tokens[0]);
            int m = Integer.parseInt(tokens[1]);
            int t = Integer.parseInt(tokens[2]);

            tokens = scanner.nextLine().split(" ");
            int s = Integer.parseInt(tokens[0]);
            int g = Integer.parseInt(tokens[1]);
            int h = Integer.parseInt(tokens[2]);

            String[] edges = new String[m];
            for (int j = 0; j < m; j++) {
                edges[j] = scanner.nextLine();
            }

            int[] dests = new int[t];
            for (int j = 0; j < t; j++) {
                dests[j] = Integer.parseInt(scanner.nextLine());
            }
            answer.append(new Graph(n, edges, g, h)
                    .getValidDests(s, dests)
                    .stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(" "))).append("\n");
        }

        System.out.println(answer);
    }
}
