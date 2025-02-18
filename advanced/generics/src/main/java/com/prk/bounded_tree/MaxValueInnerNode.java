package com.prk.bounded_tree;

public class MaxValueInnerNode<T extends Comparable<T>> implements TreeNode<T> {

    private final TreeNode<T> left, right;

    public MaxValueInnerNode(TreeNode<T> left, TreeNode<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public T getValue() {
        T leftValue = left.getValue();
        T rightValue = right.getValue();
        var result = leftValue.compareTo(rightValue);
        return result >= 0 ? leftValue : rightValue;
    }

    @Override
    public TreeNode<T> getLeft() {
        return this.left;
    }

    @Override
    public TreeNode<T> getRight() {
        return this.right;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s}", left, right);
    }
}
