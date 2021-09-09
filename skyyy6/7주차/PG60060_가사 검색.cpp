#include <iostream>
#include <string>
#include <vector>
#include <cstring>
#include <algorithm>

using namespace std;

const int MAX = 10000 + 1;

struct Trie {
	Trie *children[26];
	bool isFinish;
	int count;

	Trie(): isFinish(false), count(0) {
		memset(children, 0, sizeof(children));
	}

	~Trie() {
		for (int i = 0; i < 26; i++) {
			if(children[i]) {
				delete children[i];
			}
		}
	}

	void insert(const char *key) {
		if(*key == '\0') {
			isFinish = true;
		} else {
			int idx = (*key) - 'a';
			if(children[idx] == NULL) {
				children[idx] = new Trie();
			}
			children[idx]->count++;
			children[idx]->insert(key + 1);
		}
	}

	int match(const char *key) {
		int idx = (*key) - 'a';
		
		if(*key == '?') {
			int cnt = 0;
			for (int i = 0; i < 26; i++) {
				if(children[i] != NULL) {
					cnt += children[i]->count;
				}
			}
			return cnt;
		}

		if(children[idx] == NULL)
			return 0;
		if(*(key + 1) == '?')
			return children[idx]->count;
		return children[idx]->match(key + 1);
	}
};

Trie* rt[MAX];
Trie* rrt[MAX];

vector<int> solution(vector<string> words, vector<string> queries) {
    vector<int> answer(queries.size(), 0);
	
	for(string word : words) {
		int len = word.length();
		if(rt[len] == NULL)
			rt[len] = new Trie();
		rt[len]->insert(word.c_str());

		reverse(word.begin(), word.end());
		if(rrt[len] == NULL) 
			rrt[len] = new Trie();
		rrt[len]->insert(word.c_str());
	}

	for (int i = 0; i < queries.size(); i++) {
		string query = queries[i];
		int len = query.length();

		if(rt[len] == NULL)
			continue;
		
		if(query[0] == '?') {
			reverse(query.begin(), query.end());
			answer[i] = rrt[len]->match(query.c_str());
		} else {
			answer[i] = rt[len]->match(query.c_str());
		}
	}

	return answer;
}

int main() {
	vector<string> words = {"frodo", "front", "frost", "frozen", "frame", "kakao"};
	vector<string> queries = {"fro??", "????o", "fr???", "fro???", "pro?"};
	vector<int> result = solution(words, queries);

	for(int res : result) {
		cout << res << ' ';
	}
}