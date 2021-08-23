import sys
input = sys.stdin.readline
import math

n, m, k = map(int, input().split())
ary = [0]+[int(input()) for _ in range(n)]

h = math.ceil(math.log2(n)) + 1
seg = [0] * (1 << h)


def init(st, ed, node):
    if st == ed:
        seg[node] = ary[st]
        return
    m = (st+ed)//2
    init(st, m, 2*node)
    init(m+1, ed, 2*node + 1)
    seg[node] = seg[2*node] + seg[2*node + 1]
    return


def update(st, ed, node, idx, diff):
    seg[node] -= diff
    if st != ed:
        m = (st + ed) // 2
        if idx <= m:
            update(st, m, 2*node, idx, diff)
        else:
            update(m+1, ed, 2*node+1, idx, diff)


def query(st, ed, left, right, node):
    if left > ed or right < st:
        return 0
    if left <= st and ed <= right:
        return seg[node]
    m = (st + ed) // 2
    return query(st, m, left, right, 2*node) + query(m+1, ed, left, right, 2*node +1)


init(1, n, 1)

for _ in range(m+k):
    a, b, c = map(int, input().split())
    if a == 1:
        diff = ary[b] - c
        update(1, n, 1, b, diff)
        ary[b] = c
    elif a == 2:
        print(query(1, n, b, c, 1))