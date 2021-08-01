import heapq
import sys
input = sys.stdin.readline

N, M = map(int, input().split())
board = [list(input().strip()) for _ in range(N)]  # 입력값 저장
value = [[0] * M for _ in range(N)]                # '.': 0, 'g': 3000, pg: 1
dist = [[-1] * M for _ in range(N)]                # cost 저장
dn = (1, 0, -1, 0)
dm = (0, 1, 0, -1)
q = []

# 다익스트라 알고리즘
def dijkstra():
    while q:
        cost, n, m = heapq.heappop(q)
        # 도착 지점인 경우
        if board[n][m] == 'F':
            print(dist[n][m] // 3000, dist[n][m] % 3000)
            return

        # 아닌 경우 4 방향 탐색하여 q에 저장
        for i in range(4):
            nn = n + dn[i]
            nm = m + dm[i]
            if (0 <= nn < N and 0 <= nm < M) and (dist[nn][nm] == -1 or dist[nn][nm] > cost + value[nn][nm]):
                dist[nn][nm] = cost + value[nn][nm]
                heapq.heappush(q, (dist[nn][nm], nn, nm))

# board에 값 넣기
for n in range(N):
    for m in range(M):
        # 시작 지점
        if board[n][m] == 'S':
            heapq.heappush(q, (0, n, m))
            dist[n][m] = 0
        # 쓰레기인 경우
        elif board[n][m] == 'g':
            value[n][m] = 3000
            # 주변 추가
            for i in range(4):
                nn = n + dn[i]
                nm = m + dm[i]
                if (0 <= nn < N and 0 <= nm < M) and board[nn][nm] == '.':
                    value[nn][nm] = 1

dijkstra()
