#include <iostream>
#include <string>
#include <vector>
#include <queue>

using namespace std;

vector<int> adj[20001];
bool visit[20001];

int solution(int n, vector<vector<int>> edge) {
    int answer = 0;

    for(vector<int> ed : edge) {
        adj[ed[0]].push_back(ed[1]);
        adj[ed[1]].push_back(ed[0]);
    }

    queue<int> q;
    q.push(1);
    visit[1] = true;

    while(!q.empty()) {
        int qsize = q.size();
        answer = 0;
        
        while(qsize--) {
            int now = q.front();
            q.pop();
            answer++;

            for(int next : adj[now]) {
                if(visit[next]) {
                    continue;
                }

                visit[next] = true;
                q.push(next);
            }
        }
    }

    return answer;
}

int main() {
    int n = 6;
    vector<vector<int>> vertex = {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}};

    cout << solution(n, vertex);
}