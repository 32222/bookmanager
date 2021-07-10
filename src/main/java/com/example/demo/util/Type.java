package com.example.demo.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Type {
    public static final Map<Integer,String> BOOKSTATUS;
    static {
    	Map<Integer, String> status = new LinkedHashMap<>();
    	status.put(0, "未読");
    	status.put(1, "読了");
    	BOOKSTATUS = Collections.unmodifiableMap(status);
    }
    public static final Map<Integer,String> MEDIUM;
    static {
    	Map<Integer, String> medium = new LinkedHashMap<>();
    	medium.put(0, "未選択");
    	medium.put(1, "紙");
    	medium.put(2, "電子");
    	MEDIUM = Collections.unmodifiableMap(medium);
    }
}
