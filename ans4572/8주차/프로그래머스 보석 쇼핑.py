def solution(gems):
    size = len(set(gems))
    answer = [0, len(gems) - 1]
    left = right = 0
    dic = {gems[0] : 1}

    while (left < len(gems)) and (right < len(gems)):
        # 현재 범위의 보석 종류가 모두 있는 경우
        if len(dic) == size:
            if answer[1] - answer[0] > right - left:
                answer = [left, right]
            if dic[gems[left]] == 1:
                del dic[gems[left]]
            else:
                dic[gems[left]] -= 1
            left += 1
        # 현재 범위의 보석 종류가 부족한 경우
        else:
            right += 1
            if right == len(gems):
                break
            if gems[right] not in dic:
                dic[gems[right]] = 1
            else:
                dic[gems[right]] += 1

    return [answer[0] + 1, answer[1] + 1]