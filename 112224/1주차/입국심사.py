def solution(n, times):
    answer = 0
    lo, hi = 0, 10**13+1
    while lo <= hi:
        ret = 0
        mid = (lo+hi)//2
        for ele in times:
            ret += mid//ele
        if ret>= n:
            answer = mid
            hi = mid - 1
        else:
            lo = mid + 1
    return answer