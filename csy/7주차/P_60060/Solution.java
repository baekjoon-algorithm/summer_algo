import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** 프로그래머스 ID: 60060, 가사 검색 */
public class Solution {
    private final int ALPHABET = 26;

    class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET];
        boolean terminal;
        int count = 0;

        void insert(String s) {
            insert(s, 0, 1);
        }

        void insertReverse(String s) {
            insert(s, s.length() - 1, -1);
        }

        void insert(String s, int idx, int delta) {
            if ((delta == 1 && idx == s.length()) || (delta == -1 && idx < 0)) {
                this.terminal = true;
                return;
            }
            count++;
            int next = s.charAt(idx) - 'a';
            if (children[next] == null) {
                children[next] = new TrieNode();
            }
            children[next].insert(s, idx + delta, delta);
        }

        TrieNode findReverse(String s) {
            return find(s, s.length() - 1, -1);
        }

        TrieNode find(String s) {
            return find(s, 0, 1);
        }

        TrieNode find(String s, int idx, int delta) {
            if ((delta == 1 && idx == s.length()) || (delta == -1 && idx < 0))
                return this;
            int next = s.charAt(idx) - 'a';
            if (children[next] == null)
                return null;
            return children[next].find(s, idx + delta, delta);
        }

        int count() {
            return count;
        }
    }


    public int[] solution(String[] words, String[] queries) {
        Map<Integer, TrieNode> preFixRoot = new HashMap<>();
        Map<Integer, TrieNode> postFixRoot = new HashMap<>();
        for (String word : words) {
            int key = word.length();
            preFixRoot.putIfAbsent(key, new TrieNode());
            postFixRoot.putIfAbsent(key, new TrieNode());
            preFixRoot.get(key).insert(word);
            postFixRoot.get(key).insertReverse(word);
        }

        int[] answer = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String query = queries[i];
            int key = query.length();
            String subQuery = query.replaceAll("\\?", "");
            if (!preFixRoot.containsKey(key)) {
                answer[i] = 0;
                continue;
            }
            TrieNode root;
            if (query.startsWith("?")) {
                root = postFixRoot.get(key).findReverse(subQuery);
            } else {
                root = preFixRoot.get(key).find(subQuery);
            }
            answer[i] = root == null ? 0 : root.count();
        }
        return answer;
    }

    public static void main(String[] args) {
        int[] answer = new Solution().solution(new String[]{"frodo", "front", "frost", "frozen", "frame", "kakao"},
                new String[]{"fro??", "????o", "fr???", "fro???", "pro?"});
        System.out.println(Arrays.toString(answer));
    }

    // https://jinomadstory.tistory.com/119
}
