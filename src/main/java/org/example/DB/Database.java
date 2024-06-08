package org.example.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
        HashMap<String, Object> keyValues;
        HashMap<String, String> valueTypes;
        public Database() {
            keyValues = new HashMap<>();
            valueTypes = new HashMap<>();
        }
        public void set(String key, Object value) {
            keyValues.put(key, value);
            valueTypes.put(key, value.getClass().getName());
        }
        public Object get(String key) {
            return keyValues.get(key);
        }
        public void delete(String key) {
            keyValues.remove(key);
            valueTypes.remove(key);
        }
        public boolean exists(String key) {
            return keyValues.containsKey(key);
        }
        public int getDBSize() {
            return keyValues.size();
        }

        public int increment(String key) {
            if(!keyValues.containsKey(key)) {
                set(key, 1);
                return 1;
            }
            if(valueTypes.get(key).equals(Integer.class.getName())) {
                keyValues.put(key, (Integer)keyValues.get(key) + 1);
                return (Integer)keyValues.get(key);
            }
            else {
                throw new IllegalArgumentException("Value is not an integer");
            }
        }
        public int decrement(String key) {
            if(!keyValues.containsKey(key)) {
                set(key, -1);
                return -1;
            }
            if(valueTypes.get(key).equals(Integer.class.getName())) {
                keyValues.put(key, (Integer)keyValues.get(key) - 1);
                return (Integer)keyValues.get(key);
            }
            else {
                throw new IllegalArgumentException("Value is not an integer");
            }
        }
        public String type(String key) {
            if(!keyValues.containsKey(key)) {
                return "(nil)";
            }
            return valueTypes.get(key);
        }
        public void flushDB() {
            keyValues.clear();
            valueTypes.clear();
        }
        public void insertValuesAtHead(String key, List<String> values) {
            List<String> list = keyValues.get(key) == null ? new ArrayList<>() : (List<String>)keyValues.get(key);
            for(String value : values) {
                list.add(0, value);
            }
            set(key, list);
        }
        public void insertValuesAtTail(String key, List<String> values) {
            List<String> list = keyValues.get(key) == null ? new ArrayList<>() : (List<String>)keyValues.get(key);
            for (String value : values) {
                list.add(value);
            }
            set(key, list);
        }

}
