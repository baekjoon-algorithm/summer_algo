import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Stream;

/** 백준 ID: 1445, 일요일 아침의 데이트 */
public class Main {
    static class Forest {
        public static final char START = 'S';
        public static final char FLOWER = 'F';
        public static final char GARBAGE = 'g';

        private final int n;
        private final int m;
        private final char[][] map;
        private final int[] s;
        private final int[] f;

        private int[][] garbageCnt;
        private int[][] garbageAdjCnt;

        Forest(int n, int m, char[][] map, int[] s, int[] f) {
            this.n = n;
            this.m = m;
            this.map = map;
            this.s = s;
            this.f = f;
        }

        public int[] getGarbageCount() {
            dijkstra(s);
            return new int[] {garbageCnt[f[0]][f[1]], garbageAdjCnt[f[0]][f[1]] - isGarbageAdj(f)};
        }

        private void dijkstra(int[] s) {
            int[][] visited = new int[n][m];
            garbageCnt = new int[n][m];
            init(garbageCnt, -1);
            garbageAdjCnt = new int[n][m];
            init(garbageAdjCnt, -1);

            PriorityQueue<Move> queue = new PriorityQueue<>();
            queue.add(new Move(s, 0, 0));

            while (!queue.isEmpty()) {
                Move move = queue.poll();
                int[] from = move.getFrom();

                if (visited[from[0]][from[1]] == 1) {
                    continue;
                }

                visited[from[0]][from[1]] = 1;
                garbageCnt[from[0]][from[1]] = move.getGarbageCnt();
                garbageAdjCnt[from[0]][from[1]] = move.getGarbageAdjCnt();

                int[][] moveTo = new int[][]{
                        {from[0] + 1, from[1]},
                        {from[0] - 1, from[1]},
                        {from[0], from[1] + 1},
                        {from[0], from[1] - 1}
                };
                for (int[] to : moveTo) {
                    if (!isOutside(to) && visited[to[0]][to[1]] == 0) {
                        int isToGarbage = isGarbage(to);
                        int gCnt = garbageCnt[from[0]][from[1]] + isToGarbage;
                        int gAdjCnt = garbageAdjCnt[from[0]][from[1]] + (isToGarbage == 1 ? 0 : isGarbageAdj(to));

                        if (garbageCnt[to[0]][to[1]] == -1 || gCnt < garbageCnt[to[0]][to[1]]
                                || gAdjCnt < garbageAdjCnt[to[0]][to[1]]) {
                            queue.add(new Move(to, gCnt, gAdjCnt));
                        }
                    }
                }
            }
        }

        private void init(int[][] arr, int i) {
            Stream.of(arr)
                    .forEach(a -> Arrays.fill(a, i));
        }

        private boolean isOutside(int[] p) {
            int x = p[0];
            int y = p[1];
            return x < 0 || x >= n || y < 0 || y >= m;
        }

        private int isGarbageAdj(int[] p) {
            int x = p[0];
            int y = p[1];
            if ((x - 1 >= 0 && map[x - 1][y] == GARBAGE) || (x + 1 < n && map[x + 1][y] == GARBAGE)
                    || (y - 1 >= 0 && map[x][y - 1] == GARBAGE) || (y + 1 < m && map[x][y + 1] == GARBAGE)) {
                return 1;
            }
            return 0;
        }

        private int isGarbage(int[] p) {
            return map[p[0]][p[1]] == GARBAGE ? 1 : 0;
        }

        class Move implements Comparable<Move>{
            private int[] from;
            private int garbageCnt;
            private int garbageAdjCnt;

            public Move(int[] to, int garbageCnt, int garbageAdjCnt) {
                this.from = to;
                this.garbageCnt = garbageCnt;
                this.garbageAdjCnt = garbageAdjCnt;
            }

            public int[] getFrom() {
                return from;
            }

            public int getGarbageCnt() {
                return garbageCnt;
            }

            public int getGarbageAdjCnt() {
                return garbageAdjCnt;
            }

            @Override
            public int compareTo(Move o) {
                int compared = Integer.compare(this.garbageCnt, o.garbageCnt);
                return compared == 0 ? Integer.compare(this.garbageAdjCnt, o.garbageAdjCnt) : compared;
            }
        }
    }

    public static Forest processInput() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();

        int[] s = new int[2];
        int[] f = new int[2];
        char[][] map = new char[n][m];
        for (int i = 0; i < n; i++) {
            String row = scanner.nextLine();
            for (int j = 0; j < m; j++) {
                char point = row.charAt(j);
                if (point == Forest.START) {
                    s[0] = i;
                    s[1] = j;
                }
                if (point == Forest.FLOWER) {
                    f[0] = i;
                    f[1] = j;
                }
                map[i][j] = point;
            }
        }
        return new Forest(n, m, map, s, f);
    }

    public static void main(String[] args) {
        Forest forest = processInput();
        int[] garbageCnt = forest.getGarbageCount();
        System.out.println(garbageCnt[0] + " " + garbageCnt[1]);
    }
}
