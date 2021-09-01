# 혹시 해서 pandas 써봤더니 당연하게도 시간초과
'''import pandas as pd


def solution(info, query):
    answer = []
    cols = ['lang', 'pre', 'year', 'food', 'score']
    data = []
    for ele in info:
        ele = ele.split()
        data.append(ele)
    df = pd.DataFrame(data, columns=cols)

    df[cols[4]] = df[cols[4]].apply(pd.to_numeric)
    for ele in query:
        ele = ele.split()
        key = [ele[i] for i in range(0, 7, 2)] + [ele[7]]

        ret = df
        condition =
        for i in range(4):
            if key[i] == '-':
                continue
            ret = ret[ret[cols[i]] == key[i]]
        if key[4] != '-':
            ret = ret[ret[cols[4]] >= int(key[4])]
        answer.append(len(ret))

    return answer'''


def str2idx(s):
    if s in ['cpp', 'backend', 'junior', 'chicken']:
        return 0
    if s in ['java', 'frontend', 'senior', 'pizza']:
        return 1
    if s == 'python':
        return 2
    return -1


def solution(info, query):
    answer = []
    table = [dict() for _ in range(4)]
    score = []
    n = len(info)
    for i in range(n):
        ele = info[i]
        ele = ele.split()
        for j in range(4):
            idx = str2idx(ele[j])
            if idx not in table[j]:
                table[j][idx] = set({i})
            else:
                table[j][idx].add(i)
        score.append(int(ele[-1]))

    whole = set()
    whole.update(list(range(n)))
    for ele in query:
        ele = ele.split()
        key = [ele[x] for x in range(0,7,2)]

        ret = whole.copy()
        for j in range(4):
            idx = str2idx(key[j])
            if idx == -1:
                continue
            if idx in table[j]:
                ret &= table[j][idx]

        cut = int(ele[7])
        cnt = 0
        for i in ret:
            if score[i] >= cut:
                cnt += 1
        answer.append(cnt)
    return answer


print(solution(["java backend junior pizza 150", "python frontend senior chicken 210", "python frontend senior chicken 150", "cpp backend senior pizza 260", "java backend junior chicken 80", "python backend senior chicken 50"],
               ["java and backend and junior and pizza 100", "python and frontend and senior and chicken 200", "cpp and - and senior and pizza 250", "- and backend and senior and - 150", "- and - and - and chicken 100", "- and - and - and - 150"]))