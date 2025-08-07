#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

string s;
vector q(21, vector<int>(1000000));

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    getline(cin, s);
    const int l = static_cast<int>(s.length());
    for (int i = 0; i < l; i++) {
        q[0][i + 1] = q[0][i] + (s[i] == '(' ? 1 : -1);
    }
    for (int j = 0; j < 20; j++) {
        for (int i = 0; i <= l; i++) {
            q[j + 1][i] = min(q[j][i], q[j][min(i + (1 << j), l)]);
        }
    }
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        int L, R;
        cin >> L >> R;
        L--;
        int j = 0;
        while ((1 << (j + 1)) <= R - L) {
            j++;
        }
        const int qq = min(q[j][L], q[j][R - (1 << j) + 1]);
        cout << R - L - (q[0][L] + q[0][R] - 2 * qq) << endl;
    }
    return 0;
}
