from operator import itemgetter
def pretreatment(line, times):
    start_time, processing = line.split()[1:]
    start_time = start_time.split(':')

    processing = processing.replace('s','')
    processing = processing.split('.')
    processing = list(map(int, processing))
    if len(processing)>1:
        processing = processing[0]*1000 + processing[1]
    else:
        processing = processing[0]*1000
    start_time[-1] = start_time[-1].replace('.', '')
    start_time = list(map(int, start_time))

    start = start_time[0] * 3600*1000 + start_time[1] * 60*1000 + start_time[2]

    times.append([start - processing + 1, False])
    times.append([start + 999, True])


def solution(lines):
    answer = 0

    times = []
    for line in lines:
        pretreatment(line, times)
    times.sort(key=itemgetter(0, 1))
    cnt = 0
    for st, flag in times:
        if not flag:
            cnt += 1
        else:
            cnt -= 1
        answer = max(answer, cnt)
    return answer

lines = [["2016-09-15 00:00:00.000 3s"], ["2016-09-15 23:59:59.999 0.001s"],
         ["2016-09-15 01:00:04.001 2.0s", "2016-09-15 01:00:07.000 2s"],
         ["2016-09-15 01:00:04.002 2.0s", "2016-09-15 01:00:07.000 2s"],
         ["2016-09-15 20:59:57.421 0.351s", "2016-09-15 20:59:58.233 1.181s", "2016-09-15 20:59:58.299 0.8s", "2016-09-15 20:59:58.688 1.041s", "2016-09-15 20:59:59.591 1.412s", "2016-09-15 21:00:00.464 1.466s", "2016-09-15 21:00:00.741 1.581s", "2016-09-15 21:00:00.748 2.31s", "2016-09-15 21:00:00.966 0.381s", "2016-09-15 21:00:02.066 2.62s"],
         ["2016-09-15 00:00:00.000 2.3s", "2016-09-15 23:59:59.999 0.1s"]]
for line in lines:
    print(solution(line))