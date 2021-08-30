import sys
from collections import deque
input = sys.stdin.readline

N, M = list(map(int, input().split()))
arr = [list(map(int, input().split())) for _ in range(N)]

next = [(1, 0), (-1, 0), (0, 1), (0, -1)]
island = [[] for _ in range(7)]    # 1번 섬부터 각 섬의 위치들 저장

# 섬 넘버링 해주면서 island 배열에 섬 좌표들 저장
num = 2
for i in range(N):
    for j in range(M):
        if arr[i][j] == 1:
            arr[i][j] = num
            q = deque()
            q.append((i, j))
            island[num - 1].append((i, j))
            while q:
                n, m = q.popleft()
                for d in range(4):
                    nn = n + next[d][0]
                    nm = m + next[d][1]
                    if 0 <= nn < N and 0 <= nm < M and arr[nn][nm] == 1:
                        arr[nn][nm] = num
                        q.append((nn,nm))
                        island[num - 1].append((nn, nm))
            num += 1

graph = []    # 다리 연결 그래프, (섬1, 섬2, 다리 길이)

# A번째 섬과 B번째 섬 다리 연결이 가능한지 확인 후 graph에 다리 추가
def conn(A, B, island, graph):
    for n1, m1 in island[A]:
        for n2, m2 in island[B]:
            dis = 0
            if n1 == n2:
                big, small = max(m1, m2), min(m1, m2)
                for i in range(small + 1, big):
                    dis += 1
                    if arr[n1][i] > 1:
                        dis = -1
                        break
            elif m1 == m2:
                big, small = max(n1, n2), min(n1, n2)
                for i in range(small + 1, big):
                    dis += 1
                    if arr[i][m1] > 1:
                        dis = -1
                        break
            if dis >= 2:
                graph.append((A, B, dis))

    return

for i in range(1, 7):
    for j in range(i + 1, 7):
        conn(i, j, island, graph)

graph = set(graph)  # 중복 제거
graph = list(graph) # 다시 리스트로 변환
graph.sort(key = lambda x: x[2])    # 다리 길이를 기준으로 정렬
n = num - 2   # 섬의 개수
p = [0]       # parent
ans = 0

for i in range(1, n + 1):
    p.append(i)

def find(x):
    if x != p[x]:
        p[x] = find(p[x])
    return p[x]

def union(x, y):
    x = find(x)
    y = find(y)
    if x > y:
        p[x] = y
    else:
        p[y] = x

# 크루스칼 알고리즘
count = 0   # 간선 개수
while graph:
    if count == n - 1:
        break
    x, y, w = graph.pop(0)
    if find(x) != find(y):
        union(x, y)
        ans += w
        count += 1

# -1인 경우
if count < n - 1:
    print(-1)
else:
    print(ans)