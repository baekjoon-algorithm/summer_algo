#include <string>
#include <vector>
#include <algorithm>

using namespace std;

vector<string> split(string str, string delimeter) {
    vector<string> result;
    int current = str.find(delimeter);
    int prev = 0;
    
    while(current != string::npos) {
        result.push_back(str.substr(prev, current - prev));
        prev = current + delimeter.length();
        current = str.find(delimeter, prev);
    }
    
    result.push_back(str.substr(prev, current - prev));
    
    return result;
}

string solution(vector<string> table, vector<string> languages, vector<int> preference) {
    vector<string> answer;
    
    int maxScore = 0;
    for(string tb : table) {
        vector<string> splitResult = split(tb, " ");
        string job = splitResult[0];
        
        int sum = 0;
        for(int i = 0; i < languages.size(); i++) {
            int idx = find(splitResult.begin(), splitResult.end(), languages[i]) - splitResult.begin();
            sum += (6 - idx) * preference[i];
        }
        
        if(sum >= maxScore) {
            if(sum > maxScore) {
                answer.clear();
            }
            maxScore = sum;
            answer.push_back(job);
        }
    }
    
    sort(answer.begin(), answer.end());
    
    return answer[0];
}