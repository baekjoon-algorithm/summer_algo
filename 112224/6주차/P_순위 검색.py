from bisect import bisect_left
def str2idx(s):
    if s == '-':
        return 0
    if s in ['cpp', 'backend', 'junior', 'chicken']:
        return 1
    if s in ['java', 'frontend', 'senior', 'pizza']:
        return 2
    if s == 'python':
        return 3


def solution(info, query):
    answer = []

    table = [[[[[] for _ in range(3)] for _ in range(3)] for _ in range(3)] for _ in range(4)]

    for ele in info:
        ele = ele.split()
        score = int(ele[-1])
        ele = [str2idx(s) for s in ele[:-1]]
        ary = [[0,0,0,0] for _ in range(16)]
        for i in range(1<<4):
            for j in range(4):
                if i & (1<<j):
                    ary[i][j] = ele[j]
        for lang, jobs, year, food in ary:
            table[lang][jobs][year][food].append(score)

    for i in range(4):
        for j in range(3):
            for k in range(3):
                for l in range(3):
                    table[i][j][k][l].sort()

    for qu in query:
        qu = qu.split()
        score = int(qu[-1])
        op = [str2idx(s) for s in qu[:7:2]]
        vi = table[op[0]][op[1]][op[2]][op[3]]

        n = len(vi)
        k = bisect_left(vi, score)
        answer.append(n - k)


    return answer


print(solution(["java backend junior pizza 150", "python frontend senior chicken 210", "python frontend senior chicken 150", "cpp backend senior pizza 260", "java backend junior chicken 80", "python backend senior chicken 50"],
    ["java and backend and junior and pizza 100", "python and frontend and senior and chicken 200", "cpp and - and senior and pizza 250", "- and backend and senior and - 150", "- and - and - and chicken 100", "- and - and - and - 150"]))
