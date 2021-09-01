#include <iostream>
#include <string>

using namespace std;

int dp[2500][2500];

int solution(string s)
{
    int answer = 1;
    
    for(int i = 0; i < s.length(); i++) {
        dp[i][i] = 1;
    }
    
    for(int i = 0; i < s.length() - 1; i++) {
        if(s[i] == s[i + 1]) {
            dp[i][i + 1] = 1;
            answer = 2;
        }
    }
    
    for(int len = 3; len <= s.length(); len++) {
        for(int i = 0; i <= s.length() - len; i++) {
            int j = i + len - 1;
            dp[i][j] = (s[i] == s[j]) && dp[i + 1][j - 1];
            if(dp[i][j]) {
                answer = len;
            }
        }
    }

    return answer;
}