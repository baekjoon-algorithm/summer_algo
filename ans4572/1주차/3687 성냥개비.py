import sys
input = sys.stdin.readline

min_num = [-1] * 101
min_num[2:8] = list('174268')
min_num[8] = '10'

# 최소값들 구하기
for num in range(9,101):
    for i in range(2,8):
        if i == 6:
            temp = min_num[num-i] + '0'
        else:
            temp = min_num[num-i] + min_num[i]

        if min_num[num] == -1 or int(min_num[num]) > int(temp):
            min_num[num] = temp

testcase = int(input())
for _ in range(testcase):
    test = int(input())
    if test % 2 == 0:
        max_num = '1' * (test // 2)
    else:
        max_num = '7' + '1' * (test // 2 - 1)
    print(min_num[test], max_num)