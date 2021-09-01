def solution(table, languages, preference):
    table.sort()  # 사전순을 위해 정렬

    # 2차원 배열로 변경
    list = []
    for arr in table:
        list.append(arr.split())

    # 점수 계산
    score = []
    for i in list:
        sum = 0
        for j in range(len(languages)):
            if languages[j] in i:
                sum += (6 - i.index(languages[j])) * preference[j]
        score.append(sum)

    max_score = max(score)
    idx = score.index(max_score)

    return list[idx][0]

print(solution(["SI JAVA JAVASCRIPT SQL PYTHON C#", "CONTENTS JAVASCRIPT JAVA PYTHON SQL C++", "HARDWARE C C++ PYTHON JAVA JAVASCRIPT", "PORTAL JAVA JAVASCRIPT PYTHON KOTLIN PHP", "GAME C++ C# JAVASCRIPT C JAVA"], ["PYTHON", "C++", "SQL"], [7, 5, 5]))
