#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

vector<string> answer;
bool visit[100001];

bool recur(int L, string now, vector<vector<string>> tickets) {
    answer.push_back(now);
    if(L == tickets.size()) {
        return true;
    }

    for (int i = 0; i < tickets.size(); i++) {
        if(tickets[i][0] == now && !visit[i]) {
            visit[i] = true;
            bool result = recur(L + 1, tickets[i][1], tickets);
            visit[i] = false;
            if(result)
                return result;
        }
    }

    answer.pop_back();
    return false;
}

vector<string> solution(vector<vector<string>> tickets) {
    sort(tickets.begin(), tickets.end());
    recur(0, "ICN", tickets);
    return answer;
}

int main() {
    vector<vector<string>> tickets = {{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL", "SFO"}};
    vector<string> result = solution(tickets);

    for(string res : result) {
        cout << res << ' ';
    }
}
