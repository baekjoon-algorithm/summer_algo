#include<iostream>
#include<queue>
#include<string>

using namespace std;

struct GarbageState {
    int y, x, getGarbage, sideGarbage;

    bool operator < (const GarbageState& g) const{
        if(getGarbage == g.getGarbage) {
            return sideGarbage > g.sideGarbage;
        }
        return getGarbage > g.getGarbage;
    }
};

int N, M;
string arr[50];
bool visit[50][50][50][25];
GarbageState start;

int dy[] = {-1, 0, 1, 0};
int dx[] = {0, 1, 0, -1};

bool checkSide(int y, int x) {
    for (int i = 0; i < 4; i++) {
        int ny = y + dy[i];
        int nx = x + dx[i];

        if(ny < 0 || ny >= N || nx < 0 || nx >= M) {
            continue;
        }

        if(arr[ny][nx] == 'g') {
            return true;
        }
    }

    return false;
}

int main() {
    cin >> N >> M;
    for (int i = 0; i < N; i++) {
        cin >> arr[i];
        for (int j = 0; j < M; j++) {
            if(arr[i][j] == 'S') {
                start = {i, j, 0, 0};
            }
        }
    }

    priority_queue<GarbageState> pq;
    pq.push(start);
    visit[start.y][start.x][0][0] = true;

    while(!pq.empty()) {
        GarbageState now = pq.top();
        pq.pop();

        if(arr[now.y][now.x] == 'g') {
            now.getGarbage++;
        }

        if(arr[now.y][now.x] == '.' && checkSide(now.y, now.x)) {
            now.sideGarbage++;
        }

        if(arr[now.y][now.x] == 'F') {
            cout << now.getGarbage << ' ' << now.sideGarbage;
            break;
        }

        for (int i = 0; i < 4; i++) {
            int ny = now.y + dy[i];
            int nx = now.x + dx[i];

            if(ny < 0 || ny >= N || nx < 0 || nx >= M || visit[ny][nx][now.getGarbage][now.sideGarbage]) {
                continue;
            }

            visit[ny][nx][now.getGarbage][now.sideGarbage] = true;
            pq.push({ny, nx, now.getGarbage, now.sideGarbage});
        }
    }
}