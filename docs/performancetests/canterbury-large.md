## Performance testing results
### Huffman coding
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
E.coli | 4 638 690 | 1 302 536 | 28% | 245 | 115
bible.txt | 4 047 392 | 2 220 819 | 54% | 269 | 158
world192.txt | 2 473 400 | 1 565 836 | 63% | 179 | 113

### Lempel-Ziv-Welch (4k dictionary)
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
E.coli | 4 638 690 | 1 254 990 | 27% | 443 | 180
bible.txt | 4 047 392 | 1 851 251 | 45% | 394 | 190
world192.txt | 2 473 400 | 1 569 260 | 63% | 257 | 130

### Lempel-Ziv-Welch (64k dictionary)
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
E.coli | 4 638 690 | 1 221 488 | 26% | 576 | 195
bible.txt | 4 047 392 | 1 425 644 | 35% | 512 | 193
world192.txt | 2 473 400 | 933 710 | 37% | 301 | 108

