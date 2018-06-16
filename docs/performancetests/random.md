## Performance testing results
Input: Random data generated with /dev/urandom, length: 10 485 760 bytes

### Huffman coding
compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
---: | ---: | ---: | ---:
10 651 486 | 102% | 1 198 | 788

### Lempel-Ziv-Welch
Dictionary size | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
---: | ---: | ---: | ---: | ---:
512 | 11 750 923 | 112% | 1 280 | 856
1 024 | 12 955 904 | 124% | 1 520 | 875
2 048 | 14 038 828 | 134% | 1 243 | 705
4 096 | 14 881 940 | 142% | 1 192 | 720
8 192 | 15 295 168 | 146% | 1 428 | 739
16 384 | 15 069 967 | 144% | 1 585 | 737
32 768 | 14 134 276 | 135% | 1 665 | 749
65 536 | 12 853 436 | 123% | 1 820 | 734

