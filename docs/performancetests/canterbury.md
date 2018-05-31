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
alice29.txt | 152089 | 88143 | 57% | 30 | 17
asyoulik.txt | 125179 | 76505 | 61% | 11 | 10
cp.html | 24603 | 16465 | 66% | 3 | 1
fields.c | 11150 | 7362 | 66% | 1 | 1
grammar.lsp | 3721 | 2459 | 66% | 0 | 1
kennedy.xls | 1029744 | 479670 | 46% | 59 | 34
lcet10.txt | 426754 | 252797 | 59% | 32 | 25
plrabn12.txt | 481861 | 277000 | 57% | 35 | 20
ptt5 | 513216 | 109587 | 21% | 25 | 14
sum | 38240 | 25940 | 67% | 4 | 2
xargs.1 | 4227 | 2864 | 67% | 0 | 1

### Lempel-Ziv-Welch
Testfile | input size (bytes) | compressed size (bytes) | compress ratio | compression time (ms) | decompression time (ms)
--- | ---: | ---: | ---: | ---: | ---:
alice29.txt | 152089 | 72324 | 47% | 32 | 18
asyoulik.txt | 125179 | 62978 | 50% | 20 | 12
cp.html | 24603 | 12230 | 49% | 4 | 2
fields.c | 11150 | 5318 | 47% | 2 | 1
grammar.lsp | 3721 | 2118 | 56% | 1 | 1
kennedy.xls | 1029744 | 419222 | 40% | 118 | 47
lcet10.txt | 426754 | 222231 | 52% | 42 | 20
plrabn12.txt | 481861 | 234980 | 48% | 47 | 22
ptt5 | 513216 | 70230 | 13% | 58 | 15
sum | 38240 | 30942 | 80% | 5 | 2
xargs.1 | 4227 | 2693 | 63% | 1 | 0