package com.nazarite.arithmetic;

// 定义节点类
class TreeNode {
    int[] key; // 关键字
    int keyNum; // 关键字个数
    int level; // 节点的层级，0表示叶子节点
    TreeNode[] child; // 子节点

    public TreeNode(int level) {
        this.key = new int[3];
        this.keyNum = 0;
        this.level = level;
        this.child = new TreeNode[4];
    }

    // 判断节点是否已满
    public boolean isFull() {
        return (keyNum == 3);
    }

    // 在当前节点中按顺序插入关键字
    public void insertKey(int val) {
        int i = keyNum - 1;
        while (i >= 0 && key[i] > val) {
            key[i + 1] = key[i];
            i--;
        }
        key[i + 1] = val;
        keyNum++;
    }

    // 在当前节点中按顺序删除关键字
    public void deleteKey(int val) {
        int i = 0;
        while (i < keyNum && key[i] != val) {
            i++;
        }
        if (i == keyNum) {
            return;
        }
        while (i < keyNum - 1) {
            key[i] = key[i + 1];
            i++;
        }
        keyNum--;
    }

    // 在当前节点中查找关键字的位置
    public int findKeyPosition(int val) {
        int i = 0;
        while (i < keyNum && key[i] < val) {
            i++;
        }
        return i;
    }
}

// 定义 B+ 树类
public class BPlusTree {
    private TreeNode root;

    public BPlusTree() {
        root = new TreeNode(0);
    }

    // 在 B+ 树中查找一个值是否存在
    public boolean search(int val) {
        TreeNode node = findLeafNode(val, root);
        if (node == null) {
            return false;
        }
        int pos = node.findKeyPosition(val);
        return (pos < node.keyNum && node.key[pos] == val);
    }

    // 在 B+ 树上插入一个值
    public void insert(int val) {
        // 找到要插入的叶子节点
        TreeNode node = findLeafNode(val, root);
        node.insertKey(val);

        // 如果叶子节点已满，需要进行分裂
        if (node.isFull()) {
            splitTreeNode(node);
        }
    }

    // 在 B+ 树上删除一个值
    public void delete(int val) {
        // 找到要删除的叶子节点
        TreeNode node = findLeafNode(val, root);
        node.deleteKey(val);

        // 如果叶子节点删除后关键字个数小于等于1，需要进行合并
        if (node.keyNum <= 1) {
            mergeTreeNode(node);
        }
    }

    // 查找要插入或删除的值所在的叶子节点
    private TreeNode findLeafNode(int val, TreeNode node) {
        if (node.level == 0) {
            return node;
        }
        int pos = node.findKeyPosition(val);
        if (pos == 0 || pos == node.keyNum) {
            pos = pos - 1;
        }
        return findLeafNode(val, node.child[pos]);
    }

    // 分裂一个满节点
    private void splitTreeNode(TreeNode node) {
        // 创建新的节点，作为当前节点的右兄弟
        TreeNode right = new TreeNode(node.level);
        right.keyNum = 1;
        // 将当前节点的后两个关键字及其对应的子节点复制到右兄弟中
        right.child[0] = node.child[2];
        right.child[1] = node.child[3];
        right.key[0] = node.key[2];
        node.keyNum = 1;

        // 如果当前节点是根节点，需要创建新的根节点并设置子节点
        if (node.level == 0) {
            TreeNode newRoot = new TreeNode(1);
            newRoot.key[0] = right.key[0];
            newRoot.child[0] = node;
            newRoot.child[1] = right;
            root = newRoot;
        } else {
            // 否则，将右兄弟插入到父节点中
            TreeNode parent = getParent(node);
            int pos = parent.findKeyPosition(right.key[0]);
            parent.insertKey(right.key[0]);
            for (int i = parent.keyNum - 1; i > pos; i--) {
                parent.child[i + 1] = parent.child[i];
            }
            parent.child[pos + 1] = right;
        }
    }

