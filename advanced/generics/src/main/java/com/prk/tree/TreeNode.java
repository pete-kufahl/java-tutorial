package com.prk.tree;

public interface TreeNode<T> {

    T getValue();

    TreeNode<T> getLeft();
    TreeNode<T> getRight();
}
