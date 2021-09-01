def solution(info, query):
    # 2차원 배열로 변경
    arr = []
    for i in info:
        arr.append(i.split())

    answer = []
    for q in query:
        condition = q.split(' and ')
        condition.extend(condition[-1].split())
        condition.pop(3)

        count = 0
        for applicant in arr:
            is_count = True
            for i in range(4):
                if condition[i] == '-':
                    continue
                elif condition[i] != applicant[i]:
                    is_count = False
                    break

            if is_count and int(applicant[-1]) >= int(condition[-1]):
                count += 1

        answer.append(count)

    return answer

print(solution(["java backend junior pizza 150","python frontend senior chicken 210","python frontend senior chicken 150","cpp backend senior pizza 260","java backend junior chicken 80","python backend senior chicken 50"], ["java and backend and junior and pizza 100","python and frontend and senior and chicken 200","cpp and - and senior and pizza 250","- and backend and senior and - 150","- and - and - and chicken 100","- and - and - and - 150"]))