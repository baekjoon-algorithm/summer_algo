#include <iostream>
#include <string>
#include <vector>
#include <set>
#include <stack>

using namespace std;

string solution(int n, int k, vector<string> cmd) {
    string answer = "";
    
    set<int> arr;
    stack<int> trash;

    for (int i = 0; i < n; i++) {
        arr.insert(i);
        answer += 'X';
    }

    auto cur = arr.find(k);

    for(string c : cmd) {
        char command = c[0];
        if(c.length() > 1) {
            int count = atoi(c.substr(2, c.length() - 2).c_str());
            while(count--) {
                if(command == 'U')
                    cur = prev(cur);
                else
                    cur = next(cur);
            }
        } else if(command == 'C') {
            trash.push(*cur);
            cur = arr.erase(cur);
            if(cur == arr.end()) {
                cur = prev(cur);
            }
        } else {
            arr.insert(trash.top());
            trash.pop();
        }
    }

    for(int i : arr) {
        answer[i] = 'O';
    }

    return answer;
}

int main() {
    int n = 8;
    int k = 2;
    vector<string> cmd = {"D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z"};
    cout << solution(n, k, cmd);
}