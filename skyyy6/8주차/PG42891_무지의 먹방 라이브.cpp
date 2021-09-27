#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

struct Eating {
    int idx, amount;
    bool operator< (const Eating& e) const {
        if(amount == e.amount) {
            return idx > e.idx;
        }
        return amount > e.amount;
    }
};

int solution(vector<int> food_times, long long k) {
    int answer = 0;
    long long total = 0;
    priority_queue<Eating> pq;

    for (int i = 0; i < food_times.size(); i++) {
        pq.push({i + 1, food_times[i]});
        total += food_times[i];
    }

    if(total <= k) {
        return -1;
    }

    int prev = 0;
    while(!pq.empty()) {
        int minAmount = pq.top().amount;
        if(k < (minAmount - prev) * pq.size()) {
            break;
        }

        k -= (minAmount - prev) * pq.size();

        while(pq.top().amount == minAmount) {
            pq.pop();
        }
        prev = minAmount;
    }

    if(pq.empty()) {
        return -1;
    }

    vector<int> rest;
    while(!pq.empty()) {
        rest.push_back(pq.top().idx);
        pq.pop();
    }

    sort(rest.begin(), rest.end());
    int idx = k % rest.size();

    return rest[idx];
}

int main() {
    vector<int> food_times = {3, 1, 2};
    int k = 5;
    cout << solution(food_times, k);
}