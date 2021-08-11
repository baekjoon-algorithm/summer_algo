#include<iostream>
#include<vector>

using namespace std;

const int MAX = 30;

int N, M, dumbell[MAX + 1], ball[7];
int check[MAX + 1][MAX * 500 + 1];

void checkRecur(int cnt, int weight) {
    if(cnt > N)
        return;
    
    if(check[cnt][weight])
        return;

    check[cnt][weight] = true;

    checkRecur(cnt + 1, weight + dumbell[cnt]);
    checkRecur(cnt + 1, weight);
    checkRecur(cnt + 1, abs(weight - dumbell[cnt]));
}

int main() {
    
    cin >> N;
    for (int i = 0; i < N; i++) {
        cin >> dumbell[i];
    }

    cin >> M;
    for (int i = 0; i < M; i++) {
        cin >> ball[i];
    }

    checkRecur(0, 0);

    for (int i = 0; i < M; i++) {
        if(ball[i] > N * 500 || !check[N][ball[i]]) {
            cout << "N ";
        } else
            cout << "Y ";
    }
}