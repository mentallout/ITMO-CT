#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

class SegmentTree {
public:
    explicit SegmentTree(const int n) : n(n), data(4 * n), lazy(4 * n, 0) {
    }

    void build(const vector<int> &arr, const int v, const int tl, const int tr) {
        if (tl == tr) {
            data[v] = arr[tl];
        } else {
            const int tm = (tl + tr) / 2;
            build(arr, v * 2, tl, tm);
            build(arr, v * 2 + 1, tm + 1, tr);
            data[v] = max(data[v * 2], data[v * 2 + 1]);
        }
    }

    void update(const int l, const int r, const int add, const int v, const int tl, const int tr) {
        if (l > r)
            return;
        if (l == tl && r == tr) {
            lazy[v] += add;
            data[v] += add;
        } else {
            push(v);
            const int tm = (tl + tr) / 2;
            update(l, min(r, tm), add, v * 2, tl, tm);
            update(max(l, tm + 1), r, add, v * 2 + 1, tm + 1, tr);
            data[v] = max(data[v * 2], data[v * 2 + 1]);
        }
    }

    int query(const int l, const int r, const int v, const int tl, const int tr) {
        if (l > r)
            return -1;
        if (l <= tl && tr <= r)
            return data[v];
        push(v);
        const int tm = (tl + tr) / 2;
        return max(query(l, min(r, tm), v * 2, tl, tm), query(max(l, tm + 1), r, v * 2 + 1, tm + 1, tr));
    }

private:
    void push(const int v) {
        if (lazy[v] != 0) {
            data[v * 2] += lazy[v];
            lazy[v * 2] += lazy[v];
            data[v * 2 + 1] += lazy[v];
            lazy[v * 2 + 1] += lazy[v];
            lazy[v] = 0;
        }
    }

    int n;
    vector<int> data, lazy;
};

int main() {
    int n;
    cin >> n;
    vector<int> arr(n);
    for (int i = 0; i < n; ++i)
        cin >> arr[i];
    SegmentTree segTree(n);
    segTree.build(arr, 1, 0, n - 1);
    int m;
    cin >> m;
    for (int i = 0; i < m; ++i) {
        char type;
        cin >> type;
        if (type == 'a') {
            int l, r, add;
            cin >> l >> r >> add;
            segTree.update(l - 1, r - 1, add, 1, 0, n - 1);
        } else if (type == 'm') {
            int l, r;
            cin >> l >> r;
            cout << segTree.query(l - 1, r - 1, 1, 0, n - 1) << " ";
        }
    }
    return 0;
}
