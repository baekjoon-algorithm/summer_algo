# _*_ coding: utf-8 _*_

# 범위에 속한 로그 개수 카운트 메소드
def CalCount(time, logs):
    count = 0
    start, end = time, time + 1000
    for log in logs:
        if start <= log[1] and end > log[0]:  # 범위에 속하는 경우
            count += 1
    return count

def solution(lines):
    logs = []  # 시작 시간과 끝 시간 저장 (단위: 밀리초)
    for line in lines:
        arr = list(line.split())

        h, m, s = list(map(float, arr[1].split(':')))
        t = float(arr[2][:-1]) * 1000   # 처리 시간
        end = (h * 3600 + m * 60 + s) * 1000  # 밀리초로 계산

        # 시작 시간과 끝 시간 계산하여 튜플로 추가
        logs.append((end - t + 1, end))

    answer = 0
    for log in logs:
        answer = max(CalCount(log[0], logs), CalCount(log[1], logs), answer)

    return answer

print(solution(	["2016-09-15 01:00:04.001 2.0s", "2016-09-15 01:00:07.000 2s"]))