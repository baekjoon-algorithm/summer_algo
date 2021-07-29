def hierarchy(now, profit, answer, pare):
    val = profit // 10
    answer[now] += profit - val
    if val == 0 or pare[now] == -1:
        return
    hierarchy(pare[now], val, answer, pare)

def solution(enroll, referral, seller, amount):
    n, m = len(enroll), len(seller)
    pare = [-1]*n
    answer = [0]*n

    # 이름 -> 인덱스 연결 필요
    name2idx = dict()
    name2idx['-'] = -1
    for i in range(n):
        name2idx[enroll[i]] = i
    for i in range(n):
        pare[i] = name2idx[referral[i]]
    amount = [100 * x for x in amount]

    for key, val in zip(seller, amount):
        hierarchy(name2idx[key], val, answer, pare)

    print(pare)

    return answer




















enrolls = [["john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"],
           ["john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"]]
referrals = [["-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"],
             ["-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"]]
sellers = [["young", "john", "tod", "emily", "mary"],
           ["sam", "emily", "jaimie", "edward"]]
amounts = [[12, 4, 2, 5, 10], [2, 3, 5, 4]]

for enroll, referral, seller, amount in zip(enrolls, referrals, sellers, amounts):
    print(solution(enroll, referral, seller, amount))

