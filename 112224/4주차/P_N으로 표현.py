
def solution(n, number):
    # dp[i] = i 번에 만들 수 있는 숫자
    dp = [[]] + [[int(str(n)*i)] for i in range(1, 9)]
    print(dp)
    for i in range(1, 9):
        for j in range(1, i):
            for a in dp[j]:
                for b in dp[i-j]:
                    dp[i].append(a+b)
                    dp[i].append(a-b)
                    dp[i].append(a*b)
                    if b != 0:
                        dp[i].append(a//b)
        dp[i] = list(set(dp[i]))
        if number in dp[i]:
            return i
    return -1





    # nn 꼴들이 처리가 안됨 => bfs 불가
    # while q:
    #     now = q.popleft()
    #     # 사칙 연산 처리 완료
    #     if now + n < max_len and ary[now + n] != -1:
    #         ary[now + n] = ary[now] + 1
    #     if now - n >= 0 and ary[now - n] != -1:
    #         ary[now - n] = ary[now] + 1
    #     if now*n < max_len and ary[now * n] != -1:
    #         ary[now*n] = ary[now] + 1
    #     if ary[now // n] != -1:
    #         ary[now // n] = ary[now] + 1

print(solution(5, 12))
print(solution(2, 11))