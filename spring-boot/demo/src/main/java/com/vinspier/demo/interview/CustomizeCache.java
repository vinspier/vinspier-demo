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

    /** 默认缓存元素限制大小 */

    private final int DEFAULT_CAPACITY = 1 << 30;

    /** 实际缓存元素限制大小 */
    private int capacity;

    /** 实际hashMap中的元素大小 */
    private int size;

    private void init(int capacity){
        head  = new Node<>();
        tail = new Node<>();
        head.next =  tail;
        tail.pre = head;
        size = 0;
        this.capacity = capacity;
        map = new HashMap<>();
    }

    public CustomizeCache() {
        init(DEFAULT_CAPACITY);
    }

    public CustomizeCache(int capacity){
        if (capacity < 1){
            throw new IllegalArgumentException("cache max capacity must be greater than 0");
        }
        this.capacity = capacity;
        init(Math.min(capacity,DEFAULT_CAPACITY));
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
            // 缓存满了 移除最后一个节点
            if (size >= capacity){
                removeLast();
            }else{
                size ++;
            }
            return val;
        }
        // 如果存在 则将原先的节点移动到头节点
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
        if (map.keySet().contains(key)){
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

    /** 移除 最后一个节点 */
    @Override
    public K removeLast() {
        Node<K> temp = tail.pre;
        temp.pre.next = tail;
        tail.pre = temp.pre;
        K key = temp.getElement();
        // help GC
        temp.pre = null;
        temp.next = null;
        return key;
    }

    /** 返回缓存中大小 */
    @Override
    public int size() {
        return size;
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
        if (temp != tail){
            // 非第一个 处理节点处需要替换位置
            temp.next.pre = temp.pre;
            temp.pre.next = temp.next;
            size --;
            return temp;
        }
        return null;
    }

    /** 查找节点 */
    private Node<K> findNode(K key){
        Node<K> temp = head.next;
        while (temp != tail && temp.getElement().hashCode() != key.hashCode() && !temp.getElement().equals(key)){
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
