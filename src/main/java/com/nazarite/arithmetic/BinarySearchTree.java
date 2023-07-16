package com.nazarite.arithmetic;

public class BinarySearchTree {
    // 定义节点类
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    // 定义二叉搜索树类
     static class BST {
        private TreeNode root;

        public BST() {
            root = null;
        }

        // 向二叉搜索树中插入一个节点
        public void insert(int val) {
            root = insertHelper(root, val);
        }

        // 递归查找节点的插入位置，并将新节点插入到合适的位置
        private TreeNode insertHelper(TreeNode node, int val) {
            if (node == null) {
                node = new TreeNode(val);
                return node;
            }
            if (val < node.val) {
                node.left = insertHelper(node.left, val);
            } else if (val > node.val) {
                node.right = insertHelper(node.right, val);
            }
            return node;
        }

        // 在二叉搜索树上查找某个值是否存在
        public boolean search(int val) {
            return searchHelper(root, val);
        }

        // 递归查找节点，如果存在返回true，否则返回false
        private boolean searchHelper(TreeNode node, int val) {
            if (node == null) {
                return false;
            }
            if (val == node.val) {
                return true;
            } else if (val < node.val) {
                return searchHelper(node.left, val);
            } else {
                return searchHelper(node.right, val);
            }
        }

        // 在二叉树中删除一个节点
        public void delete(int val) {
            root = deleteHelper(root, val);
        }

        // 递归查找并删除节点，返回新的二叉搜索树根节点
        private TreeNode deleteHelper(TreeNode node, int val) {
            if (node == null) {
                return node;
            }
            if (val < node.val) {
                node.left = deleteHelper(node.left, val);
            } else if (val > node.val) {
                node.right = deleteHelper(node.right, val);
            } else { // 找到要删除的节点
                // 情况1：没有子节点
                if (node.left == null && node.right == null) {
                    node = null;
                }
                // 情况2：有一个子节点
                else if (node.left == null || node.right == null) {
                    node = node.left != null ? node.left : node.right;
                }
                // 情况3：有两个子节点
                else {
                    TreeNode minNode = findMin(node.right);
                    node.val = minNode.val;
                    node.right = deleteHelper(node.right, minNode.val);
                }
            }
            return node;
        }

        // 递归查找最小值节点
        private TreeNode findMin(TreeNode node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
    }

    // 测试程序

        public static void main(String[] args) {
            BST bst = new BST();
            bst.insert(5);
            bst.insert(3);
            bst.insert(8);
            bst.insert(2);
            bst.insert(4);
            bst.insert(7);
            bst.insert(9);
            System.out.println(bst.search(3)); // true
            bst.delete(3);
            System.out.println(bst.search(3)); // false
        }

}
