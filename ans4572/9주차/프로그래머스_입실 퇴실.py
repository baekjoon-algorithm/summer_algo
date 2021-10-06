def solution(enter, leave):
    size = len(enter)
    answer = [0] * (size + 1)
    room = []
    idx = 0

    for l in leave:
        while l not in room:
            room.append(enter[idx])
            idx += 1

        room.remove(l)
        answer[l] += len(room)
        for r in room:
            answer[r] += 1

    return answer[1:]

print(solution([3,2,1], [1,3,2]))