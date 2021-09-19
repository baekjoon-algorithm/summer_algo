def solution(food_times, k):
    n = len(food_times)
    food_times = [[x, i + 1] for i, x in enumerate(food_times)]
    food_times.sort()

    prev = 0
    for i in range(n):
        val, idx = food_times[i]
        if val == prev: continue
        delta = val - prev
        if k >= delta * (n - i):
            k -= delta * (n - i)
        else:
            tmp = [y for x, y in food_times[i:]]
            tmp.sort()
            return tmp[k % len(tmp)]
        prev = val
    return -1