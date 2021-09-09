import bisect
def solution(words, queries):
    answer = []
    word_list = [[] for _ in range(10001)]
    Rword_list = [[] for _ in range(10001)]

    f1, f2 = 'A', 'z'
    for word in words:
        n = len(word)
        word_list[n].append(word)
        rword = list(word)
        rword.reverse()
        Rword_list[n].append(''.join(rword))

    for i in range(10001):
        word_list[i].sort()
        Rword_list[i].sort()

    for query in queries:
        m = len(query)
        query = list(query)
        st, ed = -1, -1
        for i in range(m):
            if query[i] == '?':
                if st == -1:
                    st = i
                    ed = i
                else:
                    ed = i
        word = ''
        if st != 0:
            for i in range(st):
                word += query[i]
            l = bisect.bisect_left(word_list[m], word + f1 * (ed - st + 1))
            r = bisect.bisect_right(word_list[m], word + f2 * (ed - st + 1))
        else:
            for i in range(m-1,ed,-1):
                word += query[i]
            l = bisect.bisect_left(Rword_list[m], word + f1 * (ed - st + 1))
            r = bisect.bisect_right(Rword_list[m], word + f2 * (ed - st + 1))
        answer.append(r-l)

    return answer

print(solution(['abcd', 'abcc'], ['abc?']))

