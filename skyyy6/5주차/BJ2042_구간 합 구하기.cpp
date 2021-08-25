#include<iostream>
#include<vector>

using namespace std;

typedef long long ll;

ll init(vector<ll>& tree, vector<ll>& arr, int node, int start, int end) {
	if (start == end) 
		return tree[node] = arr[start];

	int mid = (start + end) / 2;
	return tree[node] = init(tree, arr, node * 2, start, mid) + init(tree, arr, node * 2 + 1, mid + 1, end);
}

void update(vector<ll>& tree, int node, int start, int end, int index, ll diff) {
	if (!(start <= index && index <= end)) return;

	tree[node] += diff;

	if (start != end) {
		int mid = (start + end) / 2;
		update(tree, node * 2, start, mid, index, diff);
		update(tree, node * 2 + 1, mid + 1, end, index, diff);
	}
}
ll sum(vector<ll>& tree, int node, int start, int end, int left, int right) {
	if (left > end || right < start) return 0;

	if (left <= start && end <= right) return tree[node];

	int mid = (start + end) / 2;
	return sum(tree, node * 2, start, mid, left, right) + sum(tree, node * 2 + 1, mid + 1, end, left, right);
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);

	int N, M, K;
	cin >> N >> M >> K;

	vector<ll> arr(N);
	vector<ll> tree(N * 4);

	for (int i = 0; i < N; i++) {
		cin >> arr[i];
	}

	init(tree, arr, 1, 0, N - 1);

	for(int i = 0; i < M + K; i++) {
		int a, b;
		ll c;
		cin >> a >> b >> c;
		
		if (a == 1) {
			update(tree, 1, 0, N - 1, b - 1, c - arr[b - 1]);
			arr[b - 1] = c;
		}
		else if (a == 2)
			cout << sum(tree, 1, 0, N - 1, b - 1, c - 1) << '\n';
	}

	return 0;
}