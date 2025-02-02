package com.bloom;

import com.bloom.filter.BloomFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class BloomFilterDemo {
    public static void main(String[] args) throws IOException {
        int capacity = 100_000; // Approximate number of URLs
        double falsePositiveRate = 0.01; // 1% false positive rate
        BloomFilter<String> bloomFilter = new BloomFilter<>(capacity, falsePositiveRate);
        HashSet<String> hashSet = new HashSet<>();

        // Load blacklist from file (one URL per line)
        List<String> blacklistedUrls = Files.readAllLines(Path.of("src/main/resources/blacklist.txt"));
        for (String url : blacklistedUrls) {
            bloomFilter.add(url);
            hashSet.add(url);
        }

        System.out.println("Loaded " + blacklistedUrls.size() + " URLs into Bloom Filter.");

        // User input test
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a URL to check (or 'exit'): ");
            String url = scanner.nextLine();
            if ("exit".equalsIgnoreCase(url)) break;

            boolean bloomResult = bloomFilter.mightContain(url);
            boolean hashSetResult = hashSet.contains(url);

            System.out.println("Bloom Filter: " + (bloomResult ? "Possibly Blacklisted" : "Not Blacklisted"));
            System.out.println("HashSet Check: " + (hashSetResult ? "Definitely Blacklisted" : "Not Blacklisted"));

            if (bloomResult && !hashSetResult) {
                System.out.println("⚠️ False Positive detected!");
            }
        }
        scanner.close();
    }
}
