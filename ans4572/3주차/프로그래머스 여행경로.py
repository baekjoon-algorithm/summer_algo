answer= []

def DFS(tickets, visit, route):
    global answer
    if len(route) == len(tickets) + 1:
        if not answer:
            answer = route.copy()
        if answer > route:
            answer = route.copy()
        return
    else:
        for idx, ticket in enumerate(tickets):
            if not visit[idx] and ticket[0] == route[-1]:
                visit[idx] = True
                route.append(ticket[1])
                DFS(tickets, visit, route)
                route.pop()
                visit[idx] = False

def solution(tickets):
    DFS(tickets, [False] * len(tickets), ['ICN'])

    return answer

print(solution([["ICN", "BOO"], ["ICN", "COO"], ["COO", "DOO"], ["DOO", "COO"], ["BOO", "DOO"], ["DOO", "BOO"], ["BOO", "ICN"], ["COO", "BOO"]]))