def dfs(v, visited):
    if visited[v]:
        return False
    visited[v] = True
    for to in adjacency_list[v]:
        if right[to] == 0 or dfs(right[to], visited):
            right[to] = v
            left[v] = to
            return True
    return False


def unique_id(s):
    result = 0
    for i, char in enumerate(s + "\0" * (4 - len(s))):
        if char == "\0":
            digit = 0
        else:
            digit = ord(char) - ord('a') + 1
        result += digit * (27 ** (3 - i))
    return result


def get_string(id):
    result = ''
    for i in range(4):
        digit = id // (27 ** (3 - i))
        if digit == 0:
            break
        else:
            result += chr(digit - 1 + ord('a'))
        id %= (27 ** (3 - i))
    return result


f = open('input.txt', "r")
n = int(f.readline())
words = [''] * n
for i in range(n):
    words[i] = f.readline().strip()
f.close()
adjacency_list = [[] for _ in range(n + 1)]
shorts = set()
for i, word in enumerate(words):
    if len(word) <= 4:
        adjacency_list[i + 1].append(unique_id(word))
        shorts.add(unique_id(word))
    subsequences = set()
    for x in range(1, 1 << len(word)):
        subs = ''
        for j in range(len(word)):
            if x & (1 << j):
                subs += word[j]
        if 1 <= len(subs) <= 4:
            subsequences.add(subs)
    for subs in subsequences:
        adjacency_list[i + 1].append(unique_id(subs))
        shorts.add(unique_id(subs))
left = [0] * (n + 1)
right = [0] * ((max(shorts) if shorts else 0) + 1)
for v in range(1, n + 1):
    visited = [False] * (n + 1)
    dfs(v, visited)
result = []
f = open('output.txt', "w")
for i in range(1, n + 1):
    if left[i] == 0:
        f.write('-1')
        f.close()
        exit(0)
    result.append(get_string(left[i]))
for word in result:
    f.write(str(word) + "\n")
f.close()
