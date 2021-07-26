import sys
input = sys.stdin.readline
import heapq

# 다익스트라 알고리즘
def dijkstra(start, distance, graph):
    q = []
    heapq.heappush(q, (0,start))
    distance[start] = 0
    while q:
        dist, now = heapq.heappop(q)
        if distance[now] < dist:
            continue
        for i in graph[now]:
            cost = dist + i[1]
            if distance[i[0]] == -1 or cost < distance[i[0]]:
                distance[i[0]] = cost
                heapq.heappush(q, (cost, i[0]))

    return distance

T = int(input())
for _ in range(T):
    n, m, t = map(int, input().split())  # n: 교차로 개수, m: 도로 개수, t: 목적지 후보 개수
    s, g, h = map(int, input().split())  # s: 출발지, g,h: 지나가는 도로
    graph = [[] for _ in range(n + 1)]
    candidate = []        # 후보 교차로

    for _ in range(m):
        a, b, d = map(int, input().split())
        graph[a].append((b,d))
        graph[b].append((a,d))
    for _ in range(t):
        temp = int(input())
        candidate.append(temp)

    s_distance = dijkstra(s, [-1] * (n+1), graph)
    g_distance = dijkstra(g, [-1] * (n+1), graph)
    h_distance = dijkstra(h, [-1] * (n+1), graph)

    ans = []
    for des in candidate:
        if s_distance[des] == s_distance[g] + g_distance[h] + h_distance[des] or s_distance[des] == s_distance[h] + h_distance[g] + g_distance[des]:
            ans.append(des)

    ans.sort()
    print(" ".join(map(str, ans)))
