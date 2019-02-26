package com.lanayru.app.tree

import org.junit.Test

class TreeTest {


    fun newTree(): TreeNode<String> {
        val a = arrayOf("a", "b", "c", "d", "e", "f", "g")

        val array = Array(7) {
            TreeNode(a[it])
        }

        array[0].apply {
            l = array[1]
            r = array[2]
        }

        array[1].apply {
            l = array[3]
            r = array[4]
        }

        array[2].apply {
            l = array[5]
            r = array[6]
        }


        return array[0]
    }

    @Test
    fun testTraversal() {
        val t= newTree()
        TreeNode.preOrderTraversal(t)

        println()
        TreeNode.inOrderTraversal(t)

        println()
        TreeNode.postOrderTraversal(t)
    }

    fun String.load() {
        println("load")
    }

}