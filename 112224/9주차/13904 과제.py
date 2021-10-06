input = __import__('sys').stdin.readline
n = int(input())

ary = []
for _ in range(n):
    d, w = map(int, input().split())
    ary.append([-w, d])
ary.sort()
visit = [False] * (n + 1)
ans = 0
for w, d in ary:
    w = -w
    d = min(d, n)
    for i in range(d, 0, -1):
        if not visit[i]:
            visit[i] = True
            ans += w
            break
print(ans)