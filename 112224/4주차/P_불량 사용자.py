def solution(user_id, banned_id):
    n, m = len(user_id), len(banned_id)
    visit = [False] * n
    adj = [[] for _ in range(m)]

    answer = set()
    for i in range(n):
        for j in range(m):
            if len(user_id[i]) != len(banned_id[j]):
                continue
            flag = True
            for k in range(len(user_id[i])):
                if banned_id[j][k] == '*':
                    continue
                if user_id[i][k] != banned_id[j][k]:
                    flag = False
                    break
            if flag:
                adj[j].append(i)

    def recursive(idx, m, ret):
        if idx == m:
            answer.add(ret)
            return
        for ele in adj[idx]:
            if not visit[ele]:
                visit[ele] = True
                recursive(idx+1, m, ret + (1<<ele))
                visit[ele] = False
        return
    recursive(0, m, 0)
    return len(answer)


user_ids = [["frodo", "fradi", "crodo", "abc123", "frodoc"],
            ["frodo", "fradi", "crodo", "abc123", "frodoc"],
            ["frodo", "fradi", "crodo", "abc123", "frodoc"]]
banned_ids = [["fr*d*", "abc1**"],
              ["*rodo", "*rodo", "******"],
              ["fr*d*", "*rodo", "******", "******"]]

for user, ban in zip(user_ids, banned_ids):
    print(solution(user, ban))