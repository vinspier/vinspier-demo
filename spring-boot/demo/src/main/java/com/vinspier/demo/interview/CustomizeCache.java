package com.vinspier.demo.interview;

import java.util.HashMap;
/**
* @description: 自定义简易 缓存结构
 * hashmap + 双向链表实现
* @author: vinspeir
* @date:2020/7/23 15:50
*/
public class CustomizeCache<K,V> implements CustomizeCached<K,V>{

    private Node<K> head;

    private Node<K> tail;

    private HashMap<K,V> map;

    public CustomizeCache() {
        head  = new Node<>();
        tail = new Node<>();
        head.next =  tail;
        tail.pre = head;
        map = new HashMap<>();
    }

    /**
     * 添加元素
     * */
    @Override
    public V put(K key,V  val) {
        V oldVal = map.get(key);
        // 链表中 不存在 插入头接点
        if (oldVal == null){
            Node<K> node = new Node<>(key);
            addFirst(node);
            map.put(key,val);
            return val;
        }
        moveToFirst(key);
        map.put(key,val);
       return oldVal;
    }

    /**
     * 获取元素
     * 若缓存中有该元素 则将该元素提到首位
     * */
    @Override
    public V get(K key) {
        V val = map.get(key);
        if (val != null){
            moveToFirst(key);
        }
        return val;
    }

    /**
     * 删除某个值
     * @param key
     * @return
     */
    @Override
    public V remove(K key) {
        V val = map.get(key);
        // 删除节点
        removeNode(key);
        return val;
    }

    /** 添加到第一位 */
    private void addFirst(Node<K> target){
        head.next.pre = target;
        target.next = head.next;
        head.next = target;
        target.pre = head;
    }

    /** 将访问的元素提到首位 */
    private void moveToFirst(K key){
        // 链表中存在
        // 遍历寻找元素 并替换
        Node<K> temp = findNode(key);
        // 找到之后 如果是第一个节点 跳过
        if (temp.pre == head){
            return ;
        }
        // 非第一个 处理节点处需要替换位置
        temp.next.pre = temp.pre;
        temp.pre.next = temp.next;
        // 添加到 头节点
        addFirst(temp);
    }

    /** 移除节点 */
    private Node<K> removeNode(K key){
        // 遍历寻找元素 并替换
        Node<K> temp = findNode(key);
        // 非第一个 处理节点处需要替换位置
        temp.next.pre = temp.pre;
        temp.pre.next = temp.next;
        return temp;
    }

    /** 查找节点 */
    private Node<K> findNode(K key){
        Node<K> temp = head.next;
        while (temp != tail && temp.getElement() != key){
            temp = temp.next;
        }
        return temp;
    }
    /**
     * 遍历
     * */
    @Override
    public void iterator() {
        Node point = head.next;
        while (point != null && point != tail){
            System.out.println(point.getElement());
            point = point.next;
        }
    }
}
