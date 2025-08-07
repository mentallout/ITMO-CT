#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct SegmentTree {
    struct Node {
        long long sum, maxPrefix, maxSuffix, maxSubarray;

        explicit Node(const long long val = 0) : sum(val), maxPrefix(max(0LL, val)), maxSuffix(max(0LL, val)),
                                                 maxSubarray(max(0LL, val)) {
        }
    };

    vector<Node> tree;
    int n;

    explicit SegmentTree(const vector<int> &data) {
        n = static_cast<int>(data.size());
        tree.resize(4 * n);
        build(data, 1, 0, n - 1);
    }

    void build(const vector<int> &data, const int v, const int tl, const int tr) {
        if (tl == tr) {
            tree[v] = Node(data[tl]);
        } else {
            const int tm = (tl + tr) / 2;
            build(data, v * 2, tl, tm);
            build(data, v * 2 + 1, tm + 1, tr);
            tree[v] = merge(tree[v * 2], tree[v * 2 + 1]);
        }
    }

    static Node merge(const Node &left, const Node &right) {
        Node res;
        res.sum = left.sum + right.sum;
        res.maxPrefix = max(left.maxPrefix, left.sum + right.maxPrefix);
        res.maxSuffix = max(right.maxSuffix, right.sum + left.maxSuffix);
        res.maxSubarray = max({left.maxSubarray, right.maxSubarray, left.maxSuffix + right.maxPrefix});
        return res;
    }

    void update(const int pos, const int new_val, const int v, const int tl, const int tr) {
        if (tl == tr) {
            tree[v] = Node(new_val);
        } else {
            if (const int tm = (tl + tr) / 2; pos <= tm) {
                update(pos, new_val, v * 2, tl, tm);
            } else {
                update(pos, new_val, v * 2 + 1, tm + 1, tr);
            }
            tree[v] = merge(tree[v * 2], tree[v * 2 + 1]);
        }
    }

    void update(const int pos, const int new_val) {
        update(pos, new_val, 1, 0, n - 1);
    }

    Node query(const int l, const int r, const int v, const int tl, const int tr) {
        if (l > r) return Node();
        if (l == tl && r == tr) return tree[v];
        const int tm = (tl + tr) / 2;
        return merge(query(l, min(r, tm), v * 2, tl, tm), query(max(l, tm + 1), r, v * 2 + 1, tm + 1, tr));
    }

    long long query(const int l, const int r) {
        return query(l, r, 1, 0, n - 1).maxSubarray;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int n, m;
    cin >> n >> m;
    vector<int> data(n);
    for (int i = 0; i < n; ++i) {
        cin >> data[i];
    }
    SegmentTree st(data);
    for (int i = 0; i < m; ++i) {
        string type;
        cin >> type;
        if (type == "change") {
            int pos, val;
            cin >> pos >> val;
            st.update(pos - 1, val);
        } else if (type == "get") {
            int l, r;
            cin >> l >> r;
            cout << st.query(l - 1, r - 1) << '\n';
        }
    }
    return 0;
}
