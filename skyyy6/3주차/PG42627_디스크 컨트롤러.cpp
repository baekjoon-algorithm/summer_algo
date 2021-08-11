#include<iostream>
#include <string>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

struct Job {
    int inputTime, takeTime;

    bool operator< (const Job& job) const {
        return takeTime > job.takeTime;
    }
};

int solution(vector<vector<int>> jobs) {
    int answer = 0;

    int time = 0;
    int pushCount = 0;
    priority_queue<Job> pq;

    sort(jobs.begin(), jobs.end());

    while(pushCount < jobs.size() || !pq.empty()) {
        while(pushCount < jobs.size() && jobs[pushCount][0] <= time) {
            pq.push({jobs[pushCount][0], jobs[pushCount][1]});
            pushCount++;
        }

        if(pq.empty()) {
            time = jobs[pushCount][0];
        } else {
            time += pq.top().takeTime;
            answer += time - pq.top().inputTime;
            pq.pop();
        }
    }

    answer /= jobs.size();

    return answer;
}

int main() {
    vector<vector<int>> jobs = {{0, 3}, {4, 4}, {5, 3}, {4, 1}};
    cout << solution(jobs);
}