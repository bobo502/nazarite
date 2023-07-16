package com.nazarite.arithmetic;

// 定义节点类
class Node {
    int val, height;
    Node left, right;

    public Node(int val) {
        this.val = val;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

// 定义 AVL 树类
public class AVLTree {
    private Node root;

    public AVLTree() {
        root = null;
    }

    // 计算节点的高度
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    // 计算节点的平衡因子，左子树高度减去右子树高度
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }

        return getHeight(node.left) - getHeight(node.right);
    }

    // 对子树进行右旋
    private Node rightRotate(Node node) {
        Node newRoot = node.left;
        Node grandChild = newRoot.right;
        newRoot.right = node;
        node.left = grandChild;

        // 更新节点高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        return newRoot;
    }

    // 对子树进行左旋
    private Node leftRotate(Node node) {
        Node newRoot = node.right;
        Node grandChild = newRoot.left;
        newRoot.left = node;
        node.right = grandChild;

        // 更新节点高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        return newRoot;
    }

    // 插入节点到 AVL 树中
    public void insert(int val) {
        root = insertHelper(root, val);
    }

    // 递归搜索合适的插入位置，插入新节点并进行平衡调整
    private Node insertHelper(Node node, int val) {
        // 找到插入位置
        if (node == null) {
            return new Node(val);
        }

        // 插入到左子树
        if (val < node.val) {
            node.left = insertHelper(node.left, val);
        }
            // 插入到右子树
        else {
            node.right = insertHelper(node.right, val);
        }

        // 更新节点高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        // 计算平衡因子
        int balance = getBalanceFactor(node);

        // 左子树高度大于右子树
        if (balance > 1) {
            // 左左情况
            if (getBalanceFactor(node.left) >= 0) {
                return rightRotate(node);
            }

            // 左右情况
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // 右子树高度大于左子树
        if (balance < -1) {
            // 右右情况
            if (getBalanceFactor(node.right) <= 0) {
                return leftRotate(node);
            }
            // 右左情况
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // 在 AVL 树上查找一个值是否存在
    public boolean search(int val) {
        return searchHelper(root, val);
    }

    // 递归查找节点是否存在
    private boolean searchHelper(Node node, int val) {
        if (node == null) {
            return false;
        }

        if (node.val == val) {
            return true;
        } else if (node.val > val) {
            return searchHelper(node.left, val);
        } else {
            return searchHelper(node.right, val);
        }
    }

    // 在 AVL 树中删除一个节点
    public void delete(int val) {
        root = deleteHelper(root, val);
    }

    // 递归查找要删除的节点并进行删除和平衡调整
    private Node deleteHelper(Node node, int val) {
        if (node == null) {
            return node;
        }

        // 待删除节点在左子树
        if (node.val > val) {
            node.left = deleteHelper(node.left, val);
        }
            // 待删除节点在右子树
        else if (node.val < val) {
            node.right = deleteHelper(node.right, val);
        }
            // 找到了待删除节点
        else {
            // 没有左或右子节点
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            }
                // 有左右子节点
            else {
                Node maxLeftNode = node.left;
                while (maxLeftNode.right != null) {
                    maxLeftNode = maxLeftNode.right;
                }

                node.val = maxLeftNode.val;
                node.left = deleteHelper(node.left, maxLeftNode.val);
            }
        }

        // 如果只有一个节点
        if (node == null) {
            return node;
        }

        // 更新节点高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        // 计算平衡因子
        int balance = getBalanceFactor(node);

        // 左子树高度大于右子树
        if (balance > 1) {
            // 左左情况
            if (getBalanceFactor(node.left) >= 0) {
                return rightRotate(node);
            }
            // 左右情况
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // 右子树高度大于左子树
        if (balance < -1) {
            // 右右情况
            if (getBalanceFactor(node.right) <= 0) {
                return leftRotate(node);
            }
            // 右左情况
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
}
