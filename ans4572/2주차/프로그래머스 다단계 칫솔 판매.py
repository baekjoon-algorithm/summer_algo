def solution(enroll, referral, seller, amount):
    answer = {}
    parent = {}
    for i in range(len(enroll)):
        answer[enroll[i]] = 0
        parent[enroll[i]] = referral[i]

    for i in range(len(seller)):
        now = seller[i]
        cost = amount[i] * 100
        while True:
            passcost = 0
            if cost * 0.1 >= 1:
               passcost = int(cost * 0.1)
            answer[now] += cost - passcost
            cost = passcost
            next = parent[now]
            if next == '-' or passcost == 0:
                break
            else:
                now = next

    return list(answer.values())

solution(["john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"], ["-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"], ["young", "john", "tod", "emily", "mary"], [12, 4, 2, 5, 10])