import java.util.List;
import java.util.stream.IntStream;

/** 프로그래머스 ID: 84325, 직업군 추천하기 */
public class Solution {
    static private final List<String> LANGUAGES = List.of("JAVA", "JAVASCRIPT", "C", "C++" ,"C#" , "SQL", "PYTHON", "KOTLIN", "PHP");
    static private final List<String> JOBS = List.of("SI", "CONTENTS", "HARDWARE", "PORTAL", "GAME");
    public static final int MAX_JOB_SCORE = 5;
    private int[][] jobScore = new int[JOBS.size()][LANGUAGES.size()];

    private void initJobScore(String[] table) {
        for (String t: table) {
            String[] tokens = t.split(" ");
            int job = JOBS.indexOf(tokens[0]);
            IntStream.range(0, MAX_JOB_SCORE)
                    .forEach(i -> jobScore[job][LANGUAGES.indexOf(tokens[i + 1])] = 5 - i);
        }
    }

    private String getBestJob(String[] table, String[] languages, int[] preference) {
        initJobScore(table);
        int bestJob = 0;
        int maxScore = 0;
        for (int i = 0; i < JOBS.size(); i++) {
            int score = 0;
            for (int j = 0; j < languages.length; j++) {
                score += jobScore[i][LANGUAGES.indexOf(languages[j])] * preference[j];
            }
            if (score > maxScore || (score == maxScore && JOBS.get(i).compareTo(JOBS.get(bestJob)) < 0)) {
                maxScore = score;
                bestJob = i;
            }
        }
        return JOBS.get(bestJob);
    }

    public String solution(String[] table, String[] languages, int[] preference) {
        return getBestJob(table, languages, preference);
    }

    public static void main(String[] args) {
        String job = new Solution().solution(new String[]{
                "SI JAVA JAVASCRIPT SQL PYTHON C#",
                "CONTENTS JAVASCRIPT JAVA PYTHON SQL C++",
                "HARDWARE C C++ PYTHON JAVA JAVASCRIPT",
                "PORTAL JAVA JAVASCRIPT PYTHON KOTLIN PHP",
                "GAME C++ C# JAVASCRIPT C JAVA"}, new String[] {"PYTHON", "C++", "SQL"}, new int[]{7, 5, 5});
        System.out.println(job);
    }
}
