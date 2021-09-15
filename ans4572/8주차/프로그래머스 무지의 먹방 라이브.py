import heapq

def solution(food_times, k):
    # 음식의 합보다 K가 더 크거나 같은 경우
    if sum(food_times) <= k:
        return -1

    size = len(food_times)
    q = []  # 우선순위 큐
    for i in range(size):
        heapq.heappush(q, (food_times[i], i + 1))

    sum_time = 0   # 먹기 위해 사용한 시간
    prev_time = 0  # 직전에 다 먹은 음식 시간

    while sum_time + (q[0][0] - prev_time) * size <= k:
        now = heapq.heappop(q)[0]
        sum_time += (now - prev_time) * size
        size -= 1
        prev_time = now

    result = sorted(q, key =lambda x: x[1]) #음식 번호 기준으로 정렬
    return result[(k-sum_time) % size][1]

print(solution([3,1,2], 5))