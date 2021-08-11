import heapq

def solution(jobs):
    jobs = sorted(jobs, key = lambda x : (x[0], x[1]))   # 작업 요청되는 시점을 기준으로 정렬

    complete = [0] * len(jobs)   # 각 작업의 요청부터 종료까지 걸린 시간
    count = len(jobs)            # 남은 작업의 개수
    time = 0                     # 시점
    h = []
    while count > 0:
        if not h:
            # 하드디스크 작업을 수행하고 있지 않을 때 먼저 요청이 들어온 작업 찾기
            for idx, job in enumerate(jobs):
                if complete[idx] == 0:
                    heapq.heappush(h, (job[1], idx))
                    time = job[0]
                    break

        # 작업 진행
        work, idx = heapq.heappop(h)
        complete[idx] = (time - jobs[idx][0]) + work    # 걸린시간 = 대기한 시간 + 작업 시간
        time += work
        count -= 1

        # 가능한 작업들 힙에 추가
        for idx, job in enumerate(jobs):
            if complete[idx] == 0 and job[0] <= time:
                heapq.heappush(h, (job[1], idx))  # 작업 시간을 기준으로 힙에 넣기
                complete[idx] = -1                # 힙에 넣었다면 중복되지 않기 위해 -1로 변경
            if job[0] > time:
                break

    return sum(complete) // len(complete)

print(solution([[24, 10], [28, 39], [43, 20], [37, 5], [47, 22], [20, 47], [15, 2], [15, 34], [35, 43], [26, 1]]))