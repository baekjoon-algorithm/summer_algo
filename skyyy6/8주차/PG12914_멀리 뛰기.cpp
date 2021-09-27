#include <iostream>
#include <string>
#include <vector>

using namespace std;

const int MOD = 1234567;

long long dp[2001];

long long solution(int n) {
    if(n < 0) {
        return 0;
    } else if (n == 0) {
        return 1;
    }

    if(dp[n]) {
        return dp[n];
    }
    
    return dp[n] = (solution(n - 1) + solution(n - 2)) % MOD;
}

int main() {
    int n = 3;
    cout << solution(n);
}