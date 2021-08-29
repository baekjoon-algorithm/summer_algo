/** 프로그래머스 ID: 12904, 가장 긴 팰린드롬 */
class Solution {
    private int getPalindromeLength(String s, int idx) {
        // palindrome with even length
        int evenLength = 2 * symmetrical(s, idx + 1, idx);
        // palindrome with odd length
        int oddLength = 1 + 2 * symmetrical(s, idx, idx);
        return Math.max(evenLength, oddLength);
    }

    private int symmetrical(String s, int leftFrom, int rightFrom) {
        int cnt = 0;
        for (int i = 1; leftFrom - i >= 0 && rightFrom + i < s.length(); i++) {
            if (s.charAt(leftFrom - i) != s.charAt(rightFrom + i)) {
                break;
            }
            cnt += 1;
        }
        return cnt;
    }

    public int solution(String s) {
        int maxPalindromeLength = 0;
        for (int i = 0; i < s.length(); i++) {
            maxPalindromeLength = Math.max(getPalindromeLength(s, i), maxPalindromeLength);
        }
        return maxPalindromeLength;
    }

    public static void main(String[] args) {
        int answer = new Solution().solution("cdaebbadce");
        System.out.println(answer);
    }
}
