import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 프로그래머스 ID: 86048, 입실 퇴실 */
public class Solution {
    private void markMet(List<Integer> room, int[][] met) {
        for (int i = 0; i < room.size(); i++) {
            int a = room.get(i) - 1;
            for (int j = i; j < room.size(); j++) {
                int b = room.get(j) - 1;
                if (a == b) {
                    continue;
                }
                met[a][b] = 1;
                met[b][a] = 1;
            }
        }
    }

    private int[] countMet(int[][] met) {
        int[] counts = new int[met.length];
        for (int i = 0; i < met.length; i++) {
            int sum = 0;
            for (int j = 0; j < met.length; j++) {
                sum += met[i][j];
            }
            counts[i] = sum;
        }
        return counts;
    }

    public int[] solution(int[] enter, int[] leave) {
        int enterIdx = 0;
        int[][] met = new int[enter.length][enter.length];
        List<Integer> room = new ArrayList<>();
        for (int l : leave) {
            while (!room.contains(l)) {
                room.add(enter[enterIdx++]);
                markMet(room, met);
            }
            room.remove(room.indexOf(l));
        }
        return countMet(met);
    }

    public static void main(String[] args) {
        int[] answer = new Solution().solution(new int[]{1,3,2}, new int[]{1,2,3});
        System.out.println(Arrays.toString(answer));
    }
}
