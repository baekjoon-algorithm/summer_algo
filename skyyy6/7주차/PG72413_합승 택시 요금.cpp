#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX = 200 * 100000;

int solution(int n, int s, int a, int b, vector<vector<int>> fares) {
    int answer = MAX;
    vector<vector<int>> dp(n + 1, vector<int>(n + 1, MAX));
    
    for(int i = 1; i <= n; i++){
        dp[i][i] = 0;
    }
    
    for(vector<int> fare : fares) {
        dp[fare[0]][fare[1]] = dp[fare[1]][fare[0]] = fare[2];
    }
    
    for(int k = 1; k <= n; k++) {
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                dp[i][j] = min(dp[i][j], dp[i][k] + dp[k][j]);
            }
        }
    }
    
    for(int i = 1; i <= n; i++) {
        int sum = dp[s][i] + dp[i][a] + dp[i][b];
        answer = min(answer, sum);
    }
    
    return answer;
}

int main() {
    int n = 6;
    int s = 4;
    int a = 6;
    int b = 2;
    vector<vector<int>> fares = {{4, 1, 10}, {3, 5, 24}, {5, 6, 2}, {3, 1, 41}, {5, 1, 24}, {4, 6, 50}, {2, 4, 66}, {2, 3, 22}, {1, 6, 25}};
    cout << solution(n, s, a, b, fares);
}