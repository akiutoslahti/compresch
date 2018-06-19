## Performance testing results

### Test files
File name | Description
--- | ---
dickens | Collected works of Charles Dickens
mozilla | Tarred executables of Mozilla 1.0 (Tru64 UNIX edition)
mr | Medical magnetic resonanse image
nci | Chemical database of structures
ooffice | A dll from Open Office.org 1.01
osdb | Sample database in MySQL format from Open Source Database Benchmark
reymont | Text of the book Chłopi by Władysław Reymont
samba | Tarred source code of Samba 2-2.3
sao | The SAO star catalog
webster | The 1913 Webster Unabridged Dictionary
x-ray | X-ray medical picture
xml | Collected XML files

### Huffman coding
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
dickens | 10 192 446 | 5 826 185 | 57% | 730 | 428
mozilla | 51 220 480 | 40 421 687 | 79% | 4 149 | 2 274
mr | 9 970 564 | 4 755 949 | 48% | 336 | 276
nci | 33 553 445 | 11 682 328 | 35% | 740 | 528
ooffice | 6 152 192 | 5 166 246 | 84% | 399 | 342
osdb | 10 085 684 | 8 342 362 | 83% | 712 | 616
reymont | 6 627 202 | 4 069 684 | 61% | 300 | 246
samba | 21 606 400 | 16 547 364 | 77% | 1 086 | 953
sao | 7 251 944 | 6 901 001 | 95% | 573 | 492
webster | 41 458 703 | 26 037 983 | 63% | 1 980 | 1 629
x-ray | 8 474 240 | 7 089 625 | 84% | 528 | 444
xml | 5 345 280 | 3 731 412 | 70% | 244 | 213

### Lempel-Ziv-Welch (4k dictionary)
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
dickens | 10 192 446 | 5 653 632 | 55% | 883 | 395
mozilla | 51 220 480 | 51 753 408 | 101% | 5 441 | 2 541
mr | 9 970 564 | 6 412 602 | 64% | 1 066 | 355
nci | 33 553 445 | 5 510 141 | 16% | 2 150 | 527
ooffice | 6 152 192 | 5 740 884 | 93% | 738 | 315
osdb | 10 085 684 | 8 444 591 | 84% | 1 244 | 526
reymont | 6 627 202 | 2 628 899 | 40% | 574 | 193
samba | 21 606 400 | 19 614 833 | 91% | 2 199 | 1 028
sao | 7 251 944 | 8 076 668 | 111% | 900 | 387
webster | 41 458 703 | 34 758 050 | 84% | 4 037 | 1 830
x-ray | 8 474 240 | 9 724 427 | 115% | 1 067 | 459
xml | 5 345 280 | 5 081 916 | 95% | 464 | 341

### Lempel-Ziv-Welch (64k dictionary)
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
dickens | 10 192 446 | 4 306 050 | 42% | 1 258 | 319
mozilla | 51 220 480 | 41 791 544 | 82% | 6 897 | 2 133
mr | 9 970 564 | 3 785 450 | 38% | 1 511 | 288
nci | 33 553 445 | 3 665 810 | 11% | 3 504 | 486
ooffice | 6 152 192 | 5 103 920 | 83% | 929 | 291
osdb | 10 085 684 | 4 342 378 | 43% | 1 376 | 299
reymont | 6 627 202 | 1 941 526 | 29% | 764 | 166
samba | 21 606 400 | 17 599 352 | 81% | 2 417 | 919
sao | 7 251 944 | 6 981 778 | 96% | 1 305 | 387
webster | 41 458 703 | 14 574 830 | 35% | 4 808 | 1 049
x-ray | 8 474 240 | 7 771 646 | 92% | 1 197 | 396
xml | 5 345 280 | 3 847 300 | 72% | 486 | 194

