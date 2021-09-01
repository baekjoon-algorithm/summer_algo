def solution(table, languages, preference):
    score = []
    domain = []

    for i in range(len(table)):
        ele = table[i].split()
        domain.append((ele[0], i))

        domain_score = dict()
        val = 5
        for key in ele[1:]:
            domain_score[key] = val
            val -= 1
        score.append(domain_score)

    domain.sort()
    answer, value = '', 0
    for dom, key in domain:
        now = score[key]
        ret = 0
        for lang, pre in zip(languages, preference):
            if lang in now:
                ret += now[lang] * pre
        if ret > value:
            value = ret
            answer = dom
    return answer