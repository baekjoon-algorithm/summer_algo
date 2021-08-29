import java.util.Arrays;
import java.util.List;

/** 프로그래머스 ID: 72412, 순위 검색 */
class Solution {
    private static final List<String> LANGUAGE = List.of("cpp", "java", "python");
    private static final List<String> PART = List.of("backend", "frontend");
    private static final List<String> EXPERIENCE = List.of("junior", "senior");
    private static final List<String> FOOD = List.of("chicken", "pizza");
    private static final int LANGUAGE_ALL = LANGUAGE.size();
    private static final int PART_ALL = PART.size();
    private static final int EXPERIENCE_ALL = EXPERIENCE.size();
    private static final int FOOD_ALL = FOOD.size();
    private static final int MAX_SCORE = 100000;
    private static final String ALL = "-";

    int[][][][][] scores = new int[LANGUAGE.size() + 1][PART.size() + 1][EXPERIENCE.size() + 1][FOOD.size() + 1][MAX_SCORE + 1];

    private void initScore(String[] info) {
        for (String i : info) {
            String[] tokens = i.split(" ");
            int language = getIndex(tokens[0], LANGUAGE);
            int part = getIndex(tokens[1], PART);
            int experience = getIndex(tokens[2], EXPERIENCE);
            int food = getIndex(tokens[3], FOOD);
            int score = Integer.parseInt(tokens[4]);

            // count all cases
            scores[language][part][experience][food][score] += 1;
            scores[LANGUAGE_ALL][part][experience][food][score] += 1;
            scores[LANGUAGE_ALL][PART_ALL][experience][food][score] += 1;
            scores[LANGUAGE_ALL][part][EXPERIENCE_ALL][food][score] += 1;
            scores[LANGUAGE_ALL][part][experience][FOOD_ALL][score] += 1;
            scores[LANGUAGE_ALL][PART_ALL][EXPERIENCE_ALL][food][score] += 1;
            scores[LANGUAGE_ALL][PART_ALL][experience][FOOD_ALL][score] += 1;
            scores[LANGUAGE_ALL][part][EXPERIENCE_ALL][FOOD_ALL][score] += 1;
            scores[LANGUAGE_ALL][PART_ALL][EXPERIENCE_ALL][FOOD_ALL][score] += 1;
            scores[language][PART_ALL][experience][food][score] += 1;
            scores[language][PART_ALL][EXPERIENCE_ALL][food][score] += 1;
            scores[language][PART_ALL][experience][FOOD_ALL][score] += 1;
            scores[language][PART_ALL][EXPERIENCE_ALL][FOOD_ALL][score] += 1;
            scores[language][part][EXPERIENCE_ALL][food][score] += 1;
            scores[language][part][EXPERIENCE_ALL][FOOD_ALL][score] += 1;
            scores[language][part][experience][FOOD_ALL][score] += 1;
        }
        initPreSum();
    }

    private void initPreSum() {
        for (int i = 0; i <= LANGUAGE.size(); i++) {
            for (int j = 0; j <= PART.size(); j++) {
                for (int k = 0; k <= EXPERIENCE.size(); k++) {
                    for (int l = 0; l <= FOOD.size(); l++) {
                        for (int m = MAX_SCORE - 1; m >= 0; m--) {
                            scores[i][j][k][l][m] += scores[i][j][k][l][m + 1];
                        }
                    }
                }
            }
        }
    }

    private int getIndex(String element, List<String> list) {
        if (ALL.equals(element)) {
            return list.size();
        }
        return list.indexOf(element);
    }

    private int executeQuery(String q) {
        String[] tokens = q.replaceAll(" and ", " ").split(" ");
        int language = getIndex(tokens[0], LANGUAGE);
        int part = getIndex(tokens[1], PART);
        int experience = getIndex(tokens[2], EXPERIENCE);
        int food = getIndex(tokens[3], FOOD);
        int limit = Integer.parseInt(tokens[4]);
        return scores[language][part][experience][food][limit];
    }

    public int[] solution(String[] info, String[] query) {
        initScore(info);
        int[] answer = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            answer[i] = executeQuery(query[i]);
        }
        return answer;
    }

    public static void main(String[] args) {
        String[] info = new String[]{
                "java backend junior pizza 150",
                "python frontend senior chicken 210",
                "python frontend senior chicken 150",
                "cpp backend senior pizza 260",
                "java backend junior chicken 80",
                "python backend senior chicken 50"
        };
        String[] query = new String[]{
                "java and backend and junior and pizza 100",
                "python and frontend and senior and chicken 200",
                "cpp and - and senior and pizza 250",
                "- and backend and senior and - 150",
                "- and - and - and chicken 100",
                "- and - and - and - 150"
        };
        int[] answer = new Solution().solution(info, query);
        System.out.println(Arrays.toString(answer));
    }
}
