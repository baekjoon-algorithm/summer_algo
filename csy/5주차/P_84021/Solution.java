import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 프로그래머스 ID 84021, 퍼즐 조각 채우기 */
public class Solution {
    private List<List<List<Integer>>> blocks;
    private int[][] gameBoard;
    int[] used;
    int n;

    private void init(int[][] gameBoard, int[][] table) {
        this.gameBoard = gameBoard;
        this.n = gameBoard.length;
        this.blocks = new ArrayList<>();
        findBlocks(table);
    }

    private void findBlocks(int[][] table) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (table[i][j] == 0) {
                    continue;
                }
                List<List<Integer>> block = new ArrayList<>();
                List<List<Integer>> queue = new ArrayList<>();
                queue.add(List.of(i, j));
                while (!queue.isEmpty()) {
                    List<Integer> point = queue.remove(0);
                    int x = point.get(0);
                    int y = point.get(1);
                    if (table[x][y] == 0) {
                        continue;
                    }
                    block.add(List.of(x - i, y - j));
                    table[x][y] = 0;
                    if (x - 1 >= 0)
                        queue.add(List.of(x - 1, y));
                    if (x + 1 < n)
                        queue.add(List.of(x + 1, y));
                    if (y - 1 >= 0)
                        queue.add(List.of(x, y - 1));
                    if (y + 1 < n)
                        queue.add(List.of(x, y + 1));
                }
                blocks.add(block);
            }
        }
    }

    public int getMaxBlockMatched() {
        this.used = new int[blocks.size()];
        return fillBlocks(0, 0, 0);
    }

    private boolean isNotFilledOrNotBlock(int bx, int by, int x, int y, int b_idx) {
        if (bx >= n || bx < 0 || by >= n || by < 0) {
            return false;
        }
        if (gameBoard[bx][by] == 1) {
            return false;
        }
        for (List<Integer> point : blocks.get(b_idx)) {
            if (bx == x + point.get(0) && by == y + point.get(1)){
                return false;
            }
        }
        return true;
    }

    private boolean matched(int x, int y, int b_idx) {
        for (List<Integer> point : blocks.get(b_idx)) {
            int realX = x + point.get(0);
            int realY = y + point.get(1);

            if (realX >= n || realX < 0 || realY >= n || realY < 0){
                return false;
            }

            if (gameBoard[realX][realY] == 1
                    || isNotFilledOrNotBlock(realX - 1, realY, x, y, b_idx)
                    || isNotFilledOrNotBlock(realX + 1, realY, x, y, b_idx)
                    || isNotFilledOrNotBlock(realX, realY - 1, x, y, b_idx)
                    || isNotFilledOrNotBlock(realX, realY + 1, x, y, b_idx)) {
                return false;
            }
        }
        return true;
    }

    private void rotate(int b_idx) {
        List<List<Integer>> block = blocks.get(b_idx);
        for (int i = 0; i < block.size(); i++) {
            List<Integer> point = block.get(i);
            int x = point.get(0);
            int y = point.get(1);
            block.set(i, List.of(y, -x));
        }
    }

    private void fill(int x, int y, int b_idx) {
        used[b_idx] = 1;
        for (List<Integer> point : blocks.get(b_idx)) {
            gameBoard[x + point.get(0)][y + point.get(1)] = 1;
        }
    }

    private int fillBlocks(int x, int y, int cnt) {
        if ((x == n - 1 && y == n - 1) || Arrays.stream(used).sum() == used.length) {
            return cnt;
        }
        // get next point
        int nextX = y == (n - 1) ? x + 1 : x;
        int nextY = y == (n - 1) ? 0 : y + 1;

        if (gameBoard[x][y] == 1) {
            return fillBlocks(nextX, nextY, cnt);
        }

        for (int i = 0; i < blocks.size(); i++) {
            if (used[i] == 0) {
                for (int j = 0; j < 4; j++) {
                    rotate(i);
                    if (matched(x, y, i)) {
                        fill(x, y, i);
                        return fillBlocks(nextX, nextY, cnt + blocks.get(i).size());
                    }
                }
            }
        }
        return fillBlocks(nextX, nextY, cnt);
    }

    public int solution(int[][] game_board, int[][] table) {
        init(game_board, table);
        return getMaxBlockMatched();
    }

    public static void main(String[] args) {
        int answer = new Solution().solution(new int[][]{
                        {0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
                        {1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0},
                        {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1},
                        {0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
                        {0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
                        {0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0},
                        {0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0},
                        {1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0},
                        {0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0},
                        {0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1},
                        {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0}},
                new int[][]{
                        {1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1},
                        {1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1},
                        {1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0},
                        {0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0},
                        {1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                        {1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1},
                        {1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1},
                        {0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
                        {1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1},
                        {1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
                        {1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1}});
        System.out.println(answer);
    }
}
