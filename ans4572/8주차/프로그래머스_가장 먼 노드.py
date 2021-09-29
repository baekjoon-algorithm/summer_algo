from collections import deque

def solution(n, edge):
    graph = [[] for _ in range(n + 1)]

    for a, b in edge:
        graph[a].append(b)
        graph[b].append(a)

    visit = [False] * (n + 1)
    dist = [0] * (n + 1)
    visit[1] = True
    q = deque()
    q.append(1)

    # BFS 활용
    while q:
        now = q.popleft()
        for x in graph[now]:
            if not visit[x]:
                visit[x] = True
                dist[x] = dist[now] + 1
                q.append(x)

    return dist.count(max(dist))

print(solution(6, [[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]))