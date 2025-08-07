import math


class SegmentTreeGCD:
    def __init__(self, arr):
        self.n = len(arr)
        self.N = 1 << (self.n - 1).bit_length()
        self.tree = [0] * (2 * self.N)
        self.build(arr)

    def build(self, arr):
        for i in range(self.n):
            self.tree[self.N + i] = arr[i]
        for i in range(self.N - 1, 0, -1):
            self.tree[i] = math.gcd(self.tree[i << 1], self.tree[(i << 1) | 1])

    def update(self, pos, newval):
        pos += self.N
        self.tree[pos] = newval
        pos >>= 1
        while pos > 0:
            self.tree[pos] = math.gcd(self.tree[pos << 1], self.tree[(pos << 1) | 1])
            pos >>= 1

    def gcd(self, l, r):
        l += self.N
        r += self.N
        result = 0
        while l < r:
            if l & 1:
                result = math.gcd(result, self.tree[l])
                l += 1
            if r & 1:
                r -= 1
                result = math.gcd(result, self.tree[r])
            l >>= 1
            r >>= 1
        return result


n = int(input())
arr = [int(x) for x in input().split()]
m = int(input())
segment_tree = SegmentTreeGCD(arr)
while m > 0:
    temp = input().split()
    if temp[0] == 's':
        print(segment_tree.gcd(int(temp[1]) - 1, int(temp[2])), end=" ")
    else:
        segment_tree.update(int(temp[1]) - 1, int(temp[2]))
    m -= 1
