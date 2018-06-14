# Testing

## Unit testing
Data structures, I/O utilities and compression related classes have been thoroughly tested with unit tests. Encoding and Decoding classes have been tested by actually encoding, decoding and checking the diff between original input and final output file. Main class has been tested for some functions, but as it acts as a class providing command line user interface, some of it has to be tested 'by hand'. I have tried to test its functionality as thoroughly as possible, but some bugs may still remain.

## Benchmarking and comparison
### Efficiency of compression
Efficiency of compression has been evaluated with three common compression corpuses along with benchmarks for encoding and decoding times.

Canterbury corpus  
[Test report](performancetests/canterbury.md)  

Large Canterbury corpus  
[Test report](performancetests/canterbury-large.md) 

Silesia corpus  
[Test report](performancetests/silesia.md)

### Lempel-Ziv-Welch dictionary size
Lempel-Ziv-Welch compression has been evaluated also for effects of dictionary size to compression ratio. Testing has been performed on King James Bible from Project Gutenberg. Test file is UTF-8 formatted plain text and 4 452 069 bytes long.

[Test Report](performancetests/bible.md)

### Comparison to common compression tools
My implementations have been also benchmarked against couple common compression tools: *Unix compress(LZ77), gzip(DEFLATE) and bzip2(Burrows-Wheeler-Transform)*.

[Test report](performancetests/comparison-to-other-tools.md)

## Conclusions
*// what can be efficiently compressed?*