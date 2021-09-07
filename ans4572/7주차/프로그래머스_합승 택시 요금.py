# _*_ coding: utf-8 _*_

def solution(n, s, a, b, fares):
    graph = [[float('inf')] * (n + 1) for _ in range(n+1)]

    for c, d, f in fares:
        graph[c][d] = f
        graph[d][c] = f

    for i in range(n+1):
        graph[i][i] = 0

    # 플로이드-와샬 알고리즘
    for k in range(n+1):
        for i in range(n+1):
            for j in range(n+1):
                graph[i][j] = min(graph[i][j], graph[i][k] + graph[k][j])

    # 처음 answer 초기화
    answer = graph[s][a] + graph[s][b]

    # 한 정점씩 확인하여 최소값 찾기
    for t in range(1, n+1):
        answer = min(answer, graph[s][t] + graph[t][a] + graph[t][b])

    return answer

print(solution(6, 4, 6, 2, [[4, 1, 10], [3, 5, 24], [5, 6, 2], [3, 1, 41], [5, 1, 24], [4, 6, 50], [2, 4, 66], [2, 3, 22], [1, 6, 25]]))