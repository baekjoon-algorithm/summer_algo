#import sys
#input = sys.stdin.readline

# DFS
# idx: 배열의 index, group: 해당 그룹의 숫자들
"""
처음 풀이, DFS로 시간초과 발생
def DFS(idx, g_idx, groups, group_sum):
    global ans_value

    if g_idx == M - 1:
        groups[M - 1] = sum(arr[idx:])

        # 각 그룹의 합의 최대값 찾기
        # 간단하게 줄일 수 있나?
        maxValue = max(groups)

        # 정답 값과 리스트 갱신
        if ans_value == -1 or ans_value > maxValue:
            ans_value = maxValue
            group_sum.clear()
            group_sum.extend(groups)
        groups[M - 1] = 0

    else:
        for i in range(idx, N - (M - g_idx) + 1):
            groups[g_idx] += arr[i]
            DFS(i + 1, g_idx + 1, groups, group_sum)
        groups[g_idx] = 0
"""
import sys
input = sys.stdin.readline

N, M = list(map(int, input().split()))
arr = list(map(int, input().split()))

# 이분 탐색에 활용, left = arr 원소들 중 최대값, right = sum(arr)
right = sum(arr)
left = max(arr)
ans_value = -1
group_count = []
while left <= right:
    mid = (left + right) // 2   # 각 그룹의 합 상한선
    temp_count = [1] * M        # 그룹마다 최소 한 개씩은 있어야함

    temp_sum, idx = arr[0], 0
    for i in range(1, N):
        if (N - i + 1) <= (M - idx):     # 남은 구슬 수가 남은 그룹 수보다 작거나 같은 경우 더 이상 진행 불가능
            break

        if temp_sum + arr[i] <= mid:
            temp_sum += arr[i]
            temp_count[idx] += 1
        else:
            idx += 1
            temp_sum = arr[i]

            # idx가 그룹 수보다 커진 경우, 더 이상 그룹 못 만듬
            if idx == M:
                break

    # 상한선이 작은 경우
    if idx >= M:
        left = mid + 1
    else:
        right = mid - 1
        ans_value = mid
        group_count = temp_count

print(ans_value)
print(*group_count)  # 리스트의 각 원소를 분리하여 출력