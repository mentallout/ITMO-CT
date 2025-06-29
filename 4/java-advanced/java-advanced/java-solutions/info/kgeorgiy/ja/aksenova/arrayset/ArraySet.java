package info.kgeorgiy.ja.aksenova.arrayset;

import java.util.*;

public class ArraySet<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {
    private final List<T> data;
    private final Comparator<T> comparator;
    private final Comparator<Object> reverseComparator = Collections.reverseOrder().reversed();

    public ArraySet() {
        this(List.of(), null);
    }

    public ArraySet(Collection<T> collection) {
        this(collection, null);
    }

    public ArraySet(Collection<T> collection, Comparator<T> comparator) {
        SortedSet<T> treeSet = new TreeSet<>(comparator);
        treeSet.addAll(collection);
        this.data = new ArrayList<>(treeSet);
        this.comparator = comparator;
    }

    public ArraySet(List<T> subset, Comparator<T> comparator) {
        this.data = new ArrayList<>(subset);
        this.comparator = comparator;
    }

    public Iterator<T> iterator() {
        return Collections.unmodifiableList(data).iterator();
    }

    public int size() {
        return data.size();
    }

    public Comparator<? super T> comparator() {
        return comparator;
    }

    @SuppressWarnings("unchecked")
    public boolean contains(Object element) {
        return Collections.binarySearch(data, (T) element, comparator) > -1;
    }

    private int getIndex(T element) {
        int index = Collections.binarySearch(data, element, comparator);
        if (index < 0) {
            return -index - 1;
        }
        return index;
    }

    public SortedSet<T> subSet(T fromElement, T toElement) {
        Comparator<? super T> cmp = comparator;
        if (comparator == null) {
            cmp = reverseComparator;
        }
        if (cmp.compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException("Error! From element > to element");
        }
        return new ArraySet<>(data.subList(getIndex(fromElement), getIndex(toElement)), comparator);
    }

    public SortedSet<T> headSet(T toElement) {
        if (getIndex(toElement) > 0) {
            return subSet(first(), toElement);
        }
        return new ArraySet<>(List.of(), comparator);
    }

    public SortedSet<T> tailSet(T fromElement) {
        return new ArraySet<>(data.subList(getIndex(fromElement), data.size()), comparator);
    }

    public T first() {
        return data.getFirst();
    }

    public T last() {
        return data.getLast();
    }
}
