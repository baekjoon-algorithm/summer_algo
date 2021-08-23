from collections import deque
di = [(0,1),(1,0),(0,-1),(-1,0)]


def check_board(board, visit, ret, n, x, y, opt):
# ret = 빈 공간을 담을 배열
    q = deque()
    q.append((x, y))
    visit[x][y] = True

    tmp = []
    while q:
        cx, cy = q.popleft()
        if opt == 1:
            tmp.append((cx - x, cy - y))
        else:
            tmp.append((cx, cy))

        for dx, dy in di:
            nx, ny = cx + dx, cy + dy
            if 0 <= nx < n and 0 <= ny < n:
                if not visit[nx][ny] and board[nx][ny] == opt:
                    visit[nx][ny] = True
                    q.append((nx, ny))
    ret.append(tmp)

def rotate(piece, opt):
    # opt 회전 각도 0 = 0도 1 90도 2 180도 3 270도
    ret = []
    for x, y in piece:
        if opt == 0:
            return piece
        elif opt == 1:
            ret.append((-y, x))
        elif opt == 2:
            ret.append((-x, -y))
        elif opt == 3:
            ret.append((y, -x))
    return ret

def solution(game_board, table):
    answer = 0
    n = len(game_board)

    empty_board = []
    visit_board = [[False] * n for _ in range(n)]
    for i in range(n):
        for j in range(n):
            if not visit_board[i][j] and game_board[i][j] == 0:
                check_board(game_board, visit_board, empty_board, n, i, j, 0)

    pieces = []
    visit_table = [[False] * n for _ in range(n)]
    for i in range(n):
        for j in range(n):
            if not visit_table[i][j] and table[i][j] == 1:
                check_board(table, visit_table, pieces, n, i, j, 1)


    use_piece = [False] * len(pieces)

    for blank in empty_board:
        for i in range(len(pieces)):
            if len(blank) != len(pieces[i]) or use_piece[i]:
                continue
            cnt = 0
            for j in range(4):
                candi = rotate(pieces[i], j)
                flag = True
                for cx, cy in blank:
                    ary = []
                    for dx, dy in candi:
                        nx, ny = cx + dx, cy + dy
                        if nx < 0 or nx >= n or ny < 0 or ny >=n or game_board[nx][ny] == 1:
                            flag = False
                            break
                        else:
                            game_board[nx][ny] = 1
                            ary.append((nx, ny))
                    if not flag:
                        flag = True
                        while ary:
                            cx, cy = ary.pop()
                            game_board[cx][cy] = 0
                        continue
                    else:
                        cnt += len(blank)
                        break
                if cnt != 0:
                    use_piece[i] = True
                    answer += cnt
                    break

    return answer