    // 合并两个兄弟节点
    private void mergeTreeNode(TreeNode node) {
        if (node == root) {
            if (node.keyNum == 0) {
                root = node.child[0];
            }
            return;
        }

        // 找到节点的兄弟节点
        TreeNode parent = getParent(node);
        int pos = 0;
        while (pos < parent.keyNum && parent.child[pos] != node) {
            pos++;
        }
        TreeNode leftSibling = null;
        TreeNode rightSibling = null;
        if (pos > 0) {
            leftSibling = parent.child[pos - 1];
        }
        if (pos < parent.keyNum) {
            rightSibling = parent.child[pos + 1];
        }

        // 如果左兄弟节点的关键字个数大于1，将当前节点的最小关键字插入到当前节点中
        if (leftSibling != null && leftSibling.keyNum > 1) {
            int key = leftSibling.key[leftSibling.keyNum - 1];
            leftSibling.deleteKey(key);
            node.insertKey(key);
            if (node.level == 0) {
                node.child[0] = leftSibling.child[leftSibling.keyNum];
                leftSibling.child[leftSibling.keyNum] = null;
            } else {
                node.child[node.keyNum] = node.child[node.keyNum - 1];
                node.child[node.keyNum - 1] = leftSibling.child[leftSibling.keyNum];
                leftSibling.child[leftSibling.keyNum] = null;
            }
            return;
        }

        // 如果右兄弟节点的关键字个数大于1，将当前节点的最大关键字插入到当前节点中
        if (rightSibling != null && rightSibling.keyNum > 1) {
            int key = rightSibling.key[0];
            rightSibling.deleteKey(key);
            node.insertKey(key);
            if (node.level == 0) {
                node.child[1] = rightSibling.child[0];
                for (int i = 0; i < rightSibling.keyNum; i++) {
                    rightSibling.child[i] = rightSibling.child[i + 1];
                }
                rightSibling.child[rightSibling.keyNum] = null;
            } else {
                node.child[node.keyNum] = rightSibling.child[0];
                for (int i = 0; i < rightSibling.keyNum; i++) {
                    rightSibling.child[i] = rightSibling.child[i + 1];
                }
                rightSibling.child[rightSibling.keyNum] = null;
            }
            return;
        }

        // 如果左兄弟节点和右兄弟节点的关键字个数都为1，则需要将左右兄弟节点合并，当前节点作为新的右子树
        if (leftSibling != null) {
            leftSibling.insertKey(node.key[0]);
            if (node.level == 0) {
                leftSibling.child[1] = node.child[0];
            } else {
                leftSibling.child[2] = node.child[0];
            }
            parent.deleteKey(node.key[0]);
            parent.child[pos] = leftSibling;
        } else if (rightSibling != null) {
            node.insertKey(rightSibling.key[0]);
            if (node.level == 0) {
                node.child[1] = rightSibling.child[0];
                node.child[2] = rightSibling.child[1];
            } else {
                node.child[3] = rightSibling.child[0];
            }
            parent.deleteKey(rightSibling.key[0]);
            parent.child[pos + 1] = node;
        }

        if (parent.keyNum < 1 && parent != root) {
            mergeTreeNode(parent);
        }
    }

    // 查找节点的父节点
    private TreeNode getParent(TreeNode node) {
        TreeNode parent = root;
        TreeNode child = root;
        for (int i = 0; i < node.level; i++) {
            int pos = child.findKeyPosition(node.key[0]);
            parent = child;
            child = child.child[pos];
        }
        return parent;
    }

