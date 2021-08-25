#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>

using namespace std;

map<string, int> courseCount;
int sel[10];

string sortStr(string str) {
    vector<int> alaph(26, 0);
    string result = "";
    
    for(char c : str) {
        alaph[c - 'A']++;
    }
    
    for(int i = 0; i < 26; i++) {
        while(alaph[i]--) {
            result += ('A' + i);
        }
    }
    
    return result;
}

void Comb(int L, int idx, int size, string origin) {
    if(L == size) {
        string course = "";
        for(int i = 0; i < size; i++) {
            course += origin[sel[i]];
        }
        
        courseCount[course]++;
        return;
    }
    
    if(idx == origin.length()) {
        return;
    }
    
    sel[L] = idx;
    Comb(L + 1, idx+1, size, origin);
    Comb(L, idx + 1, size, origin);
}

vector<string> solution(vector<string> orders, vector<int> course) {
    vector<string> answer;
    
    for(int length : course) {
        for(string order : orders) {
            Comb(0, 0, length, sortStr(order));
        }
        
        int maxCount = 2;
        for(auto it = courseCount.begin(); it != courseCount.end(); it++) {
            maxCount = maxCount > it->second ? maxCount : it->second;
        }
        
        for(auto it = courseCount.begin(); it != courseCount.end(); it++) {
            if(it->second == maxCount) {
                answer.push_back(it->first);
            }
        }
        
        courseCount.clear();
    }
    
    sort(answer.begin(), answer.end());
    
    return answer;
}

int main() {
    vector<string> orders = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"};
    vector<int> course = {2, 3, 4};
    vector<string> result = solution(orders, course);

    for(string res : result) {
        cout << res << '\n';
    }
}