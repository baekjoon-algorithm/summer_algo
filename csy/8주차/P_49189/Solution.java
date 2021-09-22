import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/** 프로그래머스 ID: 49189, 가장 먼 노드 */
public class Solution {
    private int n;
    private Map<Integer, List<Node>> graph;

    class Node implements Comparable<Node>{
        private int to;
        private int dist;

        public Node(int to, int dist) {
            this.to = to;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

    private void init(int n, int[][] edges) {
        this.n = n;
        this.graph = new HashMap<>();
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(new Node(to, 1));
            graph.putIfAbsent(to, new ArrayList<>());
            graph.get(to).add(new Node(from, 1));
        }
    }

    private int dijkstra(int start) {
        int[] visited = new int[n + 1];
        int[] distance = new int[n + 1];
        Arrays.fill(distance, -1);
        int maxDistance = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(start, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int to = node.to;
            int dist = node.dist;
            if (visited[to] == 1) {
                continue;
            }
            distance[to] = dist;
            visited[to] = 1;
            maxDistance = Math.max(dist, maxDistance);

            for (Node nextNode : graph.get(to)) {
                int next = nextNode.to;
                int newDist = distance[to] + nextNode.dist;
                if (visited[next] == 1) {
                    continue;
                }
                if (distance[next] == -1 || newDist < distance[next]) {
                    queue.add(new Node(next, newDist));
                }
            }
        }

        int count = 0;
        for (int dist : distance) {
            if (dist == maxDistance) {
                count++;
            }
        }
        return count;
    }

    public int solution(int n, int[][] edge) {
        init(n, edge);
        return dijkstra(1);
    }

    public static void main(String[] args) {
        int answer = new Solution().solution(6,
                new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}});
        System.out.println(answer);
    }
}
