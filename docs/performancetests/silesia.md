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
dickens | 10 192 446 | 5 826 185 | 57% | 733 | 413
mozilla | 51 220 480 | 40 421 687 | 78% | 4 231 | 2 211
mr | 9 970 564 | 4 755 949 | 47% | 360 | 275
nci | 33 553 445 | 11 682 328 | 34% | 781 | 544
ooffice | 6 152 192 | 5 166 246 | 83% | 409 | 349
osdb | 10 085 684 | 8 342 362 | 82% | 725 | 625
reymont | 6 627 202 | 4 069 684 | 61% | 302 | 247
samba | 21 606 400 | 16 547 364 | 76% | 1 108 | 959
sao | 7 251 944 | 6 901 001 | 95% | 603 | 500
webster | 41 458 703 | 26 037 983 | 62% | 2 023 | 1 632
x-ray | 8 474 240 | 7 089 625 | 83% | 545 | 455
xml | 5 345 280 | 3 731 412 | 69% | 249 | 212

### Lempel-Ziv-Welch
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
dickens | 10 192 446 | 5 653 629 | 55% | 878 | 356
mozilla | 51 220 480 | 51 753 405 | 101% | 5 244 | 2 248
mr | 9 970 564 | 6 412 599 | 64% | 845 | 309
nci | 33 553 445 | 5 510 138 | 16% | 1 911 | 492
ooffice | 6 152 192 | 5 740 881 | 93% | 552 | 269
osdb | 10 085 684 | 8 444 588 | 83% | 949 | 422
reymont | 6 627 202 | 2 628 896 | 39% | 441 | 169
samba | 21 606 400 | 19 614 830 | 90% | 1 735 | 907
sao | 7 251 944 | 8 076 665 | 111% | 749 | 362
webster | 41 458 703 | 34 758 047 | 83% | 3 398 | 1 693
x-ray | 8 474 240 | 9 724 424 | 114% | 938 | 441
xml | 5 345 280 | 5 081 913 | 95% | 402 | 240

