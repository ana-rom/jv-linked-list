package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        MyLinkedList.Node<T> tail = last;
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(tail, value, null);
        last = newNode;
        if (size == 0) {
            first = newNode;
        } else {
            tail.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexElement(index);
        if (index == size) {
            add(value);
        } else {
            MyLinkedList.Node<T> node = getNode(index);
            MyLinkedList.Node<T> prevNode = node.prev;
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(prevNode, value, node);
            node.prev = newNode;
            if (prevNode == null) {
                first = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexPosition(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexPosition(index);
        MyLinkedList.Node<T> newNode = getNode(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexPosition(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        MyLinkedList.Node<T> node = first;
        while (node != null) {
            if (object == node.item || object != null && object.equals(node.item)) {
                unlink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T value, MyLinkedList.Node<T> next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndexElement(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Can't add new value. "
                    + "Index:" + index + ". Size: " + size);
        }
    }

    private void checkIndexPosition(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Out of bounds. "
                    + "Index:" + index + ". Size: " + size);
        }
    }

    private Node<T> getNode(int index) {
        MyLinkedList.Node<T> node;
        if (index < (size / 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        final T value = node.item;
        MyLinkedList.Node<T> prev = node.prev;
        MyLinkedList.Node<T> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return value;
    }
}
