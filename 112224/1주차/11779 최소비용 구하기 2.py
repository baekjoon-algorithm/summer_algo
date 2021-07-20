import sys
input = sys.stdin.readline
import heapq
n = int(input())
m = int(input())

adj = [[] for _ in range(n)]
dist = [-1] * n
prev = [-1] * n
for _ in range(m):
    st, ed, val = map(int, input().split())
    st, ed = st-1, ed-1
    adj[ed].append((st, val))

st, desti = map(int, input().split())
st, desti = st -1, desti -1
dist[desti] = 0
prev[desti] = desti
heap = [(0, desti)]
heapq.heapify(heap)

while heap:
    now_dis, now = heapq.heappop(heap)

    if now_dis > dist[now]:
        continue
    for ed, cost in adj[now]:
        if dist[ed] == -1 or dist[ed] > now_dis + cost:
            prev[ed] = now
            dist[ed] = now_dis + cost
            heapq.heappush(heap, (dist[ed], ed))

ans = []


def get_city(ans, city):
   ans.append(city)
   if city == prev[city]:
       return
   return get_city(ans, prev[city])


get_city(ans, st)

print(dist[st])
print(len(ans))
print(' '.join(map(str, [x+1 for x in ans])))