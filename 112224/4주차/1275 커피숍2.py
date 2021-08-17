import sys
input = sys.stdin.readline

n, q = map(int, input().split())
ary = [-1] + list(map(int, input().split()))


def init(seg, ary, se, ed, node):
    if se == ed:
        seg[node] = ary[se]
    else:
        m = (se+ed)//2
        init(seg, ary, se, m, 2*node)
        init(seg, ary, m+1, ed, 2*node + 1)
        seg[node] = seg[2*node] + seg[2*node+1]


# tree[node] 가 나타내는 것 [st, ed] 까지의 범위의 합
def update(seg, st, ed, node, idx, diff):
    # 현재 segment tree 가 idx를 포함하는 범위가 아니라면
    if idx < st or idx > ed:
        return
    # 포함한다면,
    seg[node] -= diff

    # st == ed 인 상황이면 leaf 노드에 도착
    if st != ed:
        m = (st+ed)//2
        # 다음 포함 범위에 따라서 업데이트 부분 찾아가기
        if idx <= m:
            update(seg, st, m, 2*node, idx, diff)
        else:
            update(seg, m+1, ed, 2*node +1, idx, diff)


def query(seg, st, ed, node, x, y):
    if ed < x or y < st:
        return 0
    if x <= st and ed <= y:
        return seg[node]
    m = (st+ed)//2

    return query(seg, st, m, 2*node, x, y) + query(seg, m+1, ed, 2*node+1, x, y)


seg = [0] * (1 << 18)
init(seg, ary, 1, n, 1)
for _ in range(q):
    x, y, a, b = map(int, input().split())
    if x > y:
        x, y = y, x
    print(query(seg, 1, n, 1, x, y))
    diff = ary[a] - b
    ary[a] = b
    update(seg, 1, n, 1, a, diff)