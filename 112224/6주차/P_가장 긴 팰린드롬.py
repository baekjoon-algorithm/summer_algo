def solution(s):
    answer = 1
    n = len(s)
    dp = [[-1] * n for _ in range(n)]

    def recursive(st, ed):
        if st > ed:
            return 0
        if dp[st][ed] != -1:
            return dp[st][ed]
        if s[st] != s[ed]:
            dp[st][ed] = -1
            return -1
        val = recursive(st + 1, ed - 1)
        if val == -1:
            dp[st][ed] = -1
            return -1
        dp[st][ed] = val + 2
        return val + 2

    for i in range(n):
        dp[i][i] = 1

    for i in range(1, n):
        for j in range(n - i):
            recursive(j, j + i)
            if dp[j][j+i] != -1:
                answer = max(answer, dp[j][j+i])

    return answer

ss = ["abcdcba", "abcde"]
for s in ss:
    print(solution(s))