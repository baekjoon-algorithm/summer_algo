def solution(n, s, a, b, fares):
    a, b, s = a - 1, b - 1, s - 1
    dist = [[-1] * n for _ in range(n)]
    for i in range(n):
        dist[i][i] = 0
    for v1, v2, cost in fares:
        v1, v2 = v1 - 1, v2 - 1
        dist[v1][v2] = cost
        dist[v2][v1] = cost

    for k in range(n):
        for i in range(n):
            for j in range(n):
                if dist[i][k] == -1 or dist[k][j] == -1:
                    continue
                if dist[i][j] == -1 or dist[i][j] > dist[i][k] + dist[k][j]:
                    dist[i][j] = dist[i][k] + dist[k][j]
    answer = 987654321
    for i in range(n):
        if dist[a][i] != -1 and dist[b][i] != -1 and dist[s][i] != -1:
            answer = min(answer, dist[a][i] + dist[b][i] + dist[s][i])
    return answer

print(solution(6,4,6,2,[[4, 1, 10], [3, 5, 24], [5, 6, 2], [3, 1, 41], [5, 1, 24], [4, 6, 50], [2, 4, 66], [2, 3, 22], [1, 6, 25]]))