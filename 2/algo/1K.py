def merge(a1, a2, count):
    result = []
    p1 = 0
    p2 = 0
    while p1 < len(a1) and p2 < len(a2):
        if a1[p1] <= a2[p2]:
            result.append(a1[p1])
            p1 += 1
        else:
            count[0] += len(a1) - p1
            result.append(a2[p2])
            p2 += 1
    result.extend(a1[p1:])
    result.extend(a2[p2:])
    return result


def merge_sort(a, l, r, count):
    if l == r:
        return [a[l]]
    m = l + (r - l) // 2
    left = merge_sort(a, l, m, count)
    right = merge_sort(a, m + 1, r, count)
    return merge(left, right, count)


size = int(input())
mas = [int(x) for x in input().split()]
count = [0]
sorted_array = merge_sort(mas, 0, len(mas) - 1, count)
print(count[0])
