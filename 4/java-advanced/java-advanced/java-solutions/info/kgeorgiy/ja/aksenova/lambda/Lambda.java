package info.kgeorgiy.ja.aksenova.lambda;

import info.kgeorgiy.java.advanced.lambda.EasyLambda;
import info.kgeorgiy.java.advanced.lambda.Trees;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class Lambda implements EasyLambda {

    // SPLITERATORS

    private static abstract class TreeSpliterator<T, N> implements Spliterator<T> {
        protected final Deque<N> childrenToVisit = new ArrayDeque<>();

        protected abstract T getLeafValue(N node);

        protected abstract void pushChildren(N node);

        protected abstract N getLeft(N node);

        protected abstract N getRight(N node);

        public TreeSpliterator(N root) {
            if (root != null) {
                childrenToVisit.push(root);
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            while (!childrenToVisit.isEmpty()) {
                N node = childrenToVisit.pop();
                if (node instanceof Trees.Leaf) {
                    action.accept(getLeafValue(node));
                    return true;
                }
                pushChildren(node);
            }
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            if (childrenToVisit.isEmpty()) {
                return null;
            }
            N node = childrenToVisit.pop();
            if (!(node instanceof Trees.Leaf)) {
                return new TreeSpliterator<>(node) {
                    @Override
                    public int characteristics() {
                        return TreeSpliterator.this.characteristics();
                    }

                    @Override
                    protected T getLeafValue(N node) {
                        return TreeSpliterator.this.getLeafValue(node);
                    }

                    @Override
                    protected N getLeft(N node) {
                        return TreeSpliterator.this.getLeft(node);
                    }

                    @Override
                    protected N getRight(N node) {
                        return TreeSpliterator.this.getRight(node);
                    }

                    @Override
                    protected void pushChildren(N node) {
                        TreeSpliterator.this.pushChildren(node);
                    }
                };
            }
            return null;
        }

        @Override
        public long estimateSize() {
            if (childrenToVisit.isEmpty()) {
                return 0;
            }
            if (childrenToVisit.peek() instanceof Trees.Leaf) {
                return 1;
            }
            return childrenToVisit.size();
        }

        @Override
        public int characteristics() {
            if (childrenToVisit.peekFirst() instanceof Trees.Leaf) {
                return IMMUTABLE | ORDERED | SIZED | SUBSIZED;
            } else {
                return IMMUTABLE | ORDERED;
            }
        }
    }

    @Override
    public <T> Spliterator<T> binaryTreeSpliterator(Trees.Binary<T> tree) {
        return new TreeSpliterator<>(tree) {

            @Override
            protected T getLeafValue(Trees.Binary<T> node) {
                return ((Trees.Leaf<T>) node).value();
            }

            @Override
            protected Trees.Binary<T> getLeft(Trees.Binary<T> node) {
                return ((Trees.Binary.Branch<T>) node).left();
            }

            @Override
            protected Trees.Binary<T> getRight(Trees.Binary<T> node) {
                return ((Trees.Binary.Branch<T>) node).right();
            }

            @Override
            protected void pushChildren(Trees.Binary<T> node) {
                if (node instanceof Trees.Binary.Branch<T>(Trees.Binary<T> left, Trees.Binary<T> right)) {
                    if (right != null) {
                        childrenToVisit.push(right);
                    }
                    if (left != null) {
                        childrenToVisit.push(left);
                    }
                }
            }
        };
    }

    @Override
    public <T> Spliterator<T> sizedBinaryTreeSpliterator(Trees.SizedBinary<T> tree) {
        return new TreeSpliterator<>(tree) {

            @Override
            protected T getLeafValue(Trees.SizedBinary<T> node) {
                return ((Trees.Leaf<T>) node).value();
            }

            @Override
            protected Trees.SizedBinary<T> getLeft(Trees.SizedBinary<T> node) {
                return ((Trees.SizedBinary.Branch<T>) node).left();
            }

            @Override
            protected Trees.SizedBinary<T> getRight(Trees.SizedBinary<T> node) {
                return ((Trees.SizedBinary.Branch<T>) node).right();
            }

            @Override
            protected void pushChildren(Trees.SizedBinary<T> node) {
                if (node instanceof Trees.SizedBinary.Branch<T> branch) {
                    if (branch.right() != null) {
                        childrenToVisit.push(branch.right());
                    }
                    if (branch.left() != null) {
                        childrenToVisit.push(branch.left());
                    }
                }
            }

            @Override
            public long estimateSize() {
                return tree.size();
            }

            @Override
            public int characteristics() {
                return IMMUTABLE | ORDERED | SIZED | SUBSIZED;
            }
        };
    }

    @Override
    public <T> Spliterator<T> naryTreeSpliterator(Trees.Nary<T> tree) {
        return new TreeSpliterator<>(tree) {

            @Override
            protected T getLeafValue(Trees.Nary<T> node) {
                return ((Trees.Leaf<T>) node).value();
            }

            @Override
            protected Trees.Nary<T> getLeft(Trees.Nary<T> node) {
                return null;
            }

            @Override
            protected Trees.Nary<T> getRight(Trees.Nary<T> node) {
                return null;
            }

            @Override
            protected void pushChildren(Trees.Nary<T> node) {
                if (node instanceof Trees.Nary.Node<T>(List<Trees.Nary<T>> children)) {
                    for (int i = children.size() - 1; i >= 0; i--) {
                        childrenToVisit.push(children.get(i));
                    }
                }
            }
        };
    }

    // COLLECTORS

    private <T> Collector<T, ?, Optional<T>> getElement(int order) {
        class Element {
            T value;
            int count = 0;
            final Deque<T> halves = new ArrayDeque<>();
        }

        return Collector.of(Element::new, (element, e) -> {
            if (order == 0) {
                if (element.value == null) {
                    element.value = e;
                }
            } else if (order == 1) {
                element.count++;
                element.halves.add(e);
                while (element.halves.size() > element.count / 2 + 1) {
                    element.halves.pop();
                }
                if (element.count % 2 != 1) {
                    element.halves.pop();
                }
                element.value = element.halves.peek();
            } else {
                element.value = e;
            }
        }, (e1, e2) -> e1, element -> Optional.ofNullable(element.value));
    }

    @Override
    public <T> Collector<T, ?, Optional<T>> first() {
        return getElement(0);
    }

    @Override
    public <T> Collector<T, ?, Optional<T>> middle() {
        return getElement(1);
    }

    @Override
    public <T> Collector<T, ?, Optional<T>> last() {
        return getElement(2);
    }

    private Collector<CharSequence, ?, String> getCommonSequence(boolean isSuffix) {
        class Element {
            boolean first = true;
            final StringBuilder sequence = new StringBuilder();
        }

        BiFunction<StringBuilder, CharSequence, Integer> calculateLCS = (seq, str) -> {
            int i = 0;
            int limit = Math.min(seq.length(), str.length());
            while (i < limit) {
                int seqI = i;
                int strI = i;
                if (isSuffix) {
                    seqI = seq.length() - 1 - seqI;
                    strI = str.length() - 1 - strI;
                }
                if (seq.charAt(seqI) != str.charAt(strI)) {
                    break;
                }
                i++;
            }
            return i;
        };

        return Collector.of(Element::new, (element, str) -> {
            if (element.first) {
                element.sequence.append(str);
                element.first = false;
            } else {
                int common = calculateLCS.apply(element.sequence, str);
                if (isSuffix) {
                    int len = element.sequence.length();
                    element.sequence.delete(0, len - common);
                } else {
                    element.sequence.setLength(common);
                }
            }
        }, (e1, e2) -> {
            int common = calculateLCS.apply(e1.sequence, e2.sequence);
            if (isSuffix) {
                int len = e1.sequence.length();
                e1.sequence.delete(0, len - common);
            } else {
                e1.sequence.setLength(common);
            }
            return e1;
        }, element -> element.sequence.toString());
    }

    @Override
    public Collector<CharSequence, ?, String> commonPrefix() {
        return getCommonSequence(false);
    }

    @Override
    public Collector<CharSequence, ?, String> commonSuffix() {
        return getCommonSequence(true);
    }
}