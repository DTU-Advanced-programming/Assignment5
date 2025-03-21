package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Comparator;

public class ArrayList<E> implements List<E> {

    final private int DEFAULT_SIZE = 10;
    private int size = 0;
    private E[] list = createEmptyArray(DEFAULT_SIZE);

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public @NotNull E get(int pos) {
        return list[pos];
    }

    @Override
    public E set(int pos, @NotNull E e) {
        if (pos < 0 || pos >= size) throw new IndexOutOfBoundsException("The position doesn't exist");
        E helper = list[pos];
        list[pos] = e;
        return helper;
    }

    @Override
    public boolean add(@NotNull E e) {
        if (e == null) throw new IllegalArgumentException("No null values, good sir");
        if (size == list.length) expandArray();
        list[size++] = e;
        return true;
    }

    @Override
    public boolean add(int pos, @NotNull E e) {
        if (e == null) throw new UnsupportedOperationException("No null values, good sir");
        shiftElementsUpFrom(pos);
        list[pos] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int pos) {
        E helper = list[pos];
        shiftElementsDownTo(pos);
        size--;
        return helper;
    }

    @Override
    public boolean remove(E e) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(e)) {
                shiftElementsDownTo(i);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(e)) return i;
        }
        return -1;
    }

    @Override
    public void sort(@NotNull Comparator<? super E> c) {
        if (c == null) {throw new IllegalArgumentException("comparator cannot be null");}
        boolean swapped;
        int j = size;
        do {
            swapped = false;
            for (int i = 0; i+1<j; i++) {
                if (c.compare(list[i], list[i + 1]) > 0) {
                    list[i] = returnFirst(list[i + 1], list[i + 1] = list[i]);
                    swapped = true;
                }
            }
            j--;
        } while (swapped);

    }

    private E[] createEmptyArray(int length) {
        return (E[]) new Object[length];
    }

    private E returnFirst(E x, E y) {
        //use to swap x,y as returnFirst(x, x = y)
        return x;
    }

    private void shiftElementsUpFrom(int pos) {
        if (size == list.length) expandArray();
        for (int i = size; i > pos; i--) {
            list[i] = list[i - 1];
        }
    }

    private void shiftElementsDownTo(int pos) {
        for (int i = pos; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
    }

    private void expandArray() {
        list = Arrays.copyOf(list, list.length * 2);
    }
}
