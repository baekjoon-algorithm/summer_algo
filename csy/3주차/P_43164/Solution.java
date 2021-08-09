import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 프로그래머스 ID: 43164, 여행경로 */
class Solution {
    class Graph {
        Map<String, List<String>> graph;
        List<String> paths;

        Graph(String[][] edges) {
            graph = new HashMap<>();
            for (String[] edge : edges) {
                String from = edge[0];
                String to = edge[1];

                graph.putIfAbsent(from, new ArrayList<>());
                graph.putIfAbsent(to, new ArrayList<>());

                List<String> edgeList = graph.get(from);
                edgeList.add(to);
                edgeList.sort(Comparator.naturalOrder());
            }
        }

        public String[] getPaths(String from) {
            paths = new ArrayList<>();
            dfs(from);
            Collections.reverse(paths);
            return paths.toArray(new String[paths.size()]);
        }

        private void dfs(String from) {
            List<String> edges = graph.get(from);
            while (edges.size() > 0) {
                String to = edges.remove(0);
                dfs(to);
            }
            paths.add(from);
        }
    }

    public String[] solution(String[][] tickets) {
        return new Graph(tickets).getPaths("ICN");
    }
}
