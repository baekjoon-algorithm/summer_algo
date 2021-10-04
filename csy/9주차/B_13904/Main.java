import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/** 백준 ID: 13904, 과제 */
public class Main {
    int n;
    int[][] dp;
    List<Assignment> assignmentList;

    public Main(int n, List<Assignment> assignmentList) {
        this.n = n;
        this.assignmentList = assignmentList.stream().sorted().collect(Collectors.toList());
    }

    private int doAssignment() {
        this.dp = new int[n][n];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return doAssignment(0, 0);
    }

    private int doAssignment(int index, int day) {
        if (index == assignmentList.size()) {
            return 0;
        }
        if (dp[index][day] != -1) {
            return dp[index][day];
        }

        int maxScore = 0;
        Assignment assignment = assignmentList.get(index);
        // do assignment
        if (day < assignment.deadline) {
            maxScore = Math.max(doAssignment(index + 1, day + 1) + assignment.score, maxScore);
        }
        // or not
        maxScore = Math.max(doAssignment(index + 1, day), maxScore);

        dp[index][day] = maxScore;
        return maxScore;
    }

    static class Assignment implements Comparable<Assignment> {
        int deadline;
        int score;

        public Assignment(int deadline, int score) {
            this.deadline = deadline;
            this.score = score;
        }

        @Override
        public int compareTo(Assignment o) {
            return Integer.compare(this.deadline, o.deadline);
        }
    }

    static Main processInput() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();

        List<Assignment> assignments = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int deadline = scanner.nextInt();
            int score = scanner.nextInt();
            scanner.nextLine();
            assignments.add(new Assignment(deadline, score));
        }
        return new Main(n, assignments);
    }

    public static void main(String[] args) {
        Main main = processInput();
        int score = main.doAssignment();
        System.out.println(score);
    }
}
