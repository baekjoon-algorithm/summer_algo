def dfs(st, adj, used, cnt, n, ret, route):
    route = route + [st]
    if cnt == n:
        ret.append(route)
        return
    if st not in adj:
        return

    for ed, use in zip(adj[st], used[st]):
        if not use:
            idx = adj[st].index(ed)
            used[st][idx] = True
            dfs(ed, adj, used, cnt + 1, n, ret, route)
            if ret:
                return
            used[st][idx] = False


def solution(tickets):
    answer = []
    n = len(tickets)
    adj = dict()
    used = dict()
    for st, ed in tickets:
        if st in adj:
            adj[st].append(ed)
        else:
            adj[st] = [ed]

    for st in adj:
        adj[st].sort()
        used[st] = [False] * len(adj[st])

    dfs('ICN', adj, used, 0, n, answer, [])

    return answer[0]