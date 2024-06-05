package org.example.DB;

import java.util.HashMap;

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
        public String getType(String key) {
            return valueTypes.get(key);
        }
        public int getDBSize() {
            return keyValues.size();
        }
}
