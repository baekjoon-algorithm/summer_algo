import sys
input = sys.stdin.readline

N = int(input())
node = [(0,-1,-1)]
parent = [-1] * (N + 1)  # root를 찾기 위한 parent 배열
for _ in range(N):
    p,lc,rc = map(int, input().split())
    node.append((p, lc, rc))
    if lc != -1:
        parent[lc] = p
    if rc != -1:
        parent[rc] = p

node.sort()

# root 찾기
root = 0
for idx, val in enumerate(parent[1:]):
    if val == -1:
        root = idx + 1
        break

count = 1
level = [[] for _ in range(10001)]  # 레벨별 열 번호들 저장
# 중위순회 활용
def DFS(node, level, idx, lev):
    global count
    # to left child
    if node[idx][1] != -1:
        DFS(node, level, node[idx][1], lev + 1)
    # now node
    level[lev].append(count)
    count += 1
    # to right child
    if node[idx][2] != -1:
        DFS(node, level, node[idx][2], lev + 1)

DFS(node, level, root, 1)

# 정답 구하기
ansLevel = ansWidth = 1
for lev, arr in enumerate(level[1:]):
    if not arr:
        break
    width = (max(arr) - min(arr) + 1)
    if ansWidth < width:
        ansLevel = lev + 1
        ansWidth = width

print(ansLevel, ansWidth)
