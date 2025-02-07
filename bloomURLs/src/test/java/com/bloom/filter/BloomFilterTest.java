package com.bloom.filter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

public class BloomFilterTest {
    private static final int CAPACITY = 1000;
    private static final double FALSE_POSITIVE_RATE = 0.01;

    @Test
    void testInsertionAndLookup() {
        var bloomFilter = new BloomFilter<String>(CAPACITY, FALSE_POSITIVE_RATE);
        bloomFilter.add("test.com");
        bloomFilter.add("example.com");

        assertTrue(bloomFilter.mightContain("test.com"), "test.com should be in the Bloom Filter");
        assertTrue(bloomFilter.mightContain("example.com"), "example.com should be in the Bloom Filter");
    }

    @Test
    void testNonInsertedItem() {
        var bloomFilter = new BloomFilter<String>(CAPACITY, FALSE_POSITIVE_RATE);
        bloomFilter.add("malicious-site.com");

        assertFalse(bloomFilter.mightContain("safe-site.com"), "safe-site.com was never inserted, should return false");
    }

    @Test
    void testFalsePositiveRate_10k() {
        int testSize = 10000;
        BloomFilter<String> smallBloomFilter = new BloomFilter<>(testSize, 0.05);

        for (int i = 0; i < testSize; i++) {
            smallBloomFilter.add("site-" + i + ".com");
        }

        int falsePositives = 0;
        for (int i = testSize; i < testSize + 1000; i++) {
            if (smallBloomFilter.mightContain("unknown-" + i + ".com")) {
                falsePositives++;
            }
        }

        double actualFalsePositiveRate = (double) falsePositives / 1000;
        System.out.println("Measured False Positive Rate: " + actualFalsePositiveRate);

        assertTrue(actualFalsePositiveRate <= 0.1, "False positive rate should not exceed 10%");
    }

    @Test
    void testMultipleHashFunctions() {
        var bloomFilter = new BloomFilter<String>(CAPACITY, FALSE_POSITIVE_RATE);
        int expectedHashes = bloomFilter.optimalHashFunctionCount(1000, 100);
        assertTrue(expectedHashes > 0, "Number of hash functions should be positive");
    }

    @Test
    void testLargeDataSet() {
        BloomFilter<String> bloomFilter = new BloomFilter<>(1000000, 0.001); // Large capacity, 1% false positive rate

        // Insert many items
        for (int i = 0; i < 100000; i++) {
            bloomFilter.add("item" + i);
        }

        // Check for a non-existent entry
        boolean found = bloomFilter.mightContain("nonexistent-entry");

        if (found) {
            System.out.println("False positive detected for 'nonexistent-entry'");
        }

        assertFalse(found, "nonexistent-entry should not be found");
    }

    @Test
    void testHashFunctionDistribution() {
        BloomFilter<String> bloomFilter = new BloomFilter<>(1000000, 0.01);

        String testItem = "example.com";
        System.out.println("Hashes for '" + testItem + "':");

        int[] hashes = bloomFilter.getHashes(testItem); // Make getHashes() public for testing
        for (int hash : hashes) {
            System.out.println(hash);
        }

        assertTrue(hashes.length > 0, "Hash functions should return multiple values.");
    }

    @Test
    void debugBitArrayCollisions() {
        BloomFilter<String> bloomFilter = new BloomFilter<>(1000000, 0.01);

        // Insert some test items
        bloomFilter.add("item1");
        bloomFilter.add("item2");
        bloomFilter.add("item3");

        // Check a non-existent item
        int[] hashes = bloomFilter.getHashes("nonexistent-entry"); // Ensure getHashes() is accessible
        System.out.println("Bit positions set for 'nonexistent-entry':");

        for (int hash : hashes) {
            System.out.println(hash);
        }

        assertFalse(bloomFilter.mightContain("nonexistent-entry"), "False positive detected");
    }

    @Test
    void testFalsePositiveRateLargeSet() {
        BloomFilter<String> bloomFilter = new BloomFilter<>(1000000, 0.01);

        // Insert 100,000 items
        for (int i = 0; i < 100000; i++) {
            bloomFilter.add("item" + i);
        }

        // Test 10,000 non-existent items
        int falsePositives = 0;
        int testCount = 10000;

        for (int i = 200000; i < 200000 + testCount; i++) {
            if (bloomFilter.mightContain("item" + i)) {
                falsePositives++;
            }
        }

        double observedRate = (double) falsePositives / testCount;
        System.out.println("Observed false positive rate: " + observedRate);

        // Assert false positive rate is within an acceptable range
        assertTrue(observedRate <= 0.02, "False positive rate too high!");
    }

    @Test
    void testBitArrayFillPercentage() {
        BloomFilter<String> bloomFilter = new BloomFilter<>(1000000, 0.01);

        for (int i = 0; i < 100000; i++) {
            bloomFilter.add("item" + i);
        }

        int bitsSet = bloomFilter.countBitsSet();
        double fillPercentage = (double) bitsSet / bloomFilter.getSize(); // getSize() should return `m`

        System.out.println("Bits set: " + bitsSet);
        System.out.println("Bit array fill percentage: " + fillPercentage);

        assertTrue(fillPercentage < 0.5, "Bit array is overfilled!");
    }

    @Test
    void testHashDistribution() {
        BloomFilter<String> bloomFilter = new BloomFilter<>(1000000, 0.01);
        Set<Integer> uniqueBitPositions = new HashSet<>();

        for (int i = 0; i < 100000; i++) {
            String item = "item" + i;
            for (int hash : bloomFilter.getHashes(item)) { // Ensure you have a getHashes() method
                uniqueBitPositions.add(hash % bloomFilter.getSize());
            }
        }

        System.out.println("Unique bit positions set: " + uniqueBitPositions.size());
        double uniqueBitPercentage = (double) uniqueBitPositions.size() / bloomFilter.getSize();
        System.out.println("Unique bit coverage: " + uniqueBitPercentage);

        assertTrue(uniqueBitPercentage > 0.05, "Too few unique bit positions are being set!");
    }

}
