package com.bloom.filter;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.List;
import java.util.function.Function;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

/**
 *  a generic Bloom filter class using an efficient hash function and bit array manipulation.
 *  this class uses bit arrays and multiple hash functions to approximate membership checks.
 */
public class BloomFilter<T> {
    private final BitSet bitSet;
    private final int m;  // Size of bit array
    private final int k;  // Number of hash functions

    public BloomFilter(int n, double falsePositiveRate) {
        this.m = (int) (-n * Math.log(falsePositiveRate) / (Math.pow(Math.log(2), 2)));
        this.k = (int) Math.round((m / (double) n) * Math.log(2)); // optimal number of hash functions
        this.bitSet = new BitSet(m);
    }

    public int[] getHashes(String key) {
        int[] hashes = new int[k]; // k is the number of hash functions
        for (int i = 0; i < k; i++) {
            // Use MurmurHash3 with the seed `i` to ensure unique hash values
            hashes[i] = Math.abs(murmurHash(key, i)) % m; // `m` is the size of the bit array
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

    private byte[] sha256Hash(T item) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(item.toString().getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    private int murmurHash(String key, int seed) {
        return Hashing.murmur3_128(seed)
                .hashString(key, StandardCharsets.UTF_8)
                .asInt();
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
