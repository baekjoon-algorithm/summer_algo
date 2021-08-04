import sys
input = sys.stdin.readline

n = int(input())
ary = list(map(int, input().split()))
k = int(input())
cn = list(map(int, input().split()))

dp = {0}
for ele in ary:
    tmp = set()
    for val in dp:
        tmp.update({ele+val, abs(ele-val)})
    dp |= tmp

ans = ['Y' if ele in dp else 'N' for ele in cn]
print(*ans)