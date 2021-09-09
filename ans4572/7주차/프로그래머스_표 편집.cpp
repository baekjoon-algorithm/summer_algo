#include <string>
#include <vector>
#include <iostream>
#include <set>
#include <stack>

using namespace std;

string solution(int n, int k, vector<string> cmd) {
    string answer = "";
    for (int i = 0; i < n; ++i)
        answer.append("X");

    set<int> table;
    stack<int> st;     //삭제 시 저장할 스택

    for (int i = 0; i < n; ++i) {
        table.insert(i);
    }

    // 초기 행
    set<int>::iterator cursor = table.find(k);
    cout << *cursor << endl;

    for (string command : cmd) {
        if (command[0] == 'U') {
            int x = stoi(command.substr(2));
            while (x > 0) {
                cursor = prev(cursor);
                x--;
            }
        }
        else if (command[0] == 'D') {
            int x = stoi(command.substr(2));
            while (x > 0) {
                cursor = next(cursor);
                x--;
            }
        }
        else if (command[0] == 'C') {
            st.push(*cursor);
            cursor = table.erase(cursor);

            // 삭제한 것이 테이블 끝이였던 경우
            if (cursor == table.end())
                cursor = prev(cursor);
        }
        else {
            table.insert(st.top());
            st.pop();
        }
    }

    for (int i : table)  answer[i] = 'O';

    return answer;
}