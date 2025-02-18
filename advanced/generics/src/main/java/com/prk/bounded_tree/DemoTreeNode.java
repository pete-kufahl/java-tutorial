package com.prk.bounded_tree;

public class DemoTreeNode {
    public static void main(String[] args) {

        // compiler infers type argument
        var three = new LeafNode<>(3);
        var five = new LeafNode<>(5);

        var binTree = new MaxValueInnerNode<>(new LeafNode<>(2), new MaxValueInnerNode<>(three, five));
        System.out.println(binTree.getValue());
    }
}