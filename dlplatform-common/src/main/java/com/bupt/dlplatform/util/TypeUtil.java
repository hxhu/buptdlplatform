package com.bupt.dlplatform.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huhx on 2020/10/1
 */
public class TypeUtil {
    public static Object returnVoidValue(String type){
        if( type == null || type.length() == 0 ){
            return null;
        }

        Object value = null;
        switch (type){
            case "list":      value = new ArrayList<Object>(); break;
            case "figure":    value = new ArrayList<Object>(); break;
            case "picture":   value = new ArrayList<Object>(); break;
            case "video":     value = new ArrayList<Object>(); break;
            case "map":       value = new ArrayList<Object>(); break;
            case "heartbeat": value = new ArrayList<Object>(); break;
            default:          value = null;                     break;
        }

        return value;
    }

    public static void insertValue(ArrayList list, String type, Object newValue){
        if( list == null || type == null || type.length() == 0 ){
            return;
        }

        if( list.size() >= 15 ){
            for( int i = 1; i < list.size(); i++ ){
                list.set(i-1, list.get(i));
            }
            list.set(list.size()-1, newValue);
        }else{
            list.add(newValue);
        }
//        switch (type){
//            case "list":      queue.offer(newValue); break;
//            case "picture":   queue.offer(newValue); break;
//            case "video":     queue.offer(newValue); break;
//            case "map":       queue.offer(newValue); break;
//            case "heartbeat": queue.offer(newValue); break;
//            case "figure":    queue.offer(newValue); break;
//            default:          break;
//        }
    }


}
