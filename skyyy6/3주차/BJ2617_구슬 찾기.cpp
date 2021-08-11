#include<iostream>
#include<cstring>
#include<vector>

using namespace std;

int N, M, res;
bool bigv[100], smallv[100];
vector<int> big[100], small[100];

int big_DFS(int node) { // big 리스트용 DFS

	bigv[node] = true;
	int sum = 0;

	// 재귀를 통해 현 노드의 뒤에 구슬이 몇개있는지 리턴
	for (int i = 0; i < big[node].size(); i++) {
		int next = big[node][i];
		if (!bigv[next]) sum += big_DFS(next) + 1;
	}

	return sum;
}

int small_DFS(int node) { // small 리스트용 DFS

	smallv[node] = true;
	int sum = 0;

	// 재귀를 통해 현 노드의 뒤에 구슬이 몇개있는지 리턴
	for (int i = 0; i < small[node].size(); i++) {
		int next = small[node][i];
		if (!smallv[next])sum += small_DFS(next) + 1;
	}

	return sum;
}

int main() {
	cin >> N >> M;

	int s, e;
	int half = (N + 1) / 2;

	for (int i = 0; i < M; i++) {
		cin >> s >> e;
		// 구슬 s번이 e번 보다 무겁다
		// 무거운게 앞으로 가는 인접리스트와 뒤로 가는 인접리스트를 따로 만들어 저장

		big[s].push_back(e); // 무거운게 앞으로가는 리스트
		small[e].push_back(s); // 가벼운게 앞으로 가는 리스트
	}



	for (int i = 1; i <= N; i++) {

		// 방문 배열 초기화
		memset(smallv, false, sizeof(bigv));
		memset(bigv, false, sizeof(smallv));

		// i 번째 구슬로 두 DFS를 돌려서
		int b = big_DFS(i);
		int s = small_DFS(i);

		// 만약 이 구슬 뒤에 N/2 보다 많은 구슬이 있다면 절대 중간자리에 올 수 없음
		if (b >= half || s >= half) res++;
	}

	cout << res << '\n';
	return 0;
}
