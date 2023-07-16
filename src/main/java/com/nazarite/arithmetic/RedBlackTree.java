package com.nazarite.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RedBlackTree<T extends Comparable<T>> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        T key;
        Node left;
        Node right;
        boolean color;

        public Node(T key, boolean color) {
            this.key = key;
            this.color = color;
        }
    }

    private Node root;

    public RedBlackTree() {
        root = null;
    }

    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color == RED;
    }

    // 左旋转
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // 右旋转
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // 颜色反转
    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public void put(T key) {
        root = put(root, key);
        root.color = BLACK;
    }

    private Node put(Node h, T key) {
        if (h == null) {
            return new Node(key, RED);
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = put(h.left, key);
        } else if (cmp > 0) {
            h.right = put(h.right, key);
        } else {
            // 如果 key 已经存在，则更新其值
            h.key = key;
        }

        // 平衡红黑树
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        return h;
    }

    public void inorderTraversal(){
        if(root == null){
            return;
        }

        Stack<Node> stack = new Stack<>();
        List<T> list = new ArrayList<>();

        Node current = root;

        while (current != null || !stack.isEmpty()){
            while (current != null){
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            list.add(current.key);

            current = current.right;
        }

        for (T t : list) {
            System.out.print(t + " ");
        }
    }

    public  void preorderTraversal(Node root){
        if(root == null){
            return;
        }
        System.out.println(root.key);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    public void postorderTraversal(Node root) {
        if (root == null) {
            return;
        }

        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.key + " ");
    }

    public void postorderTraversal(){
        postorderTraversal(root);
    }

    public void preorderTraversal(){
        preorderTraversal(root);
    }

    public boolean contains(T key) {
        return get(key) != null;
    }

    public T get(T key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x.key;
            }
        }
        return null;
    }
}

class TestTree{
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
        for (int i = 0; i < 100; i++) {
            tree.put(i);
        }
        tree.postorderTraversal();

    }
}
