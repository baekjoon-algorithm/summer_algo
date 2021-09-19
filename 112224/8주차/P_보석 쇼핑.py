def solution(gems):
    answer = []
    n = len(gems)
    kinds = len(set(gems))

    now_contain = dict()
    i, j = 0, 0
    len_ans = n + 1
    while i <= j < n:
        key = gems[j]
        if key in now_contain:
            now_contain[key] += 1
        else:
            now_contain[key] = 1

        while len(now_contain) == kinds:
            if len_ans > j - i + 1:
                len_ans = j - i + 1
                answer = [i + 1, j + 1]
            now_contain[gems[i]] -= 1
            if now_contain[gems[i]] == 0: del now_contain[gems[i]]
            i += 1
        j += 1

    return answer
print(solution(["DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"]))