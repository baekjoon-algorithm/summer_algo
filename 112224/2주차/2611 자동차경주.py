import sys
input = sys.stdin.readline
import heapq

n = int(input())
m = int(input())

adj = [[] for _ in range(n+1)]
dist = [1] * (n+1)
prev = [0] * (n+1)
dist[1] = 0
prev[1] = 1
for _ in range(m):
    a, b, c = map(int, input().split())
    adj[a].append((b, -c))

heap = []
heapq.heappush(heap, (0, 1))

while heap:
    now_dis, now = heapq.heappop(heap)
    if now_dis > dist[now]:
        continue
    for ed, cost in adj[now]:
        if dist[ed] == 1 or dist[ed] > dist[now] + cost:
            dist[ed] = dist[now] + cost
            prev[ed] = now
            if ed != 1:
                heapq.heappush(heap, (dist[ed], ed))

print(-dist[1])
i = 1
ret = [1]
while prev[i] != 1:
    i = prev[i]
    ret.append(i)
ret.append(1)
ret.reverse()
print(' '.join(map(str, ret)))