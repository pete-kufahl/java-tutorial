package com.prk.bounded_tree;

// restrict T to Comparable subtypes
public interface TreeNode<T extends Comparable<T>> {

    T getValue();

    TreeNode<T> getLeft();
    TreeNode<T> getRight();
}
