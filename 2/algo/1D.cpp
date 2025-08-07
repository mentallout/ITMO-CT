#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct Node {
    int max_zeros, left_zeros, right_zeros;
    bool all_zeros;

    Node() : max_zeros(0), left_zeros(0), right_zeros(0), all_zeros(false) {
    }

    explicit Node(const int val) {
        if (val == 0) {
            max_zeros = left_zeros = right_zeros = 1;
            all_zeros = true;
        } else {
            max_zeros = left_zeros = right_zeros = 0;
            all_zeros = false;
        }
    }
};

class SegmentTreeZeros {
    int n, N;
    vector<Node> tree;

    static Node combine_nodes(const Node &left_node, const Node &right_node) {
        Node result;
        if (left_node.all_zeros && right_node.all_zeros) {
            result.max_zeros = result.left_zeros = result.right_zeros = left_node.max_zeros + right_node.max_zeros;
            result.all_zeros = true;
        } else if (left_node.all_zeros) {
            result.max_zeros = max(left_node.max_zeros + right_node.left_zeros, right_node.max_zeros);
            result.left_zeros = left_node.max_zeros + right_node.left_zeros;
            result.right_zeros = right_node.right_zeros;
            result.all_zeros = false;
        } else if (right_node.all_zeros) {
            result.max_zeros = max(left_node.right_zeros + right_node.max_zeros, left_node.max_zeros);
            result.left_zeros = left_node.left_zeros;
            result.right_zeros = left_node.right_zeros + right_node.max_zeros;
            result.all_zeros = false;
        } else {
            result.max_zeros = max({
                left_node.right_zeros + right_node.left_zeros, left_node.max_zeros, right_node.max_zeros
            });
            result.left_zeros = left_node.left_zeros;
            result.right_zeros = right_node.right_zeros;
            result.all_zeros = false;
        }
        return result;
    }

public:
    explicit SegmentTreeZeros(const vector<int> &arr) {
        n = static_cast<int>(arr.size());
        N = 1 << (32 - __builtin_clz(n - 1));
        tree.resize(2 * N);
        build(arr);
    }

    void build(const vector<int> &arr) {
        for (int i = 0; i < n; ++i) {
            tree[N + i] = Node(arr[i]);
        }
        for (int i = n; i < N; ++i) {
            tree[N + i] = Node(1);
        }
        for (int i = N - 1; i > 0; --i) {
            tree[i] = combine_nodes(tree[i << 1], tree[(i << 1) | 1]);
        }
    }

    void update(int pos, const int newval) {
        pos += N;
        tree[pos] = Node(newval);
        for (pos >>= 1; pos > 0; pos >>= 1) {
            tree[pos] = combine_nodes(tree[pos << 1], tree[(pos << 1) | 1]);
        }
    }

    [[nodiscard]] int max_consecutive_zeros(int l, int r) const {
        l += N;
        r += N;
        Node left_result, right_result;
        left_result.all_zeros = right_result.all_zeros = true;
        while (l < r) {
            if (l & 1) {
                left_result = combine_nodes(left_result, tree[l]);
                ++l;
            }
            if (r & 1) {
                --r;
                right_result = combine_nodes(tree[r], right_result);
            }
            l >>= 1;
            r >>= 1;
        }
        const Node result = combine_nodes(left_result, right_result);
        return result.max_zeros;
    }
};

int main() {
    int n, m;
    cin >> n;
    vector<int> arr(n);
    for (int i = 0; i < n; ++i) {
        cin >> arr[i];
    }
    SegmentTreeZeros segment_tree(arr);
    cin >> m;
    while (m-- > 0) {
        string command;
        int x, y;
        cin >> command >> x >> y;
        if (command == "QUERY") {
            cout << segment_tree.max_consecutive_zeros(x - 1, y) << endl;
        } else if (command == "UPDATE") {
            segment_tree.update(x - 1, y);
        }
    }
    return 0;
}
