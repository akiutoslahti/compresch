# Canterbury corpus

## Test files
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

## Performance test results
### Huffman coding
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
alice29.txt | 152 089 | 88 143 | 57% | 27 | 15
asyoulik.txt | 125 179 | 76 505 | 61% | 11 | 9
cp.html | 24 603 | 16 465 | 66% | 2 | 2
fields.c | 11 150 | 7 362 | 66% | 1 | 1
grammar.lsp | 3 721 | 2 459 | 66% | 1 | 0
kennedy.xls | 1 029 744 | 479 670 | 46% | 59 | 34
lcet10.txt | 426 754 | 252 797 | 59% | 32 | 18
plrabn12.txt | 481 861 | 277 000 | 57% | 35 | 21
ptt5 | 513 216 | 109 587 | 21% | 25 | 14
sum | 38 240 | 25 940 | 67% | 4 | 2
xargs.1 | 4 227 | 2 864 | 67% | 0 | 1

### Lempel-Ziv-Welch
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
alice29.txt | 152 089 | 72 324 | 47% | 32 | 19
asyoulik.txt | 125 179 | 62 978 | 50% | 19 | 13
cp.html | 24 603 | 12 230 | 49% | 4 | 2
fields.c | 11 150 | 5 318 | 47% | 2 | 2
grammar.lsp | 3 721 | 2 118 | 56% | 1 | 0
kennedy.xls | 1 029 744 | 419 222 | 40% | 121 | 46
lcet10.txt | 426 754 | 222 231 | 52% | 43 | 20
plrabn12.txt | 481 861 | 234 980 | 48% | 48 | 22
ptt5 | 513 216 | 70 230 | 13% | 56 | 15
sum | 38 240 | 30 942 | 80% | 4 | 3
xargs.1 | 4 227 | 2 693 | 63% | 1 | 0
