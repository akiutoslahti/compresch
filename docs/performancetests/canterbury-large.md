# Large Canterbury corpus

## Test files
File name | Description
--- | ---
E.coli | Complete genome of the E. Coli bacterium
bible.txt | The King James version of the bible
world192.txt | The CIA world fact book

## Performance test results
### Huffman coding
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
E.coli | 4638690 | 1302536 | 28% | 222 | 116
bible.txt | 4047392 | 2220819 | 54% | 281 | 165
world192.txt | 2473400 | 1565836 | 63% | 190 | 118

### Lempel-Ziv-Welch
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
E.coli | 4638690 | 1254987 | 27% | 469 | 174
bible.txt | 4047392 | 1851248 | 45% | 408 | 184
world192.txt | 2473400 | 1569257 | 63% | 265 | 126