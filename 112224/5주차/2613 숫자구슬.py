import sys
input = sys.stdin.readline

n, m = map(int, input().split())
ary = list(map(int, input().split()))

lo, hi = max(ary), sum(ary) + 1
answer = 0
groups = []
while lo <= hi:
    mid = (lo + hi) // 2
    cnt = 1
    tmp = ary[0]
    tmp_group = []
    prev = 0
    for i in range(1, n):
        if tmp + ary[i] <= mid:
            tmp += ary[i]
        else:
            tmp_group.append(i - prev)
            prev = i
            tmp = ary[i]
            cnt += 1
    tmp_group.append(n - prev)
    if cnt > m:
        lo = mid + 1
    else:
        answer = mid
        groups = tmp_group
        hi = mid - 1
print(answer)
k = len(groups)
while len(groups) < m:
    if groups[k-1] > 1:
        groups[k-1] -= 1
        groups.append(1)
    else:
        k -= 1
print(*groups)