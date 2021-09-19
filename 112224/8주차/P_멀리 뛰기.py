def solution(n):
    MOD = 1234567
    dp = [0] * (n + 1)
    dp[0] = 1
    for i in range(1, n+1):
        if i >= 1: dp[i] += dp[i-1] % MOD
        if i >= 2: dp[i] += dp[i-2] % MOD
        dp[i] %= MOD

    return dp[n]