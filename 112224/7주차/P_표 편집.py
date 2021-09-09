def solution(n, k, cmd):

    answer = [1] * (n +1)
    seg = [0] * (1 << 21)
    def init(st, ed, node):
        if st == ed:
            seg[node] = answer[st]
            return
        m = (st + ed) // 2
        init(st, m, 2 * node)
        init(m + 1, ed, 2 * node + 1)
        seg[node] = seg[2 * node] + seg[2 * node + 1]

    # 삭제, 복구 담당
    def update(st, ed, node, idx, diff):
        if idx < st or idx > ed:
            return
        seg[node] -= diff
        if st == ed: return
        m = (st + ed) // 2
        if idx <= m:
            update(st, m, 2 * node, idx, diff)
        else:
            update(m + 1, ed, 2 * node + 1, idx, diff)

    def get_sum(st, ed, node, left, right):
        if right < st or ed < left:
            return 0
        if left <= st and ed <= right:
            return seg[node]
        m = (st + ed) // 2
        return get_sum(st, m, 2*node, left, right) + get_sum(m+1, ed, 2*node+1, left,right)

    def query(st, ed, node, val):
        if st == ed:
            return st
        m = (st + ed)//2
        if val <= seg[2*node]:
            return query(st, m, 2*node, val)
        return query(m+1, ed, 2*node+1, val - seg[2*node])

    init(1, n, 1)
    remove = []
    cur = k + 1
    for ele in cmd:
        if ele == 'C':
            remove.append(cur)
            answer[cur] = 0
            update(1, n, 1, cur, 1)
            v1, v2 = get_sum(1,n,1, 1, cur), get_sum(1, n, 1, cur + 1, n)
            if v2 == 0:
                cur = query(1, n, 1, v1)
            else:
                cur = query(1, n, 1, v1 + 1)
        elif ele == 'Z':
            recover = remove.pop()
            answer[recover] = 1
            update(1, n, 1, recover, -1)
        else:
            com, val = ele.split()
            val = int(val)
            v1 = get_sum(1, n, 1, 1, cur)
            if com == 'U':
                cur = query(1, n, 1, v1 - val)
            else:
                cur = query(1, n, 1, v1 + val)
    answer = answer[1:]
    answer = ['O' if x else 'X' for x in answer]

    return ''.join(answer)

print(solution(8,2,["D 30","C","U 3","C","D 4","C","U 2","Z","Z","U 1","C"]))