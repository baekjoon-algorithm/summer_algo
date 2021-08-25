import sys
import math
input = sys.stdin.readline

# 세그먼트 트리 생성
# 호출: init(1, 1, size)
def init(node, S, E):
    if S == E:
        tree[node] = leaf[S]
        return tree[node]
    else:
        # 왼쪽 자식과 오른쪽 자식 트리를 만들고 합을 저장
        tree[node] = init(node*2, S, (S + E)//2) + init(node*2 + 1, (S + E)//2 + 1, E)
        return tree[node]

# A[X] = V로 갱신
# node: 현재 node
# [S, E]: 구간 S ~ E
# 초기 호출: update(X, V, 1, 1, N)
def update(X, V, node, S, E):
    if S == E:
        tree[node] = V
        return
    MID = (S + E) // 2
    if X <= MID:
        update(X, V, 2 * node, S, MID)
    else:
        update(X, V, 2 * node + 1, MID + 1, E)
    tree[node] = tree[2 * node] + tree[2 * node + 1]  # 트리 갱신

# A[L] + ... + A[R] 구하기
# 초기 호출: query(L, R, 1, 1, N)
def query(L, R, node, S, E):
    # 구간에 겹치지 않는 경우
    if R < S or E < L:
        return 0
    # 구간에 완벽히 포함된 경우
    if L <= S and E <= R:
        return tree[node]
    MID = (S + E) // 2
    return query(L, R, 2 * node, S, MID) + query(L, R, 2 * node + 1, MID + 1, E)

N, M, K = list(map(int, input().split()))
leaf = [0] + [int(input()) for _ in range(N)]

tree = [0] * (1 << (math.ceil(math.log(N, 2)) + 1))   # 세그먼트 트리의 사이즈 = 2 ^ (트리의 높이 + 1)
init(1, 1, N)                                         # 트리 생성

for i in range(M + K):
    a, b, c = list(map(int, input().split()))
    if a == 1:
        update(b, c, 1, 1, N)
    else:
        print(query(b, c, 1, 1, N))