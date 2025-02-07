package com.bloom.filter;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import com.google.common.hash.Hashing;

/**
 *  a generic Bloom filter class using an efficient hash function and bit array manipulation.
 *  this class uses bit arrays and multiple hash functions to approximate membership checks.
 */
public class BloomFilter<T> {
    private final BitSet bitSet;
    private final int bitArraySize;
    private final int numHashFunctions;

    public BloomFilter(int n, double falsePositiveRate) {
        this.bitArraySize = (int) (-n * Math.log(falsePositiveRate) / (Math.pow(Math.log(2), 2)));
        this.numHashFunctions = (int) Math.round((bitArraySize / (double) n) * Math.log(2)); // optimal number of hash functions
        this.bitSet = new BitSet(bitArraySize);
    }

    public int[] getHashes(String key) {
        int[] hashes = new int[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            // Use MurmurHash3 with the seed `i` to ensure unique hash values
            hashes[i] = Math.abs(murmurHash(key, i)) % bitArraySize;
        }
        return hashes;
    }

    /**
     * insert an item
     * @param item
     */
    public void add(T item) {
        for (int hash : getHashes(item.toString())) {
            bitSet.set(hash);
        }
    }

    /**
     * check if an item might be present
     * @param item
     * @return
     */
    public boolean mightContain(T item) {
        for (int hash : getHashes(item.toString())) {
            if (!bitSet.get(hash)) {
                return false;
            }
        }
        return true;
    }

    private int murmurHash(String key, int seed) {
        return Hashing.murmur3_128(seed)
                .hashString(key, StandardCharsets.UTF_8)
                .asInt();
    }

    /**
     * determines the number of hash functions
     * made public since it could be useful for tuning.
     * @param m
     * @param n
     * @return
     */
    public int optimalHashFunctionCount(int m, int n) {
        return (int) Math.ceil((m / n) * Math.log(2));
    }

    public int countBitsSet() {
        int count = 0;
        for (int i = 0; i < bitSet.size(); i++) {
            if (bitSet.get(i)) {
                count++;
            }
        }
        return count;
    }

    public int getSize() {
        return bitSet.size(); // If using BitSet
    }

}
