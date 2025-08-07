#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

class FenwickTree {
public:
    explicit FenwickTree(const int size) : data(size + 1, 0) {
    }

    void update(int index, const int value) {
        for (; index < data.size(); index += index & -index) {
            data[index] += value;
        }
    }

    [[nodiscard]] int query(int index) const {
        int sum = 0;
        for (; index > 0; index -= index & -index) {
            sum += data[index];
        }
        return sum;
    }

private:
    vector<int> data;
};

int main() {
    int N;
    cin >> N;
    vector<pair<int, int> > stars(N);
    for (int i = 0; i < N; ++i) {
        cin >> stars[i].first >> stars[i].second;
    }
    int maxX = 0;
    for (const auto &[fst, snd]: stars) {
        if (fst > maxX) {
            maxX = fst;
        }
    }
    FenwickTree fenwick(maxX + 1);
    vector levels(N, 0);
    for (const auto &[fst, snd]: stars) {
        const int x = fst;
        const int level = fenwick.query(x + 1);
        levels[level]++;
        fenwick.update(x + 1, 1);
    }
    for (int i = 0; i < N; ++i) {
        cout << levels[i] << endl;
    }
    return 0;
}
