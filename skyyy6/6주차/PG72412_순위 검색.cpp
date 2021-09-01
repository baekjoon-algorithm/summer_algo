#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>

using namespace std;

map<string, char> code;
map<string, vector<int>> scoreRank;

vector<string> split(string str, string delimeter) {
    vector<string> result;
    int current = str.find(delimeter);
    int prev = 0;

    while(current != string::npos) {
        string sp = str.substr(prev, current - prev);
        if(sp != "and") {
            result.push_back(sp);
        }

        prev = current + delimeter.length();
        current = str.find(delimeter, prev);
    }

    result.push_back(str.substr(prev, current - prev));

    return result;
}

int getRankCount(int L, string op, int score) {
    if(L == op.length()) {
        vector<int> rank = scoreRank[op];
        int count = rank.size() - (lower_bound(rank.begin(), rank.end(), score) - rank.begin());
        return count;
    }
    
    if(op[L] != '-') {
        return getRankCount(L + 1, op, score);
    }

    int result = 0;
    for (int i = 0; i < 3; i++) {
        if(L != 0 && i == 2) {
            continue;
        }
        op[L] = i + '0';
        result += getRankCount(L + 1, op, score);
    }

    return result;
}

vector<int> solution(vector<string> info, vector<string> query) {
    vector<int> answer;

    vector<string> lang = {"cpp", "java", "python"};
    vector<string> job = {"backend", "frontend"};
    vector<string> career = {"junior", "senior"};
    vector<string> food = {"chicken", "pizza"};

    for (int i = 0; i < 3; i++) {
        code[lang[i]] = i + '0';
    }

    for (int i = 0; i < 2; i++) {
        code[job[i]] = i + '0';
        code[career[i]] = i + '0';
        code[food[i]] = i + '0';
    }

    for(string scoreInfo : info) {
        string option = "";
        vector<string> splitInfo = split(scoreInfo, " ");

        for (int i = 0; i < splitInfo.size() - 1; i++) {
            option += code[splitInfo[i]];
        }

        int score = atoi(splitInfo[splitInfo.size() - 1].c_str());
        scoreRank[option].push_back(score);
    }

    for (auto it = scoreRank.begin(); it != scoreRank.end(); it++) {
        vector<int>& rank = it->second;
        sort(rank.begin(), rank.end());
    }

    for(string qu : query) {
        string op = "";
        vector<string> splitResult = split(qu, " ");

        for (int i = 0; i < splitResult.size() - 1; i++) {
            if(splitResult[i] == "-") {
                op += "-";
            } else {
                op += code[splitResult[i]];
            }
        }

        int score = atoi(splitResult[splitResult.size() - 1].c_str());
        answer.push_back(getRankCount(0, op, score));
    }

    return answer;
}

int main() {
    vector<string> info = {"java backend junior pizza 150", "python frontend senior chicken 210", "python frontend senior chicken 150", "cpp backend senior pizza 260", "java backend junior chicken 80", "python backend senior chicken 50"};
    vector<string> query = {"java and backend and junior and pizza 100", "python and frontend and senior and chicken 200", "cpp and - and senior and pizza 250", "- and backend and senior and - 150", "- and - and - and chicken 100", "- and - and - and - 150"};
    vector<int> result = solution(info, query);

    for (int res : result) {
        cout << res << ' ';
    }
}