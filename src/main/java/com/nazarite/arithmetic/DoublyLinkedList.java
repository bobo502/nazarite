package com.nazarite.arithmetic;

/**
 * 双向链表
 */
public class DoublyLinkedList {
    Node2 head;

    public void insert(int data) {
        Node2 newNode = new Node2(data);
        if (head == null) {
            head = newNode;
        } else {
            Node2 current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.prev = current;
        }
    }
    public void delete(int data){
        if(head == null){
            return;
        }

        Node2 current = head;
        if(current.data == data){
            head = current.next;
        } else {
            while (current.next != null){
                Node2 next = current.next;
                if(next.data == data){
                    Node2 doubleNext = next.next;
                    current.next = doubleNext;
                    doubleNext.prev = current;
                } else {
                    current = next;
                }
            }
        }
    }

    public void display() {
        Node2 current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        DoublyLinkedList dll = new DoublyLinkedList();

        dll.insert(1);
        dll.insert(2);
        dll.insert(3);
        dll.insert(4);

        dll.delete(3);
        dll.display();  // Output: 1 2 3 4
    }


}

 class Node2{
    int data;
    com.nazarite.arithmetic.Node2 prev;
    com.nazarite.arithmetic.Node2 next;

    public Node2(int data){
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}






