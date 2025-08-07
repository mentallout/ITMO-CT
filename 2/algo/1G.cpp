#include <iostream>
#include <vector>
#include <cmath>
#include <limits>
#include <algorithm>

using namespace std;

class SegmentTreeMaxIndex {
public:
    explicit SegmentTreeMaxIndex(const vector<int> &arr) {
        n = arr.size();
        N = 1 << static_cast<int>(ceil(log2(n)));
        tree.resize(2 * N, make_pair(-numeric_limits<int>::max(), -1));
        build(arr);
    }

    [[nodiscard]] int max_index(int l, int r) const {
        l += N;
        r += N;
        pair<int, int> result = make_pair(-numeric_limits<int>::max(), -1);
        while (l < r) {
            if (l & 1) {
                result = max(result, tree[l], [](const pair<int, int> &a, const pair<int, int> &b) {
                    return a.first < b.first;
                });
                l++;
            }
            if (r & 1) {
                r--;
                result = max(result, tree[r], [](const pair<int, int> &a, const pair<int, int> &b) {
                    return a.first < b.first;
                });
            }
            l >>= 1;
            r >>= 1;
        }
        return result.second;
    }

private:
    size_t n;
    int N;
    vector<pair<int, int> > tree;

    void build(const vector<int> &arr) {
        for (int i = 0; i < n; ++i) {
            tree[N + i] = make_pair(arr[i], i);
        }
        for (int i = N - 1; i > 0; --i) {
            tree[i] = max(tree[i << 1], tree[(i << 1) | 1], [](const pair<int, int> &a, const pair<int, int> &b) {
                return a.first < b.first;
            });
        }
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int n;
    cin >> n;
    vector<int> arr(n);
    for (int i = 0; i < n; ++i) {
        cin >> arr[i];
    }
    int m;
    cin >> m;
    const SegmentTreeMaxIndex segment_tree(arr);
    vector<string> results;
    results.reserve(m);
    for (int i = 0; i < m; ++i) {
        int l, r;
        cin >> l >> r;
        results.push_back(to_string(segment_tree.max_index(l - 1, r) + 1));
    }
    for (const auto &result: results) {
        cout << result << "\n";
    }
    return 0;
}
