s = input() + '$'
n = len(s)
suf = [0] * n
classes = [0] * n
pairs = sorted((s[i], i) for i in range(n))
for i in range(n):
    suf[i] = pairs[i][1]
classes[suf[0]] = 0
classes_cnt = 1
for i in range(1, n):
    if pairs[i][0] != pairs[i - 1][0]:
        classes_cnt += 1
    classes[suf[i]] = classes_cnt - 1
h = 0
while (1 << h) < n:
    second_element_permutation = [(suf[i] - (1 << h) + n) % n for i in range(n)]
    count = [0] * classes_cnt
    for x in second_element_permutation:
        count[classes[x]] += 1
    for i in range(1, classes_cnt):
        count[i] += count[i - 1]
    for x in second_element_permutation[::-1]:
        count[classes[x]] -= 1
        suf[count[classes[x]]] = x
    new_classes = [0] * n
    new_classes[suf[0]] = 0
    classes_cnt = 1
    for i in range(1, n):
        if classes[suf[i]] != classes[suf[i - 1]] or classes[(suf[i] + (1 << h)) % n] != classes[
            (suf[i - 1] + (1 << h)) % n]:
            classes_cnt += 1
        new_classes[suf[i]] = classes_cnt - 1
    classes = new_classes
    h += 1
print(*[x + 1 for x in suf[1:]])
