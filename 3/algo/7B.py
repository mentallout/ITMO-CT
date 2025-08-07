class Matr:
    def __init__(self, n):
        self.parent = list(range(n))
        self.rank = [0] * n

    def find(self, u):
        if self.parent[u] != u:
            self.parent[u] = self.find(self.parent[u])
        return self.parent[u]

    def mnozh(self, u, v):
        u_root, v_root = self.find(u), self.find(v)
        if u_root != v_root:
            if self.rank[u_root] > self.rank[v_root]:
                self.parent[v_root] = u_root
            elif self.rank[u_root] < self.rank[v_root]:
                self.parent[u_root] = v_root
            else:
                self.parent[v_root] = u_root
                self.rank[u_root] += 1


n, m = map(int, input().split())
edges = []
for i in range(m):
    u, v, w = map(int, input().split())
    edges.append((u - 1, v - 1, w))
edges.sort(key=lambda x: x[2])
matr = Matr(n)
mst = 0
for u, v, w in edges:
    if matr.find(u) != matr.find(v):
        matr.mnozh(u, v)
        mst += w
print(mst)
