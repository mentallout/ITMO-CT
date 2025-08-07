class SegmentTree:
    def __init__(self, data):
        self.n = len(data)
        self.tree = [None] * (4 * self.n)
        self.build(data, 0, 0, self.n - 1)

    def build(self, data, node, start, end):
        if start == end:
            self.tree[node] = (data[start], 1)
        else:
            mid = (start + end) // 2
            left_child = 2 * node + 1
            right_child = 2 * node + 2
            self.build(data, left_child, start, mid)
            self.build(data, right_child, mid + 1, end)
            self.tree[node] = self.merge(self.tree[left_child], self.tree[right_child])

    def merge(self, left, right):
        if left[0] > right[0]:
            return left
        elif left[0] < right[0]:
            return right
        else:
            return left[0], left[1] + right[1]

    def query(self, l, r):
        return self._query(0, 0, self.n - 1, l, r)

    def _query(self, node, start, end, l, r):
        if r < start or end < l:
            return float('-inf'), 0
        if l <= start and end <= r:
            return self.tree[node]
        mid = (start + end) // 2
        left_child = 2 * node + 1
        right_child = 2 * node + 2
        left_result = self._query(left_child, start, mid, l, r)
        right_result = self._query(right_child, mid + 1, end, l, r)
        return self.merge(left_result, right_result)


N = int(input())
array = list(map(int, input().split()))
K = int(input())
queries = []
for i in range(K):
    l, r = map(int, input().split())
    queries.append((l - 1, r - 1))
seg_tree = SegmentTree(array)
for l, r in queries:
    max_val, count = seg_tree.query(l, r)
    print(max_val, count)
