def time_to_int(time):
    time = list(map(int, time.split(':')))
    return time[0] * 3600 + time[1] * 60 + time[2]


def formatting(val):
    if val < 10:
        val = '0' + str(val)
    else:
        val = str(val)
    return val


def int_to_time(time):
    t1, re = divmod(time, 3600)
    t2, t3 = divmod(re, 60)
    t1, t2, t3 = formatting(t1), formatting(t2), formatting(t3)
    return t1+':'+t2+':'+t3


def solution(play_time, adv_time, logs):
    play_time = time_to_int(play_time)
    adv_time = time_to_int(adv_time)

    viewer = [0] * (play_time+1)
    for log in logs:
        st, ed = log.split('-')
        st, ed = time_to_int(st), time_to_int(ed)
        viewer[st] += 1
        viewer[ed] -= 1

    # 구간 별 재생 수 [st, ed)의 형태로 저장
    for i in range(1, play_time + 1):
        viewer[i] += viewer[i-1]
    # 해당 시간까지의 누적 재생 수
    for i in range(1, play_time + 1):
        viewer[i] += viewer[i-1]
    ans = 0
    max_time = viewer[adv_time - 1]

    for i in range(play_time - adv_time + 1):
        val = viewer[i + adv_time] - viewer[i]
        if val > max_time:
            ans = i + 1
            max_time = val
    return int_to_time(ans)





plays = ["02:03:55", "99:59:59", "50:00:00"]
advs = ["00:14:15", "25:00:00", "50:00:00"]
logs = [["01:20:15-01:45:14", "00:25:50-00:48:29", "00:40:31-01:00:00", "01:37:44-02:02:30", "01:30:59-01:53:29"],
        ["69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00"],
        ["15:36:51-38:21:49", "10:14:18-15:36:51", "38:21:49-42:51:45"]]
for i in range(3):
    print(solution(plays[i], advs[i], logs[i]))
