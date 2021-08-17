import sys
input = sys.stdin.readline

n = int(input())
words = list(input().strip())
MOD = int(1e5) + 3
# 아스키 값 범위 97 ~ 122 -> 0 ~ 25
words = [ord(x) - 97 for x in words]


def mod(val):
    return val % MOD


def matching(words, mid):
    pos = [[] for _ in range(MOD)]

    hash_val = 0
    power = 1
    for i in range(n - mid + 1):
        if i == 0:
            for j in range(mid-1, -1, -1):
                hash_val += mod(words[j] * power)
                if j < mid - 1:
                    power = mod(power * 2)
        else:
            hash_val = mod(mod(2 * (hash_val - words[i-1] * power)) + mod(words[i + mid - 1]))

        found_flag = False
        if pos[hash_val]:

            for ele in pos[hash_val]:
                flag = True
                for j in range(mid):
                    if words[i + j] != words[ele + j]:
                        flag = False
                        break
                if flag:
                    found_flag = True
                    break
        if found_flag:
            return True
        else:
            pos[hash_val].append(i)
    return False


lo, hi = 0, n

while lo < hi:
    m = (lo + hi) // 2
    if matching(words, m):
        lo = m
    else:
        hi = m - 1

print(lo)