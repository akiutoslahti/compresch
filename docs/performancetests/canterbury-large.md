# Large Canterbury corpus

## Test files
File name | Description
--- | ---
E.coli | Complete genome of the E. Coli bacterium
bible.txt | The King James version of the bible
world192.txt | The CIA world fact book

## Performance testing results
### Huffman coding
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
E.coli | 4 638 690 | 1 302 536 | 28% | 251 | 113
bible.txt | 4 047 392 | 2 220 819 | 54% | 274 | 152
world192.txt | 2 473 400 | 1 565 836 | 63% | 184 | 117

### Lempel-Ziv-Welch
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
E.coli | 4 638 690 | 1 254 987 | 27% | 447 | 174
bible.txt | 4 047 392 | 1 851 248 | 45% | 396 | 185
world192.txt | 2 473 400 | 1 569 257 | 63% | 256 | 124
