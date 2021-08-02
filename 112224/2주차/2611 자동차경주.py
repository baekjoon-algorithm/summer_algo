import sys
input = sys.stdin.readline
from collections import deque


n = int(input())
m = int(input())

adj = [[] for _ in range(n+1)]
ind = [0] * (n+1)
dist = [0] * (n+1)
prev = [0] * (n+1)


for _ in range(m):
    a, b, c = map(int, input().split())
    ind[b] += 1
    adj[a].append((b, c))

q = deque()
q.append(1)
ind[1] = 0
while q:
    now = q.popleft()
    for ed, cost in adj[now]:
        if cost + dist[now] > dist[ed]:
            dist[ed] = dist[now] + cost
            prev[ed] = now
        ind[ed] -= 1
        if ind[ed] == 0:
            q.append(ed)
print(dist[1])

ret = [1]
i = 1
while True:
    i = prev[i]
    ret.append(i)
    if i == 1:
        break
ret.reverse()
print(' '.join(map(str, ret)))