import sys
import heapq

#입력
n = int(input())
m = int(input())
graph = [[] * n for _ in range(n+1)]
distance = [int(1e9)] * (n+1)
for _ in range(m):
    a,b,cost = map(int, sys.stdin.readline().split())
    graph[a].append((b, cost))
start, dest = map(int, sys.stdin.readline().split())

parent = [0] * (n+1)  # 최소 거리의 부모 노드

# 다익스트라 알고리즘
def dijkstra(start):
    q = []
    heapq.heappush(q, (0,start))
    distance[start] = 0
    while q:
        dist, now = heapq.heappop(q)
        if distance[now] < dist:
            continue
        for i in graph[now]:
            cost = dist + i[1]
            if cost < distance[i[0]]:
                parent[i[0]] = now
                distance[i[0]] = cost
                heapq.heappush(q, (cost, i[0]))

dijkstra(start)
print(distance[dest])  # 출발점 -> 도착점의 최소 비용

# 경로 탐색 진행
route = []
now = dest
while now != start:
    route.append(now)
    now = parent[now]
route.append(start)
print(len(route))     #경로 개수 출력

route.reverse()
# 경로를 역순으로 출력
print(" ".join(map(str,route)))
