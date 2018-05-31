# User Guide

## How to build
First clone repository and then change directory to cloned repository:
```
git clone https://github.com/akiutoslahti/compresch
cd compresch
```
Init gradle wrapper, upgrade wrapper and then build project and javadoc:
```
gradle wrapper
./gradlew wrapper --gradle-version 4.2.1
./gradlew build
./gradlew javadoc
```
Classes are build to ./build/classes/  
Jar is build to ./build/libs/  
Javadoc is generated to  
Checkstyle report is generated to ./build/reports/checkstyle/???  
Code coverage report is generated to ./build/reports/jacoco/???  

## Usage/Help
Compresch takes a single input file and single output file as argument for compression/decompression. For compression all file formats are supported. For decompression only files compressed with Compresch can be passed as argument. For decompression used encoding does not have to be specified as Compresch will automatically detect which encoding was utilized in compression stage.

For performance testing a folder containing test files is taken as input and output will be single Markdown formatted file.
```
/* paste help here */
```
## Examples
Compress file *text.txt* to file *test.txt.H* using Huffman coding.
```
java -jar compresch.jar -H test.txt test.txt.H
```
Compress file *test.txt* to file *test.txt.L* using Lempel-Ziv-Welch compression.
```
java -jar compresch.jar -L test.txt test.txt.L
```
Decompress Huffman or Lempel-Ziv-Welch encoded file *test.txt.compressed* to file *test.txt.decompressed*.
```
java -jar compresch.jar -D test.txt.compressed test.txt.decompressed
```
Print usage/help message
```
java -jar compresch.jar -h
```
Run compression performance tests on *folder of files* and create a Markdown formatted *test report*.
```
java -jar compresch.jar /path/to/folder/ /path/to/testreport.md
```
