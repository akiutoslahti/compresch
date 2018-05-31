# Silesia corpus

## Test files
File name | Description
--- | ---
dickens | Collected works of Charles Dickens
mozilla | Tarred executables of Mozilla 1.0 (Tru64 UNIX edition)
mr | Medical magnetic resonanse image
nci | Chemical database of structures
ooffice | A dll from Open Office.org 1.01
osdb | Sample database in MySQL format from Open Source Database Benchmark
reymont | Text of the book Chłopi by Władysław Reymont
samba | Tarred source code of Samba 2-2.3
sao | The SAO star catalog
webster | The 1913 Webster Unabridged Dictionary
xml | Collected XML files
x-ray | X-ray medical picture

## Performance testing results
### Huffman coding
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
dickens | 10192446 | 5826185 | 57% | 668 | 412
mozilla | 51220480 | 40421687 | 78% | 4281 | 2366
mr | 9970564 | 4755949 | 47% | 345 | 276
nci | 33553445 | 11682328 | 34% | 766 | 529
ooffice | 6152192 | 5166246 | 83% | 402 | 349
osdb | 10085684 | 8342362 | 82% | 733 | 627
reymont | 6627202 | 4069684 | 61% | 289 | 249
samba | 21606400 | 16547364 | 76% | 1089 | 966
sao | 7251944 | 6901001 | 95% | 579 | 499
webster | 41458703 | 26037983 | 62% | 2004 | 1664
x-ray | 8474240 | 7089625 | 83% | 540 | 460
xml | 5345280 | 3731412 | 69% | 254 | 225

### Lempel-Ziv-Welch
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
dickens | 10192446 | 5653629 | 55% | 1000 | 360
mozilla | 51220480 | 51753405 | 101% | 5237 | 2284
mr | 9970564 | 6412599 | 64% | 921 | 318
nci | 33553445 | 5510138 | 16% | 2125 | 546
ooffice | 6152192 | 5740881 | 93% | 627 | 280
osdb | 10085684 | 8444588 | 83% | 1083 | 428
reymont | 6627202 | 2628896 | 39% | 502 | 175
samba | 21606400 | 19614830 | 90% | 1849 | 910
sao | 7251944 | 8076665 | 111% | 844 | 361
webster | 41458703 | 34758047 | 83% | 3782 | 1747
x-ray | 8474240 | 9724424 | 114% | 1012 | 425
xml | 5345280 | 5081913 | 95% | 411 | 232
