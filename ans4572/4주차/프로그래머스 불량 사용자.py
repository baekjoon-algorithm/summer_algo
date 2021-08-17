def DFS(n, temp_ban, ban_list, ans_list):
    if n == len(ban_list):
        if temp_ban not in ans_list:
            return temp_ban

    for user in ban_list[n]:
        if user in temp_ban:
            continue
        else:
            temp_ban.append(user)
            temp = DFS(n + 1, temp_ban, ban_list, ans_list)
            if len(temp) == n:
                ans_list.append(temp)
            temp_ban.remove(user)

    return ans_list

def solution(user_id, banned_id):
    ban_list = []

    for ban in banned_id:
        temp_set = set()
        for user in user_id:
            isBan = True
            if len(user) == len(ban):
                # 각 인덱스 비교
                for idx in range(len(user)):
                    if ban[idx] != "*" and user[idx] != ban[idx]:
                        isBan = False
                        break

                if isBan:
                    temp_set.add(user)
        ban_list.append(temp_set)

    return len(DFS(0, [], ban_list, []))