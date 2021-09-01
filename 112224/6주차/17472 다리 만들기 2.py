import sys
input = sys.stdin.readline
from collections import deque
import heapq

n, m = map(int, input().split())
ary = [list(map(int, input().split())) for _ in range(n)]
visit = [[False] * m for _ in range(n)]
lands = []

di = [(0, 1), (1, 0), (0,-1), (-1, 0)]
for i in range(n):
    for j in range(m):
        if not visit[i][j] and ary[i][j] == 1:
            q = deque()
            visit[i][j] = True
            q.append((i, j))
            land = []
            while q:
                x, y = q.popleft()
                land.append((x, y))
                for dx, dy in di:
                    nx, ny = x+dx, y+dy
                    if 0<=nx<n and 0<=ny<m:
                        if not visit[nx][ny] and ary[nx][ny] == 1:
                            visit[nx][ny] = True
                            q.append((nx, ny))
            lands.append(land)

def get_dist(l1, l2):
    ret = -1
    for x1, y1 in l1:
        for x2, y2 in l2:
            if x1 == x2 or y1 == y2:
                dist = abs(x1-x2) + abs(y1-y2) - 1
                if dist < 2:
                    continue
                flag = True
                if x1 == x2:
                    st, ed = y1, y2
                    if st > ed: st, ed = ed, st
                    for i in range(st+1, ed):
                        if visit[x1][i]:
                            flag = False
                            break
                if y1 == y2:
                    st, ed = x1, x2
                    if st > ed: st, ed = ed, st
                    for i in range(st+1, ed):
                        if visit[i][y1]:
                            flag = False
                            break
                if (ret == -1 or dist < ret) and flag:
                    ret = dist
    return ret


cnt = len(lands)
adj = [[] for _ in range(cnt)]

for i in range(cnt):
    for j in range(i+1, cnt):
        val = get_dist(lands[i], lands[j])
        if val == -1: continue
        adj[i].append((val, j))
        adj[j].append((val, i))

dist = [-1] * cnt
visit = [False] * cnt
dist[0] = 0
heap = [(0, 0)]

while heap:
    cur_dist, now = heapq.heappop(heap)
    if visit[now]:
        continue
    visit[now] = True
    dist[now] = cur_dist

    for cost, to in adj[now]:
        if not visit[to]:
            heapq.heappush(heap, (cost, to))

ans = 0
for ele in dist:
    if ele == -1:
        ans = -1
        break
    ans += ele

print(ans)
