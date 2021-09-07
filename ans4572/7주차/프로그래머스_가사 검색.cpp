#include <string>
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

bool cmp(string a, string b) {
    if (a.length() < b.length()) {
        return true;
    }
    else if (a.length() == b.length()) {
        if (a < b) return true;
    }
    return false;
}

vector<int> solution(vector<string> words, vector<string> queries) {
    vector<int> answer;
    
    // words 문자열들을 뒤집어서 저장 
    int size = words.size();
    vector<string> rwords = words;
    for (int i = 0; i < size; ++i) {
        reverse(rwords[i].begin(),rwords[i].end());
    }

    sort(words.begin(), words.end(), cmp);
    sort(rwords.begin(), rwords.end(), cmp);

    int lo, up;
    for (string query : queries) {
        int len = query.length();
        
        if (query[0] == '?') {
            reverse(query.begin(), query.end());
            
            int idx = query.find_first_of('?');  // 가장 처음 '?' 발견 인덱스 찾기
            for (int i = idx; i < query.length(); ++i)
                query[i] = 'a';
            lo = lower_bound(rwords.begin(), rwords.end(), query, cmp) - rwords.begin();

            for (int i = idx; i < query.length(); ++i)
                query[i] = 'z';
            up = upper_bound(rwords.begin(), rwords.end(), query, cmp) - rwords.begin();
        }
        else {
            int idx = query.find_first_of('?');  // 가장 처음 '?' 발견 인덱스 찾기
            for (int i = idx; i < query.length(); ++i)
                query[i] = 'a';
            lo = lower_bound(words.begin(), words.end(), query, cmp) - words.begin();

            for (int i = idx; i < query.length(); ++i)
                query[i] = 'z';
            up = upper_bound(words.begin(), words.end(), query, cmp) - words.begin();
        }

        answer.push_back(up - lo);
    }

    return answer;
}

int main() {
    vector<int> ans = solution({ "frodo", "front", "frost", "frozen", "frame", "kakao" }, { "fro??", "????o", "fr???", "fro???", "pro?" });
    for (int i = 0; i < ans.size(); ++i) {
        cout << ans[i] << ", ";
    }
    cout << endl;
}