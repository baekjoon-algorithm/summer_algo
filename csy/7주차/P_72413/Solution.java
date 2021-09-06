import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/** 프로그래머스 ID: 72413, 합승 택시 요금 */
public class Solution {
    private List<List<Edge>> createGraph(int n, int[][] fares) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] fare : fares) {
            int from = fare[0];
            int to = fare[1];
            int cost = fare[2];
            graph.get(from).add(new Edge(to, cost));
            graph.get(to).add(new Edge(from, cost));
        }
        return graph;
    }

    private int[] dijkstra(int n, List<List<Edge>> graph, int start) {
        int[] visited = new int[n + 1];
        int[] costs = new int[n + 1];
        Arrays.fill(costs, -1);

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(start, 0));
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int from = edge.to;
            int cost = edge.cost;
            if (visited[from] == 1) {
                continue;
            }
            visited[from] = 1;
            costs[from] = cost;
            for (Edge next : graph.get(from)) {
                int to = next.to;
                int nextCost = costs[from] + next.cost;
                if (costs[to] == -1 || nextCost < costs[to]) {
                    queue.add(new Edge(to, nextCost));
                }
            }
        }
        return costs;
    }

    class Edge implements Comparable<Edge>{
        private final int to;
        private final int cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    public int solution(int n, int s, int a, int b, int[][] fares) {
        List<List<Edge>> graph = createGraph(n, fares);
        int[] distS = dijkstra(n, graph, s);
        int[] distA = dijkstra(n, graph, a);
        int[] distB = dijkstra(n, graph, b);
        int cost = distS[a] + distS[b];
        for (int k = 1; k <= n; k++) {
            if (distS[k] == -1) {
                continue;
            }
            // s -> k -> a -> b
            cost = Math.min(distS[k] + distA[k] + distA[b], cost);
            // s -> k -> b -> a
            cost = Math.min(distS[k] + distB[k] + distB[a], cost);
            // s -> k -> a, k -> b
            cost = Math.min(distS[k] + distA[k] + distB[k], cost);
        }
        return cost;
    }

    public static void main(String[] args) {
        int answer = new Solution().solution(6, 4, 5, 6,
                new int[][]{{2,6,6}, {6,3,7}, {4,6,7}, {6,5,11}, {2,5,12}, {5,3,20}, {2,4,8}, {4,3,9}});
        System.out.println(answer);
    }
}
