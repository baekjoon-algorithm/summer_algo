#include <iostream>
#include <vector>
#include <queue>

using namespace std;

struct Edge {
    int e, c;

    bool operator< (const Edge& ed) const {
        return c > ed.c;
    }
};

struct Point {
    int y, x;
};

int N, M, res, arr[10][10];
bool visit[10][10];
int dy[] = {-1, 0, 1, 0};
int dx[] = {0, 1, 0, -1};

int islandCount;
vector<Point> island[7];
vector<Edge> adj[7];
bool visitIsland[7];

void getIsland(int i, int j) {
    islandCount++;
    
    queue<Point> q;
    q.push({i, j});
    visit[i][j] = true;

    while(!q.empty()) {
        int y = q.front().y;
        int x = q.front().x;

        island[islandCount].push_back(q.front());
        arr[y][x] = islandCount;

        q.pop();

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if(ny < 0 || nx < 0 || ny >= N || nx >= M || !arr[ny][nx] || visit[ny][nx]) {
                continue;
            }

            visit[ny][nx] = true;
            q.push({ny, nx});
        }
    }
}

void buildBridge(int id) {
    for(Point point : island[id]) {
        int y = point.y;
        int x = point.x;

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            int dist = 0;

            while(!(ny < 0 || nx < 0 || ny >= N || nx >= M)) {
                if(arr[ny][nx] != 0) {
                    if(arr[ny][nx] != id && dist > 1) {
                        int arrival = arr[ny][nx];
                        adj[id].push_back({arrival, dist});
                    }
                    break;
                }

                ny += dy[i];
                nx += dx[i];
                dist++;
            }
        }
    }
}

int main() {
    cin >> N >> M;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cin >> arr[i][j];
        }
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if(arr[i][j] && !visit[i][j]) {
                getIsland(i, j);
            }
        }
    }

    for (int i = 1; i <= islandCount; i++) {
        buildBridge(i);
    }

    int connectCount = 0;
    priority_queue<Edge> pq;
    pq.push({1, 0});

    while(!pq.empty()) {
        Edge now = pq.top();
        pq.pop();
        
        if(visitIsland[now.e]) {
            continue;
        }
        visitIsland[now.e] = true;

        res += now.c;
        if(++connectCount == islandCount) {
            cout << res;
            return 0;
        }

        for (Edge next : adj[now.e]) {
            if(!visitIsland[next.e]) {
                pq.push(next);
            }
        }
    }

    cout << -1;

    return 0;
}