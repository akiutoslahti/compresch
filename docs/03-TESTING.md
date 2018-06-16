# Testing

## Unit testing
Data structures, I/O utilities and compression related classes have been thoroughly tested with unit tests. Encoding and Decoding classes have been tested by actually encoding, decoding and checking the diff between original input and final output file. Main class has been tested for some functions, but as it acts as a class providing command line user interface, some of it has to be tested 'by hand'. I have tried to test its functionality as thoroughly as possible, but some bugs may still remain.

[Coverage report](https://codecov.io/gh/akiutoslahti/compresch)

## Benchmarking and comparison
### Efficiency of compression
Efficiency of compression has been evaluated with three common compression corpuses and chunk of random data. Encoding and decoding times have also been recorder.

Canterbury corpus  
[Test report](performancetests/canterbury.md)  

Large Canterbury corpus  
[Test report](performancetests/canterbury-large.md) 

Silesia corpus  
[Test report](performancetests/silesia.md)

With random data there is no compression. Instead encoding actually increases file size. With Huffman coding the increase in length is modest +2%, but for LZW we can observe increases in length in range from +12% to +46%. Therefore we can conclude that there is no point in trying to encode data with high randomness with Huffman coding or LZW.  
[Test report](performancetests/random.md)

### Lempel-Ziv-Welch dictionary size
Lempel-Ziv-Welch compression has been evaluated also for effects of dictionary size to compression ratio. Testing has been performed on King James Bible from Project Gutenberg. Test file is UTF-8 formatted plain text and 4 452 069 bytes long.

Running tests on same file with all different dictionary sizes shows how great effect dictionary size has on compression ratio, even though doubling dictionary size also adds one bit to every encoded code word. 

Of course the test file used here is quite optimal to showing effects of dictionary size. Firstly it is sufficiently long and secondly it has limited variance in it as it is utilizing solely english vocabulary. 
 
Even though here we only saw beneficial effects to compression ration with every increase in dictionary size, we should also remember that it can have adversary effects. For couple of files in Canterbury corpus (report in previous subsection) we saw that smaller dictionary size actually had better compression ratio in case that original file was sufficiently short. Therefore there actually is not dictionary size which is optimal in every possible scenario. However there seems to be a trend, that smaller files seem to benefit from smaller dictionary size while larger files seem to benefit from larger dictionary size.
 
[Test Report](performancetests/bible.md)

### Comparison to common compression tools
My implementations have been also benchmarked against couple common compression tools: *Unix compress(LZ77), gzip(DEFLATE) and bzip2(Burrows-Wheeler-Transform)*.

[Test report](performancetests/comparison-to-other-tools.md)

## Conclusions
*// what can be efficiently compressed?*
