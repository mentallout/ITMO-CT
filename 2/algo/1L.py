class SegmentTree:
    def __init__(self, array):
        self.N = len(array)
        self.n = 1
        while self.n < self.N:
            self.n *= 2
        self.treeAdd = [0] * (2 * self.n - 1)
        self.a = array + [0] * (self.n - self.N)
        self.build(0, 0, self.n)

    def build(self, v, l, r):
        if l + 1 == r:
            self.treeAdd[v] = 0
            return
        m = (l + r) // 2
        self.build(2 * v + 1, l, m)
        self.build(2 * v + 2, m, r)
        self.treeAdd[v] = 0

    def propagate(self, v):
        self.treeAdd[2 * v + 1] += self.treeAdd[v]
        self.treeAdd[2 * v + 2] += self.treeAdd[v]
        self.treeAdd[v] = 0

    def add(self, v, l, r, ql, qr, value):
        if l >= qr or ql >= r:
            return
        if ql <= l and r <= qr:
            self.treeAdd[v] += value
            return
        self.propagate(v)
        m = (l + r) // 2
        self.add(2 * v + 1, l, m, ql, qr, value)
        self.add(2 * v + 2, m, r, ql, qr, value)

    def get(self, v, l, r, index):
        if l + 1 == r:
            return self.a[l] + self.treeAdd[v]
        self.propagate(v)
        m = (l + r) // 2
        if index < m:
            return self.get(2 * v + 1, l, m, index)
        else:
            return self.get(2 * v + 2, m, r, index)

    def process_queries(self, queries):
        results = []
        for query in queries:
            if query[0] == 'g':
                index = int(query[1]) - 1
                results.append(self.get(0, 0, self.n, index))
            elif query[0] == 'a':
                ql = int(query[1]) - 1
                qr = int(query[2])
                value = int(query[3])
                self.add(0, 0, self.n, ql, qr, value)
        return results


n = int(input())
a = list(map(int, input().split()))
m = int(input())
queries = []
for _ in range(m):
    inp = input().split()
    if inp[0] == 'g':
        queries.append(('g', inp[1]))
    elif inp[0] == 'a':
        queries.append(('a', inp[1], inp[2], inp[3]))
segment_tree = SegmentTree(a)
results = segment_tree.process_queries(queries)
print("\n".join(map(str, results)))
