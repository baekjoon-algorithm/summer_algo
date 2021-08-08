import heapq


def solution(jobs):
    answer = 0
    n = len(jobs)
    jobs.sort()

    now_time = 0
    heap = []
    # 요칭 시간대로 작업이 들어와야함 => 현재 시간보다 적다면
    for req_time, burst_time in jobs:
        if now_time >= req_time:
            heapq.heappush(heap, (burst_time, req_time))
            continue
        while heap and now_time < req_time:
            bt, rt = heapq.heappop(heap)
            now_time += bt
            answer += now_time - rt

        if now_time < req_time:
            now_time = req_time
        heapq.heappush(heap, (burst_time, req_time))

    while heap:
        bt, rt = heapq.heappop(heap)
        now_time += bt
        answer += now_time - rt

    return answer//n
