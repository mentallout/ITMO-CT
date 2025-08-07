import bisect


def get(v, l, r, ql, qr, x, tree):
    if qr <= l or r <= ql:
        return 0
    if ql <= l and r <= qr:
        return len(tree[v]) - bisect.bisect_right(tree[v], x)
    m = (l + r) // 2
    count_left = get(2 * v + 1, l, m, ql, qr, x, tree)
    count_right = get(2 * v + 2, m, r, ql, qr, x, tree)
    return count_left + count_right


def merge(first, second):
    return sorted(first + second)


def build(v, l, r, tree, pref_sum, n):
    if l + 1 == r:
        tree[v] = [pref_sum[l]] if l < n + 1 else [float('-inf')]
        return
    m = (l + r) // 2
    build(2 * v + 1, l, m, tree, pref_sum, n)
    build(2 * v + 2, m, r, tree, pref_sum, n)
    tree[v] = merge(tree[2 * v + 1], tree[2 * v + 2])


def power(x):
    a = 1
    power = 0
    while a < x:
        a *= 2
        power += 1
    return power


n, t = map(int, input().split())
a = list(map(int, input().split()))
pref_sum = [0] * (n + 1)
summa = 0
for i in range(n):
    summa += a[i]
    pref_sum[i + 1] = summa
tree_len = 2 ** power(n + 1)
tree = [[] for _ in range(2 * tree_len - 1)]
build(0, 0, tree_len, tree, pref_sum, n)
answer = 0
for i in range(n + 1):
    diff = pref_sum[i] - t
    answer += get(0, 0, tree_len, 0, i, diff, tree)
print(answer)
