from collections import deque
def solution(n, edge):
    adj = [[] for _ in range(n + 1)]
    dist = [-1] * (n + 1)

    for u, v in edge:
        adj[u].append(v)
        adj[v].append(u)

    q = deque()
    q.append(1)
    dist[1] = 0
    while q:
        cur = q.popleft()
        for ele in adj[cur]:
            if dist[ele] == -1:
                dist[ele] = dist[cur] + 1
                q.append(ele)

    return dist.count(max(dist))