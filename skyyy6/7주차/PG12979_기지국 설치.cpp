#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

int solution(int n, vector<int> stations, int w) {
    int answer = 0;
    double interval = 2 * w + 1;
    int prev = 0;

    stations.push_back(n + w + 1);
    for(int station : stations) {
        double diff = (station - w) - prev - 1;
        if(diff > 0) {
            answer += ceil(diff / interval);
        }        

        prev = station + w;
    }

    return answer;
}

int main() {
    int n = 16;
    vector<int> stations = {9};
    int w = 2;
    cout << solution(n, stations, w);
}