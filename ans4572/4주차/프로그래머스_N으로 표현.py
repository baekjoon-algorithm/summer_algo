def solution(N, number):
    dp = []  # dp[N-1]: N의 사용 개수에 따른 모든 경우의 수

    for i in range(1, 9):
        allSet = set()  # 모든 경우의 수 set
        allSet.add(int(str(N) * i))

        # dp[i] = dp[j] 연산 dp[-j-1]
        for j in range(0, i - 1):
            for op1 in dp[j]:
                for op2 in dp[-j-1]:
                    allSet.add(op1 + op2)
                    allSet.add(op1 - op2)
                    allSet.add(op1 * op2)
                    if op2 != 0:
                        allSet.add(op1 // op2)

        if number in allSet:
            return i

        dp.append(allSet)

    return -1