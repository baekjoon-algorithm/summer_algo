def solution(a):
    answer = 0
    b = [(ele, i) for i, ele in zip(range(len(a)), a)]
    b.sort()

    m, M = 1e9 + 1 , -1e9 -1
    for val, ele in b:
        if m < ele < M:
            continue
        answer += 1
        m = min(m, ele)
        M = max(M, ele)
    return answer


arr = [[9,-1,-5],[-16,27,65,-2,58,-92,-71,-68,-61,-33]]

for ele in arr:
    print(solution(ele))


