#include <algorithm>
#include <iostream>
#include <set>
#include <vector>

using namespace std;

vector<int> graph[20000];
set<int> points;
bool visited[20000];
int tin[20000], fup[20000];
int cnt;

void dfs(const int v, const int parent = -1) {
    visited[v] = true;
    tin[v] = fup[v] = cnt++;
    int children = 0;
    for (const int to: graph[v]) {
        if (to == parent)
            continue;
        if (visited[to]) {
            fup[v] = min(fup[v], tin[to]);
        } else {
            dfs(to, v);
            fup[v] = min(fup[v], fup[to]);
            if (fup[to] >= tin[v] && parent != -1) {
                points.insert(v);
            }
            ++children;
        }
    }
    if (parent == -1 && children > 1) {
        points.insert(v);
    }
}

int main() {
    int n, m;
    cin >> n >> m;
    for (int i = 0; i < m; ++i) {
        int u, v;
        cin >> u >> v;
        graph[u].push_back(v);
        graph[v].push_back(u);
    }
    fill(visited, visited + n + 1, false);
    cnt = 0;
    for (int i = 1; i <= n; ++i) {
        if (!visited[i]) {
            dfs(i);
        }
    }
    cout << points.size() << "\n";
    for (const int point: points) {
        cout << point << " ";
    }
    cout << "\n";
    return 0;
}
