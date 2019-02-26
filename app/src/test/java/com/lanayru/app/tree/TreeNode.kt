package com.lanayru.app.tree

class TreeNode<D> {

    /**
     * tree data
     */
    var data: D? = null

    /**
     * parent node
     */
    var p: TreeNode<D>? = null
    /**
     * left node
     */
    var l: TreeNode<D>? = null
    /**
     * right node
     */
    var r: TreeNode<D>? = null

    constructor(data: D?) {
        this.data = data
    }

    constructor(data: D?, l: TreeNode<D>?, r: TreeNode<D>?) {
        this.data = data
        this.l = l
        this.r = r
    }

    companion object {
        //=================== 深度优先遍历 =====================

        /**
         * 前序遍历: 根结点->左子树->右子树
         */
        fun preOrderTraversal(node: TreeNode<*>?) {
            if (null != node) {
                print(node.data)
                preOrderTraversal(node.l)
                preOrderTraversal(node.r)
            }
        }

        /**
         * 中序遍历: 左子树->根结点->右子树
         */
        fun inOrderTraversal(node: TreeNode<*>?) {
            node?.let {
                inOrderTraversal(it.l)
                print(node.data)
                inOrderTraversal(it.r)
            }
        }

        /**
         * 后序遍历: 左子树->右子树->根结点
         */
        fun postOrderTraversal(node: TreeNode<*>?) {
            node?.let {
                postOrderTraversal(it.l)
                postOrderTraversal(it.r)
                print(node.data)
            }
        }

        // ===================== 广度优先遍历（层次遍历） 广度优先遍历会先访问离根节点最近的节点，每一层从左到右遍历 =============

        fun deepTraversal() {

        }

    }
}