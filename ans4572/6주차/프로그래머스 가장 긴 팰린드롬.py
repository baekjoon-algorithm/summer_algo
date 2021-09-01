def solution(s):
    answer = 1

    # 최대 길이 팰린드롬 확인하여 반환
    def palindrome(s, left, right, answer):
        answer = max(answer, right - left - 1)
        while left >= 0 and right <= len(s) - 1:
            if s[left] == s[right]:
                answer = max(answer, right - left + 1)
                left -= 1
                right += 1
            else:
                break

        return answer

    for i in range(1, len(s) - 1):
        # 길이가 홀수인 경우
        answer = palindrome(s, i - 1, i + 1, answer)
        # 길이가 짝수인 경우
        if s[i] == s[i - 1]:
            answer = palindrome(s, i - 2, i + 1, answer)
        if s[i] == s[i + 1]:
            answer = palindrome(s, i - 1, i + 2, answer)

    return answer

print(solution("abcdcba"))
print(solution("abacde"))
print(solution("baac"))