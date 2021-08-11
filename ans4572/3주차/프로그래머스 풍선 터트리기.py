def solution(a):
    answer = 2
    left, right = 0, len(a) - 1
    left_min, right_min = a[left], a[right]

    while left < right - 1:
        if a[left] > a[right]:
            left += 1
            if a[left] < left_min:
                left_min = a[left]
                answer += 1
        else:
            right -= 1
            if a[right] < right_min:
                right_min = a[right]
                answer += 1

    return answer

print(solution([-16,27,65,-2,58,-92,-71,-68,-61,-33]))