#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <cstring>
#include <queue>

#define y first
#define x second

using namespace std;

struct Block {
    // row, col, block 좌표들
    int r, c;
    vector<pair<int, int>> blocks;

    // == 비교용 operator
    bool operator == (const Block& b) const {
        if(r != b.r || c != b.c || blocks.size() != b.blocks.size()) {
            return false;
        }
        
        for (int i = 0; i < blocks.size(); i++) {
            if(blocks[i].y != b.blocks[i].y || blocks[i].x != b.blocks[i].x) {
                return false;
            }
        }
        return true;
    }
};

// emptyBlock -> 비어있는 부분 저장
// tableBlock -> 채울 블록 저장
vector<Block> emptyBlock, tableBlock;
bool visit[50][50], choose[2500];
int answer;

int dy[] = {-1, 0, 1, 0};
int dx[] = {0, 1, 0, -1};

// 회전 함수
Block rotateBlock(Block b, pair<int, int> comp) {
    vector<pair<int, int>> rotationBlock;
    for (int i = 0; i < b.blocks.size(); i++) {
        rotationBlock.push_back({b.blocks[i].x, b.c - b.blocks[i].y - 1});
    }

    // 회전 후 sort
    sort(rotationBlock.begin(), rotationBlock.end());

    // 비어있는 부분의 맨 첫번째 블록이랑 거리재서 위치 맞추기
    int diffY = rotationBlock[0].y - comp.y;
    int diffX = rotationBlock[0].x - comp.x;

    for (int i = 0; i < rotationBlock.size(); i++) {
        rotationBlock[i].y -= diffY;
        rotationBlock[i].x -= diffX;
    }

    return {b.c, b.r, rotationBlock};
}

// 블록 저장용 BFS
void BFS(int i, int j, int comp, vector<vector<int>> compBoard, vector<Block>& inputBlock) {
    queue<pair<int, int>> q;
    q.push({i, j});
    visit[i][j] = true;

    int maxY = i, minY = i;
    int maxX = j, minX = j;
    vector<pair<int, int>> blocks;
    blocks.push_back({i, j});

    while(!q.empty()) {
        int y = q.front().y;
        int x = q.front().x;
        q.pop();

        for (int d = 0; d < 4; d++) {
            int ny = y + dy[d];
            int nx = x + dx[d];

            if(ny < 0 || nx < 0 || ny >= compBoard.size() || nx >= compBoard.size() || visit[ny][nx] || compBoard[ny][nx] != comp) {
                continue;
            }

            q.push({ny, nx});
            blocks.push_back({ny, nx});
            visit[ny][nx] = true;

            maxY = maxY > ny ? maxY : ny;
            minY = minY < ny ? minY : ny;

            maxX = maxX > nx ? maxX : nx;
            minX = minX < nx ? minX : nx;
        }
    }

    int r = maxY - minY + 1;
    int c = maxX - minX + 1;

    sort(blocks.begin(), blocks.end());

    inputBlock.push_back({r, c, blocks});
}

void recur(int idx) {
    if(idx == emptyBlock.size()) {
        int count = 0;
        for (int i = 0; i < tableBlock.size(); i++) {
            if(choose[i]) {
                count += tableBlock[i].blocks.size();
            }
        }
        answer = answer > count ? answer : count;
        return;
    }

    for (int i = 0; i < tableBlock.size(); i++) {
        Block nowBlock = tableBlock[i];

        if(emptyBlock[idx].blocks.size() != nowBlock.blocks.size()) {
            continue;
        }

        Block rotation = nowBlock;
        for (int r = 0; r < 4; r++) {
            rotation = rotateBlock(rotation, emptyBlock[idx].blocks[0]);
            if(rotation == emptyBlock[idx]) {
                choose[i] = true;
                recur(idx + 1);
                choose[i] = false;
                break;
            }
        }
    }
    recur(idx + 1);
}

int solution(vector<vector<int>> game_board, vector<vector<int>> table) {
    for (int i = 0; i < game_board.size(); i++) {
        for (int j = 0; j < game_board.size(); j++) {
            if(!game_board[i][j] && !visit[i][j]) {
                BFS(i, j, false, game_board, emptyBlock);
            }
        }
    }

    memset(visit, false, sizeof(visit));

    for (int i = 0; i < table.size(); i++) {
        for (int j = 0; j < table.size(); j++) {
            if(table[i][j] && !visit[i][j]) {
                BFS(i, j, true, table, tableBlock);
            }
        }
    }

    recur(0);

    return answer;
}

int main() {
    vector<vector<int>> game_board = {{1, 1, 0, 0, 1, 0}, {0, 0, 1, 0, 1, 0}, {0, 1, 1, 0, 0, 1}, {1, 1, 0, 1, 1, 1}, {1, 0, 0, 0, 1, 0}, {0, 1, 1, 1, 0, 0}};
    vector<vector<int>> table = {{1, 0, 0, 1, 1, 0}, {1, 0, 1, 0, 1, 0}, {0, 1, 1, 0, 1, 1}, {0, 0, 1, 0, 0, 0}, {1, 1, 0, 1, 1, 0}, {0, 1, 0, 0, 0, 0}};
    cout << solution(game_board, table);
}