#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int solution(int n, vector<int> stations, int w)
{
    int answer = 0;

    // �̹� ��ġ�Ǿ� �ִ� ���� Ž��
    vector<pair<int, int> > range;
    for (int num : stations) {
        range.push_back(make_pair(num - w, num + w));
    }

    int width = 2 * w + 1;

    int left = 1, right = -1;
    for (int i = 0; i < range.size(); ++i) {
        right = range[i].first;
        
        int blank = right - left;
        // �� ������ �ִ� ���
        if (blank > 0) {
            if (blank % width == 0) answer += blank / width;
            else answer += blank / width + 1;
        }
        left = range[i].second + 1;
    }

    // ������ �� ���� Ȯ��
    if (left <= n) {
        int blank = n - left + 1;
        if (blank % width == 0) answer += blank / width;
        else answer += blank / width + 1;
    }

    return answer;
}

int main() {
    cout << solution(10, {3}, 1) << endl;
}

/*
// ��� ��ġ �Լ�
void install(int num, int n, int w, vector<bool>& arr) {
    arr[num] = true;

    int count = 1;
    while (count <= w) {
        if (num - count > 0)
            arr[num - count] = true;
        if (num + count <= n)
            arr[num + count] = true;
        count++;
    }
}

int solution(int n, vector<int> stations, int w)
{
    int answer = 0;
    vector<bool> arr(n + 1, false);

    // ������ ����
    for (int num : stations) {
        install(num, n, w, arr);
    }

    for (int i = 1; i <= n - w; ++i) {
        if (!arr[i]) {
            int num = i + w;
            install(num, n, w, arr);
            answer++;
            i = num + w;
        }
    }
    
    // ������ ������ �ȴ�� ����Ʈ�� �ִ��� Ȯ�� 
    for (int i = n - w; i <= n; ++i) {
        if (!arr[i])
            answer++;
    }
    

    return answer;
}*/