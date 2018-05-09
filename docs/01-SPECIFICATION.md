# Specification

## Problem
How to implement lossless data compression (efficiently)?

## Solution
Study and implementation of two widely known lossless data compression algorithms complemented by performance testing with diverse input data.

### Algorithms
Huffman coding and Lempel-Ziv-Welch are both long standing industry standards on lossless data compression algorithms. Huffman coding will be implemented as canonical Huffman coding will be implemented as Canonical Huffman code to allow efficient storage of codebook within compressed file. Main goal is to study these two algorithms and come up with understanding how they work, compete against and complement each other.

### Data structures
**Huffman coding** 
For Huffman coding a frequency sorted binary tree(also known as Huffman tree) and priority queue are needed.

**Lempel-Ziv-Welch** 
For LZW the basis for the algorithm will be a fixed length hash based dictionary.

**Additional datastructures**
Dynamic list implementation will propably be also needed.

## Behaviour goals
Goal is to produce a command line program which can compress and decompress files. For compression the method of compression and single input and output targets are provided by command line parameneters. For decompression single input and output arguments provided by command line parameters. Compressed files are attached with a header which holds information about what compression method was used in compression stage.

## Time and space complexity goals
**Huffman coding**
Time complexity: O(n log n), space complexity: O(n)

**Lempel-Ziv-Welch**
Time complexity: O(n), space complexity: O(1)

## Sources
Thomas H. Cormen, Charles E. Leiserson, Ronald R. Rivest, and Clifford Stein: Introduction to algorithms, Third Edition. MIT Press, 2009. ISBN 978-0-262-53305-8. Section 16.3, pp. 428-437

Terry Welch: A Technique for High-Performance Data Compression. Computer Vol.17(6), June 1984, pp.8-19

[Wikipedia: Huffman coding](https://en.wikipedia.org/wiki/Huffman_coding)

[Wikipedia: Canonical Huffman code](https://en.wikipedia.org/wiki/Canonical_Huffman_code)

[Wikipedia: Lempel-Ziv-Welch](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch)
