import sys
input = sys.stdin.readline

N = int(input())
arr = list(map(int, input().split()))    # 추 배열
M = int(input())
check = list(map(int, input().split()))  # 확인할 무게 저장 배열

dy = [[False] * 40001 for _ in range(N + 1)] # dy[index][sum]: 해당 index의 sum 값 가능 여부 저장 (안하면 시간 초과 발생)
visit = [False] * 40001                  # index 무게 가능 여부 저장

def DFS(index, sum, N, arr, visit):
    if dy[index][sum]:
        return

    visit[sum] = True
    dy[index][sum] = True

    if index == N:
        return

    DFS(index + 1, sum + arr[index], N, arr, visit)       # 왼쪽에 올리기
    DFS(index + 1, sum, N, arr, visit)                    # 올리기 X
    DFS(index + 1, abs(sum - arr[index]), N, arr, visit)  # 오른쪽에 올리기

DFS(0, 0, N, arr, visit)

for i in range(M):
    if visit[check[i]]:
        print("Y", end = " ")
    else:
        print("N", end = " ")