package com.bloom.filter;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.List;
import java.util.function.Function;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  a generic Bloom filter class using an efficient hash function and bit array manipulation.
 *  this class uses bit arrays and multiple hash functions to approximate membership checks.
 */
public class BloomFilter<T> {
    private final BitSet bitSet;
    private final int size;
    private final int numHashFunctions;
    private final Function<T, byte[]> hashFunction;

    public BloomFilter(int capacity, double falsePositiveRate) {
        this.size = optimalBitArraySize(capacity, falsePositiveRate);
        this.numHashFunctions = optimalHashFunctionCount(size, capacity);
        this.bitSet = new BitSet(size);
        this.hashFunction = this::sha256Hash;
    }

    private int[] getHashes(T item) {
        byte[] hashBytes = hashFunction.apply(item);
        int[] hashes = new int[numHashFunctions];

        for (int i = 0; i < numHashFunctions; i++) {
            int hash = ((hashBytes[i * 2] & 0xFF) << 8) | (hashBytes[i * 2 + 1] & 0xFF);
            hashes[i] = Math.abs(hash % size);
        }
        return hashes;
    }

    /**
     * insert an item
     * @param item
     */
    public void add(T item) {
        for (int hash : getHashes(item)) {
            bitSet.set(hash);
        }
    }

    /**
     * check if an item might be present
     * @param item
     * @return
     */
    public boolean mightContain(T item) {
        for (int hash : getHashes(item)) {
            if (!bitSet.get(hash)) {
                return false;
            }
        }
        return true;
    }

    private byte[] sha256Hash(T item) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(item.toString().getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    /**
     * computes the best bit array size
     * @param n
     * @param p
     * @return
     */
    private int optimalBitArraySize(int n, double p) {
        return (int) Math.ceil((-n * Math.log(p)) / (Math.log(2) * Math.log(2)));
    }

    /**
     * determines the number of hash functions
     * @param m
     * @param n
     * @return
     */
    private int optimalHashFunctionCount(int m, int n) {
        return (int) Math.ceil(((double) m / n) * Math.log(2));
    }
}