    public static void main(String[] args) {
        String s = "[{\"typeId\":6001,\"content\":\"工程变更\",\"objectId\":4276851},{\"typeId\":6001,\"content\":\"生产程序 J5A20-1256-1000M(微影客制4）\",\"objectId\":4272020},{\"typeId\":6001,\"content\":\"J5A20-DF-1000M 杭州微影3 曾凡辉\",\"objectId\":4180772},{\"typeId\":6001,\"content\":\"J5A20-DF-600M 太星电子 模块化精度改善\",\"objectId\":4229508},{\"typeId\":6001,\"content\":\"生产程序 J51LF-DM-600AG 天海蓝  陈军 十三所70W激光管\",\"objectId\":4323226},{\"typeId\":6001,\"content\":\"产品异常处理改善\",\"objectId\":4267221},{\"typeId\":6001,\"content\":\"生产程序    东莞拓莱   J51GD-2000A   拓莱蓝牙板1     十三90W激光管\",\"objectId\":4358412},{\"typeId\":6001,\"content\":\"生产程序  深圳销售备货仓  J5A30-DF-1000M    姚纯    公司标准带固定孔  十三90W激光管\",\"objectId\":4364344},{\"typeId\":6001,\"content\":\"生产程序  上海经典  J51KC-DM-600AG  常涛    十三所70W\",\"objectId\":4336203},{\"typeId\":6001,\"content\":\"J51QQ结构修改兼容双色屏\",\"objectId\":4273597},{\"typeId\":6001,\"content\":\"ES订单 J51SA-DM-600AG 深圳销售仓备货 姚纯  十三所70W\",\"objectId\":4331985},{\"typeId\":6001,\"content\":\"生产程序 J51LE-DM-600AG 亚马逊 陈鑫 十三所70W激光管\",\"objectId\":4305932},{\"typeId\":6001,\"content\":\"生产程序 J51LA-600AG (L2)   常涛 EXIMPRIES 十三所70W激光管\",\"objectId\":4300179},{\"typeId\":6001,\"content\":\"生产程序 J51LC-DM-600AG 亚马逊 陈鑫 十三所70W激光管\",\"objectId\":4300153},{\"typeId\":6001,\"content\":\"生产程序 J51LE-DM-600G 亚马逊 陈鑫 十三所70W激光管\",\"objectId\":4300120},{\"typeId\":6001,\"content\":\"生产程序 J51KN-DM-600AG  魅绅   刘世帅  十三所70W\",\"objectId\":4294421},{\"typeId\":6001,\"content\":\"生产程序 J5A20-DF-1500S（标准款） 公司标品   十三所90W \",\"objectId\":4294435},{\"typeId\":6001,\"content\":\"生产程序 J51KC-DM-600AG  HanShin Project  田梦阳\",\"objectId\":4294375},{\"typeId\":6001,\"content\":\"生产程序 J51KP-DM-600AG 孙从峰\",\"objectId\":4276287},{\"typeId\":6001,\"content\":\"生产程序    杭州申纺 J51KA-DM-600AG 孙从峰\",\"objectId\":4276330},{\"typeId\":6001,\"content\":\"可靠性测试\",\"objectId\":4277277},{\"typeId\":6001,\"content\":\"J5CCA结构修改兼容双色屏\",\"objectId\":4273587},{\"typeId\":6001,\"content\":\"PLM料号申请与资料上传\",\"objectId\":4213041},{\"typeId\":6001,\"content\":\"生产程序  J51SE-MC-1000AG(黑色） Techtuit Co., Ltd. 胡忠敏 十三所70W激光管\",\"objectId\":4305973},{\"typeId\":6001,\"content\":\"生产异常单J5A90-DF-1800M\",\"objectId\":4273160},{\"typeId\":6001,\"content\":\"生产程序  合肥英睿5  J5A20-DF-1000S   曾凡辉  十三所90W\",\"objectId\":4311331},{\"typeId\":6001,\"content\":\"生产程序  销售备货仓标品  J5A90-DF-1800M    姚纯    雷尔特75W\",\"objectId\":4335362},{\"typeId\":6001,\"content\":\"生产程序  公司标品  J5A30-DF-800M        十三90W激光管\",\"objectId\":4343149},{\"typeId\":6001,\"content\":\"产品TOF系统性改善（光学，结构，工艺，硬件）\",\"objectId\":4262590},{\"typeId\":6001,\"content\":\"生产程序   杭州大立  J5A20-DF-1000S   曾凡辉  十三所90W\",\"objectId\":4311254},{\"typeId\":6001,\"content\":\"生产程序   深圳视慧通科技  J5A20-DF-1200M    刘世帅   公司标准带固定孔  十三90W激光管\",\"objectId\":4364578},{\"typeId\":6001,\"content\":\"生产异常单J5A90-1000A(杭州大立)\",\"objectId\":4274052},{\"typeId\":6001,\"content\":\"生产程序 J51LF-DM-600G 天海蓝  陈军 十三所70W激光管\",\"objectId\":4323265},{\"typeId\":6001,\"content\":\"销售ES订单 东莞拓莱J5A20-1500M\",\"objectId\":4050772},{\"typeId\":6001,\"content\":\"J5CCA-1000M 晔茂科技 小圆柱红绿屏 5台\",\"objectId\":4193918},{\"typeId\":6001,\"content\":\"生产异常单J5A20-DF-1500M(金华龙）（高德带固定孔机芯）\",\"objectId\":4193875},{\"typeId\":6001,\"content\":\"生产程序  J55FA-DF-2000A（大单目） 郑州销售仓备货 孙丛峰\",\"objectId\":4266653},{\"typeId\":6001,\"content\":\"J5A90-1547-1500S 生产程序浙江大华\",\"objectId\":4141943},{\"typeId\":6001,\"content\":\"生产程序  J58TA-2000M标准款   亚马逊\",\"objectId\":4266677},{\"typeId\":6001,\"content\":\"生产程序  J5A20-DF-1000M（合肥英睿-90w激光管)） \",\"objectId\":4266667},{\"typeId\":6001,\"content\":\"生产程序J51MB-DF-4000M 十三所90W\",\"objectId\":4180830},{\"typeId\":6001,\"content\":\"生产程序 J51IA-0629-1000AG韩国打火机 何石平 70W激光管 \",\"objectId\":4266378},{\"typeId\":6001,\"content\":\"生产程序 J51LF-1500A郑州备货 刘世帅 更换70W激光管 \",\"objectId\":4266418},{\"typeId\":6001,\"content\":\"生产程序 J51LF-1000A郑州备货 刘世帅 更换70W激光管 \",\"objectId\":4266390},{\"typeId\":6001,\"content\":\"J5A20-DF-1000M湖南笃宏\",\"objectId\":3976704},{\"typeId\":6001,\"content\":\"J51L0-DF-3000M东莞鑫泰替换   十三所90W激光管\",\"objectId\":4214969},{\"typeId\":6001,\"content\":\"生产程序 J51LF-2000-3000A郑州备货 刘世帅 更换90W激光管 \",\"objectId\":4266434},{\"typeId\":6001,\"content\":\"生产订单 J58TA-DF-1500M 东莞拓莱 种法杰\",\"objectId\":4274663},{\"typeId\":6001,\"content\":\"J51KB-DM-600AG New Aim PTY LTD 马超三台\",\"objectId\":4214781},{\"typeId\":6001,\"content\":\"ES 订单 J5A30-DF-1000M深知未来 新结构 90W激光管 \",\"objectId\":4265578},{\"typeId\":6001,\"content\":\"生产异常单J51GE（G机芯）爱朗光电\",\"objectId\":3974938},{\"typeId\":6001,\"content\":\"生产程序 J58YA-2000M俄罗斯Navigator 常涛  \",\"objectId\":4266628},{\"typeId\":6001,\"content\":\"ES 订单 J58YA-DF-1500M 东莞拓莱 种法杰\",\"objectId\":4266600},{\"typeId\":6001,\"content\":\"生产程序 J5A20-DF-1500M 东莞拓莱-(模块化导入) 90W 种法杰\",\"objectId\":4266644},{\"typeId\":6001,\"content\":\"J51GE-0567-2000A 生产程序更换金属数码片 爱朗\",\"objectId\":4141858},{\"typeId\":6001,\"content\":\"J51KS-DM-600AG New Aim PTY LTD 马超三台\",\"objectId\":4214755},{\"typeId\":6001,\"content\":\"生产异常单J5A20-DF-1000M(合肥英睿）（生产反馈54米-60米精度不准）\",\"objectId\":4193859},{\"typeId\":6001,\"content\":\"J5A60-DF-2000M浙江大华科技\",\"objectId\":3988239},{\"typeId\":6001,\"content\":\"J51KB-DM-600AG深圳亚马逊 模块化精度改善\",\"objectId\":4215081},{\"typeId\":6001,\"content\":\"J51L-T方案消耗库存\",\"objectId\":4008494},{\"typeId\":6001,\"content\":\"销售订单-J51MB（4000A红绿双色屏）\",\"objectId\":3974296},{\"typeId\":6001,\"content\":\"销售订单-高德J5A20（1500S）\",\"objectId\":3974300},{\"typeId\":6001,\"content\":\"销售订单-道通J5A60-（2000S）\",\"objectId\":3974291},{\"typeId\":6001,\"content\":\"销售订单-J55FA大单目\",\"objectId\":3974194},{\"typeId\":6001,\"content\":\"销售订单-J51MB测绘（高透屏）\",\"objectId\":3974203}]";
        System.out.println(s.length());
    }
}

