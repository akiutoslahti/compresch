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
