#include <unordered_map>

#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct Query {
    int l, r, idx;
};

bool compare(const Query &a, const Query &b) {
    return a.r < b.r;
}

class FenwickTree {
public:
    explicit FenwickTree(const int size) : tree(size + 1, 0) {
    }

    void update(int index, const int delta) {
        while (index < tree.size()) {
            tree[index] += delta;
            index += index & -index;
        }
    }

    [[nodiscard]] int query(int index) const {
        int sum = 0;
        while (index > 0) {
            sum += tree[index];
            index -= index & -index;
        }
        return sum;
    }

    [[nodiscard]] int query(const int left, const int right) const { return query(right) - query(left - 1); }

private:
    vector<int> tree;
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    int n;
    cin >> n;
    vector<int> array(n);
    for (int i = 0; i < n; i++) {
        cin >> array[i];
    }
    int q;
    cin >> q;
    vector<Query> queries(q);
    vector<int> answers(q);
    for (int i = 0; i < q; i++) {
        cin >> queries[i].l >> queries[i].r;
        queries[i].idx = i;
    }
    sort(queries.begin(), queries.end(), compare);
    FenwickTree fenwick(n);
    unordered_map<int, int> last_occurrence;
    int current_r = 0;
    for (const auto &[l, r, idx]: queries) {
        while (current_r < r) {
            current_r++;
            int element = array[current_r - 1];
            if (last_occurrence.find(element) != last_occurrence.end()) {
                fenwick.update(last_occurrence[element], -1);
            }
            last_occurrence[element] = current_r;
            fenwick.update(current_r, 1);
        }
        answers[idx] = fenwick.query(l, r);
    }
    for (const auto &answer: answers) {
        cout << answer << endl;
    }
    return 0;
}
