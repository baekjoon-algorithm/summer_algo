def combi(order, n, cnt, idx, length, check, val):
    if n == cnt:
        if val not in check:
            check[val] = 1
        else:
            check[val] += 1
        return
    if idx >= length:
        return
    combi(order, n, cnt + 1, idx + 1, length, check, val + order[idx])
    combi(order, n, cnt, idx + 1, length, check, val)
    return

def solution(orders, course):
    answer = []
    orders = [sorted(x) for x in orders]

    for n in course:
        check = dict()
        for order in orders:
            if len(order) < n:
                continue
            combi(order, n, 0, 0, len(order), check, '')
        if not check:
            continue
        ret = max(check.values())
        if ret < 2:
            continue
        for key, val in check.items():
            if val == ret:
                answer.append(key)

    return answer


print(solution(["ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"], [2,3,4]))