package DataStructure;

public abstract class OrderedSymbolTable<Key extends Comparable<Key>, Value> 
    extends SymbolTable<Key, Value> {
    /* min - 返回表中的最小的键 */
    public abstract Key min();

    /* max - 返回表中的最大的键 */
    public abstract Key max();

    /* floor - 返回小于等于 key 的最大的键 */
    public abstract Key floor(Key key);
    
    /* ceiling - 返回大于等于 key 的最小的键 */
    public abstract Key ceiling(Key key);

    /* rank - 表中小于key的键的数量 */
    public abstract int rank(Key key);

    /* select - 表中rank为k的键 */
    public abstract Key select(int k);

    /* deleteMin - 删除表中的最小值 */
    public void deleteMin() {
        delete(min());
    }

    /* deleteMax - 删除表中的最大值 */
    public void deleteMax() {
        delete(max());
    }

    /* size - [lo ... hi] 区间中键的数量 */
    public int size(Key lo, Key hi) {
        if (hi.compareTo(lo) < 0) 
            return 0;
        if (contains(hi))
            return rank(hi) - rank(lo) + 1;
        else 
            return rank(hi) - rank(lo);
    }

    @Override
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /* keys - 返回 [lo ... hi] 区间中所有键的集合 */
    public abstract Iterable<Key> keys(Key lo, Key hi);
}
