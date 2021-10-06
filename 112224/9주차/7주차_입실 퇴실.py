def solution(enter, leave):
    n = len(enter)
    rank = [0] * (n + 1)
    ans = [0] * (n + 1)
    for i in range(n):
        rank[enter[i]] = i + 1

    for i in range(n):
        flag = False
        for j in range(i):
            # 기본적인 상황 나보다 늦게 들어온 애가 먼저 나갔다면? 공간에 같이 머물렀음
            # 위 경우가 발생했다면? i번째 기준으로 j번 이후는 전부 만났음
            # 1 4 2 3, 2 1 3 4 의 경우
            # leave 의 마지막 애를 보면 1번은 4와 머물렀는지는 알 수 없지만, (1번이 4번보다 먼저 들어옴)
            # 4번 보다 나중에 들어온 2가 먼저 나갔으므로 둘은 같은 공간에 있었음
            if rank[leave[j]] > rank[leave[i]] or flag:
                ans[leave[i]] += 1
                ans[leave[j]] += 1
                flag = True

    return ans[1:]
