#include<iostream>
#include<algorithm>
#include<vector>
#include<queue>

using namespace std;

int main(){
    int N;
    cin>>N;
    
    vector<pair<int, int> > homework;

    int d,w;
    for(int i=0;i<N;++i){
        cin>>d>>w;
        homework.push_back(make_pair(d,w));
    }
    
    sort(homework.begin(), homework.end());
    reverse(homework.begin(), homework.end());

    priority_queue<int> pq;

    int ans = 0;
    int day = homework[0].first;
    int index = 0;
    while (day >= 1) {
        for (int i = index; i < N; ++i, ++index) {
            if (homework[i].first == day) {
                pq.push(homework[i].second);
            }
            else 
                break;
        }

        if (!pq.empty()) {
            ans += pq.top();
            pq.pop();
        }

        day--;
    }

    cout << ans << endl;

    return 0;
}