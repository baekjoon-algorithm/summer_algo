import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** 백준 ID: 2629, 양팔저울 */
public class Main {
    private int[] numbers;
    private int[] unknowns;

    Main (int[] numbers, int[] unknowns) {
        this.numbers = numbers;
        this.unknowns = unknowns;
    }

    public String getAnswer() {
        List<Integer> diffs = getAllDiffs();
        List<String> answer = new ArrayList<>();
        for (int unknown : unknowns) {
            answer.add(diffs.stream().anyMatch(diff -> Math.abs(diff) == unknown) ? "Y" : "N");
        }
        return String.join(" ", answer);
    }

    public List<Integer> getAllDiffs() {
        return getAllDiffs(0);
    }

    public List<Integer> getAllDiffs(int idx) {
        if (idx == numbers.length) {
            List<Integer> diffs = new ArrayList<>();
            diffs.add(0);
            return diffs;
        }

        int number = numbers[idx];
        List<Integer> diffs = getAllDiffs(idx + 1);
        List<Integer> newDiffs = new ArrayList<>();
        for (int diff: diffs) {
            newDiffs.add(diff + number);
            newDiffs.add(diff - number);
            newDiffs.add(diff);
        }
        return newDiffs.stream().distinct().collect(Collectors.toList());
    }

    public static Main processInput() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        scanner.nextLine();

        int[] numbers = IntStream.range(0, n)
                .map(i -> scanner.nextInt())
                .toArray();
        scanner.nextLine();

        int m = scanner.nextInt();
        scanner.nextLine();

        int[] unknowns = IntStream.range(0, m)
                .map(i -> scanner.nextInt())
                .toArray();
        scanner.nextLine();

        return new Main(numbers, unknowns);
    }

    public static void main(String[] args) {
        var answer = processInput().getAnswer();
        System.out.println(answer);
    }
}
