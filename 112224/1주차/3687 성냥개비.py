import sys
input = sys.stdin.readline

tc = int(input())

M_num = [0]*102
M_num[2], M_num[3] = '1', '7'

for i in range(4,101,2):
    M_num[i] = M_num[i-2] + '1'
    M_num[i+1] = M_num[i-1] + '1'
# 동전교환
# 2 : 1 , 3 : 7 , 4 : 4, 5 : 2, 6 : 0, 7 : 8, 8 : 10(2+6), 9 : 18(2+7)
# 10 : 3 + 7, 4 + 6, (5 + 5), = 22

m_num = [-1]*102
m_num[2:8] = list('174268')
m_num[8] = '10'

for i in range(9, 101):
    for j in range(2, 8):
        val = m_num[j] if j != 6 else '0'
        if m_num[i] == -1 or int(m_num[i]) > int(m_num[i-j] + val):
            m_num[i] = m_num[i-j] + val
        m_num[i] = str(m_num[i])

for _ in range(tc):
    n = int(input())
    print(m_num[n], M_num[n])
