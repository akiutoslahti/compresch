# User Guide

## How to build
First clone repository and then change directory to cloned repository:
```
git clone https://github.com/akiutoslahti/compresch
cd compresch
```
Init latest version of gradle wrapper and then build project and javadoc:
```
gradle wrapper --gradle-version 4.7
./gradlew build
./gradlew javadoc
```
Classes are build to **./build/classes/java/**  
Jar is build to **./build/libs/**  
Javadoc is generated to  **./build/docs/javadoc/**  
Checkstyle report is generated to **./build/reports/checkstyle/**  
Code coverage report is generated to **./build/reports/jacoco/test/**  

## Usage/Help
Compresch takes a single input file and single output file as argument for compression/decompression. For compression all file formats are supported. For decompression only files compressed with Compresch can be passed as argument. For decompression used encoding does not have to be specified as Compresch will automatically detect which encoding was utilized in compression stage.

For performance testing a folder containing test files is taken as input and output will be single Markdown formatted file.

Basic usage (java -jar compresch --help):
```
usage: java -jar compresch [OPTION] [-i inputfile] [-o outputfile]
 -d,--decompress                           decompress file
 -h,--huffman-coding                       compress file using Huffman
                                           coding
    --help                                 print this message
 -i,--input-file <input file>              input file/folder
 -l,--lempel-ziv-welch <codeword length>   compress file using
                                           Lempel-Ziw-Welch
                                           - optional argument: codeword
                                           length in range [9, 16]
 -o,--ouput-file <output file>             ouput file
 -r,--dictionary-test                      run LZW dictionary size tests
 -t,--test                                 run performance tests
                                           - input: folder
                                           - output: markdown
```
## Examples of use
Compress file *text.txt* to file *test.txt.H* using Huffman coding.
```
java -jar compresch.jar -h -i test.txt -o test.txt.H
```
Compress file *test.txt* to file *test.txt.L* using Lempel-Ziv-Welch compression with default dictionary size. (12bit codewords, 4096 word dictionary)
```
java -jar compresch.jar -l -i test.txt -o test.txt.L
```
Compress file *test.txt* to file *text.txt.L* using Lempel-Ziv-Welch compression with user defined codeword lenght. Maximum dictionary size is limited by codeword length. (eg. 16bit codewords, 65 536 word dictionary)
```
java -jar compresch.jar -l 16 -i test.txt -o test.txt.L
```
Decompress Huffman or Lempel-Ziv-Welch encoded file *test.txt.compressed* to file *test.txt.decompressed*.
```
java -jar compresch.jar -d -i test.txt.compressed -o test.txt.decompressed
```
Print usage/help message
```
java -jar compresch.jar --help
```
Run compression performance tests on *folder of files* and create a Markdown formatted *test report*.
```
java -jar compresch.jar -t -i /path/to/folder/ -o /path/to/testreport.md
```
Run compression efficiency tests with all different Lempel-Ziv-Welch dictionary sizes on *test.txt* and create a Markdown formatted *test_report.md*
```
java -jar compresch.jar -r -i test.txt -o test_report.md
```
