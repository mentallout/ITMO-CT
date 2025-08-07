#include "avl.h"

#include <algorithm>

size_t height(const node *n) {
    return n ? n->h : 0;
}

void update_height(node *n) {
    n->h = std::max(height(n->l), height(n->r)) + 1;
}

int balance_factor(const node *n) {
    return static_cast<int>(height(n->r) - height(n->l));
}

node *rotate_right(node *y) {
    node *x = y->l;
    y->l = x->r;
    x->r = y;
    update_height(y);
    update_height(x);
    return x;
}

node *rotate_left(node *x) {
    node *y = x->r;
    x->r = y->l;
    y->l = x;
    update_height(x);
    update_height(y);
    return y;
}

node *balance(node *n) {
    update_height(n);
    if (balance_factor(n) == 2) {
        if (balance_factor(n->r) < 0) {
            n->r = rotate_right(n->r);
        }
        return rotate_left(n);
    }
    if (balance_factor(n) == -2) {
        if (balance_factor(n->l) > 0) {
            n->l = rotate_left(n->l);
        }
        return rotate_right(n);
    }
    return n;
}

node *insert(node *root, const int key) {
    if (!root) {
        return new node{nullptr, nullptr, key, 1};
    }
    if (key < root->key) {
        root->l = insert(root->l, key);
    } else if (key > root->key) {
        root->r = insert(root->r, key);
    }
    return balance(root);
}

node *find_min(node *n) {
    return n->l ? find_min(n->l) : n;
}

node *remove_min(node *n) {
    if (n->l == nullptr) {
        return n->r;
    }
    n->l = remove_min(n->l);
    return balance(n);
}

node *remove(node *root, const int key) {
    if (!root)
        return nullptr;
    if (key < root->key) {
        root->l = remove(root->l, key);
    } else if (key > root->key) {
        root->r = remove(root->r, key);
    } else {
        node *l = root->l;
        node *r = root->r;
        delete root;
        if (!r)
            return l;
        node *min = find_min(r);
        min->r = remove_min(r);
        min->l = l;
        return balance(min);
    }
    return balance(root);
}

bool exists(const node *root, const int key) {
    if (!root)
        return false;
    if (key == root->key)
        return true;
    if (key < root->key)
        return exists(root->l, key);
    return exists(root->r, key);
}
