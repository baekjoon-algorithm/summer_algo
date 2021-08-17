def solution(play_time, adv_time, logs):

    # 문자열로 구성된 시간을 초단위로 변경하여 반환
    def time2sec(time):
        h, m, s = list(map(int, time.split(':')))
        return h * 3600 + m * 60 + s

    # 플레이 시간과 광고 시간 변환
    play_time = time2sec(play_time)
    adv_time = time2sec(adv_time)
    arr = [0] * (play_time + 1)  # 초단위 개수 누적 배열

    # log들의 시작과 끝나는 시간을 arr에 반영
    for log in logs:
        start, end = list(log.split('-'))
        start = time2sec(start)
        end = time2sec(end)
        arr[start] += 1
        arr[end] -= 1

    # 누적 계산 진행
    for i in range(1, len(arr)):
        arr[i] += arr[i - 1]

    # 최다 재생 구간 찾기
    p1, p2 = 0, adv_time - 1
    t_sum = sum(arr[:p2 + 1])
    answer = 0
    max_sum = t_sum
    while p2 < play_time:
        t_sum -= arr[p1]
        p1 += 1
        p2 += 1
        t_sum += arr[p2]

        if t_sum > max_sum:
            max_sum = t_sum
            answer = p1

    ans_h = str(answer // 3600).zfill(2)
    ans_m = str((answer % 3600) // 60).zfill(2)
    ans_s = str(answer % 60).zfill(2)
    return ans_h + ":" + ans_m + ":" + ans_s

print(solution("00:00:10", "00:00:02", ["00:00:03-00:00:05", "00:00:03-00:00:05"]))
print(solution("02:03:55", "00:14:15", ["01:20:15-01:45:14", "00:40:31-01:00:00", "00:25:50-00:48:29", "01:30:59-01:53:29", "01:37:44-02:02:30"]))
print(solution("99:59:59", "25:00:00", ["69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00"]))
print(solution("50:00:00", "50:00:00", ["15:36:51-38:21:49", "10:14:18-15:36:51", "38:21:49-42:51:45"]))