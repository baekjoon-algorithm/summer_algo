def solution(n, stations, w):
    answer = 0

    stations.sort()
    prev = 0
    val = 2*w + 1
    for ele in stations:
        empty = ele - w - 1 - prev
        while empty > 0:
            empty -= val
            answer += 1
        prev = ele + w
    if prev < n:
        empty = n - prev
        while empty > 0:
            empty -= val
            answer += 1

    return answer
