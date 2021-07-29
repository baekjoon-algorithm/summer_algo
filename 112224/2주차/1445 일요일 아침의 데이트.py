import sys
input = sys.stdin.readline
import heapq

class node:
    def __init__(self, d1, d2):
        self.d1 = d1
        self.d2 = d2

    def __add__(self, other):
        return node(self.d1 + other.d1, self.d2 + other.d2)

    def __sub__(self, other):
        return node(self.d1 - other.d1, self.d2 - other.d2)

    def __lt__(self, other):
        if self.d1 == -1 or other.d1 == -1:
            return self.d1 != -1
        elif self.d1 == other.d1:
            return self.d2 < other.d2
        return self.d1 < other.d1
    def __str__(self):
        return f'{self.d1} {self.d2}'

di = [(0,1), (1,0), (0,-1), (-1,0)]

n, m = map(int, input().split())
board = [[0]*m for _ in range(n)]
garden = [input().strip() for _ in range(n)]

st, ed = (-1, -1), (-1, -1)
for i in range(n):
    for j in range(m):
        if garden[i][j] == 'S':
            st = (i, j)
        elif garden[i][j] == 'F':
            ed = (i, j)
        elif garden[i][j] == 'g':
            board[i][j] = 2
            for dx, dy in di:
                nx, ny = i + dx, j + dy
                if 0<=nx<n and 0<=ny<m:
                    board[nx][ny] = max(board[nx][ny], 1)

dist = [[node(-1,-1)]*m for _ in range(n)]
#S와 F는 세지 않는다.
dist[st[0]][st[1]] = node(0, 0)

heap = []
heapq.heappush(heap, [dist[st[0]][st[1]], st[0], st[1]])


default_node = [node(0,0), node(0,1), node(1,0)]
while heap:
    now_dis, x, y = heapq.heappop(heap)
    if now_dis > dist[x][y]:
        continue

    for dx, dy in di:
        nx, ny = x + dx, y + dy
        if 0 <= nx < n and 0 <= ny < m:
            if now_dis + default_node[board[nx][ny]] < dist[nx][ny]:
                dist[nx][ny] = now_dis + default_node[board[nx][ny]]
                heapq.heappush(heap, [dist[nx][ny], nx, ny])

print(dist[ed[0]][ed[1]] - default_node[board[ed[0]][ed[1]]])