#include<iostream>
#include<queue>
#include<vector>

using namespace std;

struct Edge {
    int e, c;
};

int N, M, degree[1001], pre[1001], dist[1001];
vector<Edge> adj[1001];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N >> M;

    int s, e, c;

    while(M--) {
        cin >> s >> e >> c;
        if(s == 1) {
            pre[e] = 1;
            dist[e] = c;
        } else {
            degree[e]++;
            adj[s].push_back({e, c});
        }
    }

    queue<int> q;
    for (int i = 1; i <= N; i++) {
        if(!degree[i]) {
            q.push(i);
        }
    }

    while(!q.empty()) {
        int now = q.front();
        q.pop();

        for(Edge next : adj[now]) {
            if(dist[next.e] < dist[now] + next.c) {
                pre[next.e] = now;
                dist[next.e] = dist[now] + next.c;
            }

            if(--degree[next.e] == 0) {
                q.push(next.e);
            }
        }
    }

    vector<int> res;
    int now = 1;
    res.push_back(now);

    while(true) {
        res.push_back(pre[now]);
        now = pre[now];
        if(now == 1)
            break;
    }

    cout << dist[1] << '\n';
    for (int i = res.size() - 1; i >= 0; i--) {
        cout << res[i] << ' ';
    }
}