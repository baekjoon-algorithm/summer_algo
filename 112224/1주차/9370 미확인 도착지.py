import sys
input = sys.stdin.readline
import heapq

tc = int(input())

for _ in range(tc):

    n, m, t = map(int, input().split())
    s, g, h = map(int, input().split())
    adj = [[] for _ in range(n+1)]

    particular_dist = 0
    for _ in range(m):
        a, b, d = map(int,input().split())
        adj[a].append((b,d))
        adj[b].append((a,d))
    candi = [int(input()) for _ in range(t)]

    dist = [-1] * (n + 1)
    dist[s] = 0
    heap = [(0, s)]
    heapq.heapify(heap)

    while heap:
        now_dis, now = heapq.heappop(heap)

        if now_dis > dist[now]:
            continue
        for ed, cost in adj[now]:
            if dist[ed] == -1 or dist[ed] > now_dis + cost:
                dist[ed] = now_dis + cost
                heapq.heappush(heap, (dist[ed], ed))
    print(dist[g], dist[h])