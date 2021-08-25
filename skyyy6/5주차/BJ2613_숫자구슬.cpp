#include <iostream>
#include <vector>

using namespace std;

int N, M, res, arr[300];

int main() {

    cin >> N >> M;

    int mid, lt = 0, rt = 0;
    for (int i = 0; i < N; i++) {
        cin >> arr[i];

        rt += arr[i];
        lt = lt > arr[i] ? lt : arr[i];
    }

    res = rt;

    while(lt <= rt) {
        mid = (lt + rt) / 2;
        int cnt = 0;
        int sum = 0;

        for (int i = 0; i < N; i++) {
            if(sum + arr[i] > mid) {
                cnt++;
                sum = 0;
            }
            sum += arr[i];
        }

        if(sum > 0) {
            cnt++;
        }

        if(cnt > M) {
            lt = mid + 1;
        } else {
            rt = mid - 1;
            res = res < mid ? res : mid;
        }
    }

    cout << res << '\n';

    int cnt = 0;
    int sum = 0;
    vector<int> mableCount;
    for (int i = 0; i < N; i++) {
        if(sum + arr[i] > res) {
            mableCount.push_back(cnt);
            cnt = 0;
            sum = 0;
        }
        cnt++;
        sum += arr[i];
    }

    if(sum > 0) {
        mableCount.push_back(cnt);
    }

    int idx = mableCount.size() - 1;
    while(mableCount.size() != M) {
        if(mableCount[idx] > 1) {
            mableCount[idx] -= 1;
            mableCount.push_back(1);
        } else {
            idx--;
        }
    }

    for (int i = 0; i < mableCount.size(); i++) {
        cout << mableCount[i] << ' ';
    }
}