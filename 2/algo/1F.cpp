#include <iostream>
#include <vector>
using namespace std;

class FenwickTree {
public:
    explicit FenwickTree(const int n) : n(n), tree(n + 1, 0) {
    }

    void update(int i, const int delta) {
        while (i <= n) {
            tree[i] += delta;
            i += i & -i;
        }
    }

    [[nodiscard]] int query(int i) const {
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= i & -i;
        }
        return sum;
    }

    [[nodiscard]] int range_query(const int l, const int r) const {
        return query(r) - query(l - 1);
    }

private:
    int n;
    vector<int> tree;
};

int main() {
    int n;
    cin >> n;
    vector a(n + 1, 0);
    FenwickTree fenwickTree(n);
    for (int i = 1; i <= n; ++i) {
        cin >> a[i];
        const int sign = (i % 2 == 0) ? -1 : 1;
        fenwickTree.update(i, a[i] * sign);
    }
    int m;
    cin >> m;
    for (int k = 0; k < m; ++k) {
        int type, i, j;
        cin >> type >> i >> j;
        if (type == 0) {
            const int sign = (i % 2 == 0) ? -1 : 1;
            fenwickTree.update(i, (j - a[i]) * sign);
            a[i] = j;
        } else {
            const int l = i;
            const int r = j;
            int result = fenwickTree.range_query(l, r);
            if (l % 2 == 0) result = -result;
            cout << result << endl;
        }
    }
    return 0;
}
