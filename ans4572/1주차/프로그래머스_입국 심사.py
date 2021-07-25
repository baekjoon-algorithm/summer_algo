def solution(n, times):
    times.sort()
    left = 0
    right = times[-1] * n

    ans = right
    while left <= right:
        sum = 0
        mid = (left + right) // 2

        for i in times:
            sum += mid // i

        if sum >= n:
            ans = min(ans, mid)
            right = mid - 1
        else:
            left = mid + 1

    return ans

print(solution(6, [7, 10]))