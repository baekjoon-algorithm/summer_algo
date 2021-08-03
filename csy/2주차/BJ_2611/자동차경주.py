import heapq

n = int(input())
m = int(input())
n += 1

START_NODE = 1
END_NODE = n

graph = [[] for _ in range(n + 1)]
inbounds = [0] * (n + 1)

for _ in range(m):
    p, q, r = map(int, input().split(" "))
    if q == 1:
        q = n
    graph[p].append([q, r])
    inbounds[q] += 1

max_score = [0] * (n + 1)
prev = [0] * (n + 1)

h = []
heapq.heappush(h, [0, 1])
while len(h) > 0:
    _, cur_node = heapq.heappop(h)

    edges = graph[cur_node]
    while len(edges) > 0:
        q, r = edges.pop()
        value = max_score[cur_node] + r
        if value > max_score[q]:
            max_score[q] = value
            prev[q] = cur_node
        inbounds[q] -= 1

        if inbounds[q] <= 0:
            heapq.heappush(h, [inbounds[q], q])

paths = str(START_NODE)
node = n
while node != START_NODE:
    node = prev[node]
    paths = str(node) + " " + paths

print(max_score[n])
print(paths)
