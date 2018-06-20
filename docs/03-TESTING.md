# Testing

## Unit testing
Data structures, I/O utilities and compression related classes have been thoroughly tested with unit tests. Encoding and Decoding classes have been tested by actually encoding, decoding and checking the diff between original input and final output file. Main class has been tested for some functions, but as it acts as a class providing command line user interface, some of it has to be tested 'by hand'. I have tried to test its functionality as thoroughly as possible, but some bugs may still remain.

[Interactive coverage report](https://codecov.io/gh/akiutoslahti/compresch)

## Benchmarking and comparison
### Efficiency of compression
Efficiency of compression has been evaluated with three common compression corpuses and chunk of random data. Encoding and decoding times have also been recorded.

**Canterbury corpus** is a collection of files intended for benchmarking of compression algorithms. It is a little dated(from 1997) and contains quite small files in standards of today. However I decided to include it in test set as it seemed quite relevant still.

From results we can see that Huffman coding results are quite in line with each other as compression ratio seems to be mostly in range of 58-68%. LZW with its default 4K dictionary size seems to be performing slightly better with all files except SPARC executable *sum*. For LZW we can also see that 64K dictionary size is an overkill for files this small in size, thus resulting in worse compression ratio due to codewords being represented in lengthier bit sequences.

**Large Canterbury corpus** was collected because some compression algorithms could not be satisfactorily evaluated on smaller files. In fact they needed larger amounts of data to achieve good compression ratio.

From results we can see that Huffman coding is again a steady performed here due to optimal prefix coding. LZW with default dictionary size is again performing the same or slightly better. However no we can see LZW truly in action with maximum dictionary size. E. coli genome has only slightly better compression performance but with King James Bible and CIA handbook there is improvement of over 20 percentage points in compression ratio.

**Silesia corpus** tries to implement data compression needs of today. It tries to combat weaknesses of previous compression corpus which are stated to be: 1) lack of larger files, 2) over representation of English-language texts, 3) lack of files being concatenations of large projects, 4) absence of medical images and 5) lack of databases that currently grow fast.

The test results are quite diverse. There are clearly files where Huffman coding is quite a good performer and other files where compression ratio is not so good. Judging the results is quite hard. Some databases and medical images seem to be quite compressable while others are not. Performance on english-language texts is again good but with other concatenations performance is quite poor.

LZW with small dictionary size is clearly a bad choice for larger files with couple exceptions which seem to be quite repetitive in contents. In nearly all cases Huffman coding beats LZW with small dictionary on this test collection. 

LZW with large dictionary size seems to lead clearly to better compression ratio but it is still not optimal for concatenated files. There are actually still some test files where Huffman coding is a better suited.

With **random data** there is no compression. Instead encoding actually increases file size. With Huffman coding the increase in length is modest +2%, but for LZW we can observe increases in length in range from +12% to +46%. Therefore we can conclude that there is no point in trying to encode data with high randomness with Huffman coding or LZW.

**Test reports:**  
[Canterbury corpus](performancetests/canterbury.md)  
[Large Canterbury corpus](performancetests/canterbury-large.md)  
[Silesia corpus](performancetests/silesia.md)  
[Random data](performancetests/random.md)

### Lempel-Ziv-Welch dictionary size
Lempel-Ziv-Welch compression has been evaluated also for effects of dictionary size to compression ratio. Testing has been performed on King James Bible from Project Gutenberg. Test file is UTF-8 formatted plain text and 4 452 069 bytes long.

Running tests on same file with all different dictionary sizes shows how great effect dictionary size has on compression ratio, even though doubling dictionary size also adds one bit to every encoded code word. 

Of course the test file used here is quite optimal to showing effects of dictionary size. Firstly it is sufficiently long and secondly it has limited variance in it as it is utilizing solely english vocabulary. 
 
Even though here we only saw beneficial effects to compression ration with every increase in dictionary size, we should also remember that it can have adversary effects. For couple of files in Canterbury corpus (report in previous subsection) we saw that smaller dictionary size actually had better compression ratio in case that original file was sufficiently short. Therefore there actually is not dictionary size which is optimal in every possible scenario. However there seems to be a trend, that smaller files seem to benefit from smaller dictionary size while larger files seem to benefit from larger dictionary size.
 
[Test Report: Dictionary size](performancetests/bible.md)

### Comparison to common compression tools
My implementations have been also benchmarked against couple common compression tools: *Unix compress(LZ77), gzip(DEFLATE) and bzip2(Burrows-Wheeler-Transform)*.

From results here we can see that Huffman coding and LZW are not in even the same ballpark as *gzip* and *bzip2*. With every test they are outperformed.

However there are quite many test files where the best result from my implementations is in the same ballpark as *unix compress* or even slightly better. However there is no single case where my implementation would be clearly better than *unix compress*.

Therefore Huffman coding and LZW do not withstand comparison to compression algorithms of today. There are clearly alternatives performing far better.

[Test report: Comparison to common compression tools](performancetests/comparison-to-other-tools.md)

## Conclusions
My implementations of Huffman coding and LZW work as intended and they also satisfy the aspect of lossless compression. They both seem to be good in compressing single-language text and other files which contain lots of repetitive data and/or have little variance. However they both seem to struggle with data having lots of variety and/or other aspects highlighted in Silesia corpus testing. Luckily there are far more superior modern compression algorithms as was highlighted in comparison section of this document.

## Sources
[Canterbury corpuses](http://corpus.canterbury.ac.nz/)  
[Silesia corpus](http://sun.aei.polsl.pl/~sdeor/index.php?page=silesia)  
[Project Gutenberg: King James Bible](http://www.gutenberg.org/ebooks/10)
