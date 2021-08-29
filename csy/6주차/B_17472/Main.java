import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/** 백준 ID: 17472, 다리 만들기 2 */
public class Main {
    private final int LAND = 1;
    private final int ISLAND_BASE = LAND + 1;
    public static final int MIN_BRIDGE_LEN = 2;
    private final int n;
    private final int m;
    private final int[][] worldMap;
    private Map<Integer, Map<Integer, Bridge>> graph = new HashMap<>();


    public Main(int n, int m, int[][] worldMap) {
        this.n = n;
        this.m = m;
        this.worldMap = worldMap;
    }

    private int getMinBridgeSize() {
        List<List<List<Integer>>> islands = searchIsland();
        int islandCnt = islands.size();
        for (int i = 0; i < islandCnt; i++) {
            buildBridge(i, islands.get(i));
        }

        // check all paths
        int minCnt = Integer.MAX_VALUE;
        for (int i = 0; i < islandCnt; i++) {
            minCnt = Math.min(dijkstra(islandCnt, i), minCnt);
            if (minCnt == -1) {
                break;
            }
        }
        return minCnt;
    }

    private List<List<List<Integer>>> searchIsland() {
        int[][] visited = new int[n][m];
        int cnt = 0;
        List<List<List<Integer>>> islands = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (visited[i][j] == 1) {
                    continue;
                }
                if (worldMap[i][j] == LAND) {
                    islands.add(getIsland(visited, i, j, cnt + ISLAND_BASE));
                    cnt++;
                }
            }
        }
        return islands;
    }

    private List<List<Integer>> getIsland(int[][] visited, int x, int y, int idx) {
        List<List<Integer>> island = new ArrayList<>();
        List<List<Integer>> queue = new ArrayList<>();
        queue.add(List.of(x, y));
        while (!queue.isEmpty()) {
            List<Integer> point = queue.remove(0);
            int i = point.get(0);
            int j = point.get(1);
            if (visited[i][j] == 1) {
                continue;
            }
            visited[i][j] = 1;
            worldMap[i][j] = idx;
            island.add(point);
            List<List<Integer>> directions = List.of(List.of(i - 1, j), List.of(i + 1, j), List.of(i, j - 1), List.of(i, j + 1));
            for (List<Integer> direction : directions) {
                int ii = direction.get(0);
                int jj = direction.get(1);
                if (ii >= 0 && ii < n && jj >= 0 && jj < m && worldMap[ii][jj] == LAND) {
                    queue.add(direction);
                }
            }
        }
        return island;
    }

    private void buildBridge(int island, List<List<Integer>> points) {
        for (List<Integer> point : points) {
            int i = point.get(0);
            int j = point.get(1);
            markBridge(ISLAND_BASE + island, i, j, -1, 0);
            markBridge(ISLAND_BASE + island, i, j, 1, 0);
            markBridge(ISLAND_BASE + island, i, j, 0, -1);
            markBridge(ISLAND_BASE + island, i, j, 0, 1);
        }
    }

    private void markBridge(int island, int x, int y, int deltaX, int deltaY) {
        int cnt = 1;
        int currentX = x + deltaX;
        int currentY = y + deltaY;
        while (currentX >= 0 && currentX < n && currentY >= 0 && currentY < m) {
            int current = worldMap[currentX][currentY];
            if (current == island) {
                // inside same island
                break;
            }
            if (current != 0) {
                if (cnt >= MIN_BRIDGE_LEN + 1) {
                    graph.putIfAbsent(current, new HashMap<>());
                    graph.putIfAbsent(island, new HashMap<>());

                    int dist = graph.get(current).containsKey(island) ?
                            Math.min(cnt - 1, graph.get(current).get(island).distance) : cnt - 1;

                    graph.get(current).put(island, new Bridge(dist));
                    graph.get(island).put(current, new Bridge(dist));
                }
                return;
            }
            currentX += deltaX;
            currentY += deltaY;
            cnt++;
        }
    }

    private int dijkstra(int islandCnt, int island) {
        int[] dist = new int[islandCnt];
        int[] prev = new int[islandCnt];
        int[] visited = new int[islandCnt];
        Arrays.fill(dist, -1);

        PriorityQueue<Bridge> pq = new PriorityQueue<>();
        pq.add(new Bridge(island + ISLAND_BASE, island + ISLAND_BASE, 0)); // dist, node
        while (!pq.isEmpty()) {
            Bridge current = pq.poll();
            int currentDist = current.distance;
            int prevNode = current.from;
            int currentNode = current.to;

            if (visited[currentNode - ISLAND_BASE] == 1) {
                continue;
            } else {
                visited[currentNode - ISLAND_BASE] = 1;
            }
            dist[currentNode - ISLAND_BASE] = currentDist;
            prev[currentNode - ISLAND_BASE] = prevNode;

            if (graph.get(currentNode) == null) {
                continue;
            }
            for (Map.Entry<Integer, Bridge> connected : graph.get(currentNode).entrySet()) {
                int nextNode = connected.getKey();
                int nextDistance = dist[currentNode - ISLAND_BASE] + connected.getValue().distance;
                if (dist[nextNode - ISLAND_BASE] == -1 || nextDistance <= dist[nextNode - ISLAND_BASE]) {
                    pq.add(new Bridge(currentNode, nextNode, nextDistance));
                }
            }
        }

        // check brige size
        int cnt = 0;
        for (int i = 0; i < islandCnt; i++) {
            if (dist[i] == -1) {
                return -1;
            }
            if (i != island) {
                cnt += graph.get(prev[i]).get(i + ISLAND_BASE).distance;
            }
        }
        return cnt;
    }

    class Bridge implements Comparable<Bridge>{
        private final int from;
        private final int to;
        private final int distance;
        private boolean visited;

        public Bridge(int distance) {
            this.from = -1;
            this.to = -1;
            this.distance = distance;
            this.visited = false;
        }

        public Bridge(int from, int to, int distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
            this.visited = false;
        }

        @Override
        public int compareTo(Bridge o) {
            int compared = Integer.compare(this.distance, o.distance);
            return compared == 0 ? Integer.compare(this.to, o.to) : compared;
        }
    }

    public static Main processInput() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();
        int[][] worldMap = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                worldMap[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
        }
        return new Main(n, m, worldMap);
    }

    public static void main(String[] args) {
        Main main = processInput();
        int size = main.getMinBridgeSize();
        System.out.println(size);
    }
}
