package com.lyf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/4/11 21:56
 * @Modified By:
 */
public class Hello {
    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();



        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("HELLO");
        //第一种遍历方法使用 For-Each 遍历 List
//        for (String str:list) {
//            System.out.println(str);
//        }

        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }
}
