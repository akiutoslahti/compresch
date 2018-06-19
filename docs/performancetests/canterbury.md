## Performance testing results

### Test files
File name | Description
--- | ---
alice29.txt | English text
asyoulik.txt | Shakespeare
cp.html | HTML source
fields.c | C source
grammar.lsp | LISP source
kennedy.xls | Excel spreadsheet
lcet10.txt | Technical writing
pl‌rabn12.txt | Poetry
ptt5 | CCITT test set
sum | SPARC executable
xargs.1 | GNU manual page

### Huffman coding
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
alice29.txt | 152 089 | 88 143 | 58% | 27 | 18
asyoulik.txt | 125 179 | 76 505 | 61% | 11 | 10
cp.html | 24 603 | 16 465 | 67% | 3 | 2
fields.c | 11 150 | 7 362 | 66% | 1 | 1
grammar.lsp | 3 721 | 2 459 | 66% | 1 | 0
kennedy.xls | 1 029 744 | 479 670 | 47% | 59 | 34
lcet10.txt | 426 754 | 252 797 | 59% | 31 | 18
plrabn12.txt | 481 861 | 277 000 | 57% | 36 | 20
ptt5 | 513 216 | 109 587 | 21% | 25 | 13
sum | 38 240 | 25 940 | 68% | 4 | 2
xargs.1 | 4 227 | 2 864 | 68% | 1 | 0

### Lempel-Ziv-Welch (4k dictionary)
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
alice29.txt | 152 089 | 72 327 | 48% | 33 | 18
asyoulik.txt | 125 179 | 62 981 | 50% | 19 | 12
cp.html | 24 603 | 12 233 | 50% | 4 | 2
fields.c | 11 150 | 5 321 | 48% | 2 | 2
grammar.lsp | 3 721 | 2 121 | 57% | 1 | 0
kennedy.xls | 1 029 744 | 419 225 | 41% | 119 | 48
lcet10.txt | 426 754 | 222 234 | 52% | 41 | 22
plrabn12.txt | 481 861 | 234 983 | 49% | 46 | 22
ptt5 | 513 216 | 70 233 | 14% | 55 | 16
sum | 38 240 | 30 945 | 81% | 4 | 3
xargs.1 | 4 227 | 2 696 | 64% | 1 | 0

### Lempel-Ziv-Welch (64k dictionary)
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
alice29.txt | 152 089 | 70 156 | 46% | 18 | 9
asyoulik.txt | 125 179 | 62 756 | 50% | 15 | 7
cp.html | 24 603 | 14 956 | 61% | 3 | 2
fields.c | 11 150 | 7 092 | 64% | 1 | 1
grammar.lsp | 3 721 | 2 826 | 76% | 1 | 0
kennedy.xls | 1 029 744 | 351 614 | 34% | 110 | 42
lcet10.txt | 426 754 | 171 618 | 40% | 56 | 25
plrabn12.txt | 481 861 | 204 872 | 43% | 66 | 29
ptt5 | 513 216 | 70 124 | 14% | 74 | 17
sum | 38 240 | 25 062 | 66% | 4 | 3
xargs.1 | 4 227 | 3 592 | 85% | 1 | 0

