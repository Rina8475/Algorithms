package DataStructure;

public abstract class SymbolTable<Key, Value> {
    /* put - 将一个键值对放入表 */
    public abstract void put(Key key, Value val);

    /* get - 根据一个键从表中查找对应的值，查找不到则返回 null */
    public abstract Value get(Key key);

    /* delete - 从表中删去一个键值对 */
    public void delete(Key key) {
        put(key, null);
    }

    /* contains - 检查表中是否含有键 KEY 若含有则返回 TRUE */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract int size();

    /* keys - 返回表中所有键的集合 */
    public abstract Iterable<Key> keys();
}
