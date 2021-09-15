def solution(n):
    dy = [0, 1, 2]

    for i in range(3, n + 1):
        dy.append((dy[i - 1] + dy[i - 2]) % 1234567)

    return dy[n]