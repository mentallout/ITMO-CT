#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

class FenwickTree {
    vector<long long> tree;
    int size;

public:
    explicit FenwickTree(const int n) : size(n) { tree.resize(n + 1, 0); }

    void update(int idx, const long long delta) {
        while (idx <= size) {
            tree[idx] += delta;
            idx += idx & -idx;
        }
    }

    [[nodiscard]] long long query(int idx) const {
        long long sum = 0;
        while (idx > 0) {
            sum += tree[idx];
            idx -= idx & -idx;
        }
        return sum;
    }
};

int main() {
    int n;
    cin >> n;
    vector<int> a(n);
    vector<int> sorted_a(n);
    for (int i = 0; i < n; i++) {
        cin >> a[i];
        sorted_a[i] = a[i];
    }
    sort(sorted_a.begin(), sorted_a.end());
    for (int i = 0; i < n; i++) {
        a[i] = lower_bound(sorted_a.begin(), sorted_a.end(), a[i]) - sorted_a.begin() + 1;
    }
    FenwickTree left_tree(n), right_tree(n);
    vector<long long> left(n), right(n);
    for (int i = 0; i < n; i++) {
        left[i] = left_tree.query(n) - left_tree.query(a[i]);
        left_tree.update(a[i], 1);
    }
    for (int i = n - 1; i >= 0; i--) {
        right[i] = right_tree.query(a[i] - 1);
        right_tree.update(a[i], 1);
    }
    long long weakness = 0;
    for (int i = 0; i < n; i++) {
        weakness += left[i] * right[i];
    }
    cout << weakness << endl;
    return 0;
}
