from collections import deque
import sys
input = sys.stdin.readline
sys.setrecursionlimit(10010)

n = int(input())
adj = [[]for _ in range(n+1)]
pare = [-1] * (n+1)
for _ in range(n):
    now, lc, rc = map(int, input().split())
    if lc != -1:
        pare[lc] = now
    if rc != -1:
        pare[rc] = now
    adj[now].extend([lc, rc])

root = 0
for i in range(1, n+1):
    if pare[i] == -1:
        root = i
        break

height = [-1] * (n+1)

q = deque()
q.append(root)
height[root] = 1

while q:
    now = q.popleft()
    for ele in adj[now]:
        if ele != -1:
            height[ele] = height[now] + 1
            q.append(ele)

cnt = [0]
levelm, levelM = [0]*(max(height)+1), [0]*(max(height)+1)
def inorder(cur, cnt):
    lc, rc = adj[cur]
    if lc != -1:
        inorder(lc, cnt)
    cnt[0] += 1
    h = height[cur]
    if levelm[h] == 0:
        levelm[h] = cnt[0]
    levelM[h] = cnt[0]
    if rc != -1:
        inorder(rc, cnt)

inorder(root, cnt)

ans, idx = -1, -1
for h in range(1, max(height)+1):
    val = levelM[h] - levelm[h]
    if val > ans:
        ans = val
        idx = h
print(idx, ans+1)