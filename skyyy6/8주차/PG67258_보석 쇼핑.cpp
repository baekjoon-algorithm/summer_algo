#include<iostream>
#include<map>
#include<vector>

using namespace std;

map<string, int> m;

vector<int> solution(vector<string> gems) {
    vector<int> answer;

    for (int i = 0; i < gems.size(); i++)
        m[gems[i]] = 0;
    int gem_size = m.size();

    answer.push_back(1);
    answer.push_back(gems.size());

    int lt = 0, rt = 0;
    m[gems[lt]]++;
    int cnt = 1;

    while (lt < gems.size()) {
        if (cnt < gem_size && rt != gems.size() - 1) {
            rt++;
            if (!m[gems[rt]]) cnt++;
            m[gems[rt]]++; 
        }
        else if (cnt == gem_size) {
            if (rt - lt < answer[1] - answer[0]) {
                answer[1] = rt + 1;
                answer[0] = lt + 1;
            }

            if (m[gems[lt]] == 1) cnt--;
            m[gems[lt]]--; lt++;
        }
        else break;
    }

    return answer;
}

int main() {
    vector<string> gems = { "DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA" };
    vector<int> res = solution(gems);
    cout << res[0] << ' ' << res[1];
}