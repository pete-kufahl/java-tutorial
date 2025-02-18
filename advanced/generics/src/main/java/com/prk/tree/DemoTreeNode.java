package com.prk.tree;

public class DemoTreeNode {
    public static void main(String[] args) {

        // compiler infers type argument
        var three = new LeafNode<>(3);
        var five = new LeafNode<>(5);

        var binTree = new InnerNode<>(new LeafNode<>(2), new InnerNode<Integer>(three, five));
        System.out.println(binTree);
    }
}