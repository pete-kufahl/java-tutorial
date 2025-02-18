package com.prk.bounded_tree;

public class LeafNode<T extends Comparable<T>> implements TreeNode<T> {

    private final T value;

    public LeafNode(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public TreeNode<T> getLeft() {
        return null;
    }

    @Override
    public TreeNode<T> getRight() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("[%s]", value);
    }
}
