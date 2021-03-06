import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

/** 백준 ID:11779, 최소비용 구하기 2 */
public class Main {
    int n;
    private int[] costs;
    private int[] prev;
    Map<Integer, List<Edge>> graph;

    Main(int n, String[] cities) {
        this.n = n;
        createGraph(n, cities);
    }

    private void createGraph(int n, String[] cities) {
        graph = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (String city : cities) {
            String[] tokens = city.split(" ");
            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int cost = Integer.parseInt(tokens[2]);
            graph.get(from).add(new Edge(to, cost));
        }
    }

    public String[] getShortestPath(int start, int end) {
        costs = new int[n + 1];
        Arrays.fill(costs,-1);
        prev = new int[n + 1];

        dijkstra(start);
        int node = end;
        List<Integer> paths = new ArrayList<>();
        while (node != start) {
            paths.add(0, node);
            node = prev[node];
        }
        paths.add(0, start);
        return new String[]{
                String.valueOf(costs[end]),
                String.valueOf(paths.size()),
                paths.stream().map(String::valueOf).collect(Collectors.joining(" "))
        };
    }

    private void dijkstra(int start) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        int[] visited = new int[this.n + 1];
        costs[start] = 0;
        queue.add(new Edge(start, 0));

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int node = edge.to;
            if (visited[node] == 1) {
                continue;
            }
            visited[node] = 1;
            costs[node] = edge.cost;
            prev[node] = edge.prev;
            for (Edge b : graph.get(node)) {
                int to = b.to;
                int addCost = costs[node] + b.cost;
                if (costs[to] == -1 || addCost <= costs[to]) {
                    queue.add(new Edge(to, addCost, node));
                }
            }
        }
    }

    class Edge implements Comparable<Edge>{
        private final int to;
        private final int cost;
        private final int prev;

        public Edge(int to, int weight) {
            this.to = to;
            this.cost = weight;
            this.prev = 0;
        }

        public Edge(int to, int weight, int prev) {
            this.to = to;
            this.cost = weight;
            this.prev = prev;
        }

        @Override
        public int compareTo(Edge o) {
            int compared = Integer.compare(this.cost, o.cost);
            if (compared == 0) {
                return Integer.compare(this.to, o.to);
            }
            return compared;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int m = Integer.parseInt(scanner.nextLine());
        String[] cities = new String[m];
        for (int i = 0; i < m; i++) {
            cities[i] = scanner.nextLine();
        }
        int start = scanner.nextInt();
        int end = scanner.nextInt();

        String[] result = new Main(n, cities).getShortestPath(start, end);
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);
    }
}
