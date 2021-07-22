import sys
input = sys.stdin.readline
import heapq

tc = int(input())

for _ in range(tc):
    n, m, t = map(int, input().split())
    s, g, h = map(int, input().split())
    adj = [[] for _ in range(n+1)]

    particular = [g, h]
    for _ in range(m):
        a, b, d = map(int,input().split())
        if a in particular and b in particular:
            adj[a].append((b, 2*d-1))
            adj[b].append((a, 2*d-1))
        else:
            adj[a].append((b, 2*d))
            adj[b].append((a, 2*d))
    candi = [int(input()) for _ in range(t)]
    candi.sort()

    dist = [-1] * (n + 1)
    prev = [-1] * (n + 1)
    dist[s] = 0
    prev[s] = s
    is_contain = [False] * (n+1)
    heap = [(0, s)]
    heapq.heapify(heap)

    while heap:
        now_dis, now = heapq.heappop(heap)

        if now_dis > dist[now]:
            continue
        for ed, cost in adj[now]:
            if dist[ed] == -1 or dist[ed] > now_dis + cost:
                if now in particular and ed in particular:
                    is_contain[ed] = True
                else:
                    is_contain[ed] = is_contain[now]
                prev[ed] = now
                dist[ed] = now_dis + cost
                heapq.heappush(heap, (dist[ed], ed))
    ans = []
    for ele in candi:
        if is_contain[ele]:
            ans.append(ele)
    print(' '.join(map(str, ans)))