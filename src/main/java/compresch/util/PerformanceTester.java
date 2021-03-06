/*
 * MIT License
 *
 * Copyright (c) 2018 Aki Utoslahti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package compresch.util;

import compresch.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

/**
 * Test 'script' to test folder of files with Huffman, LZW-4K and LZW-64K encodings.
 * Produces a markdown formatted report.
 */
public class PerformanceTester {

    private File testFileFolder;
    private File testReport;
    private PrintWriter writer;
    private DecimalFormatSymbols symbols;
    private DecimalFormat df;

    /**
     * Constructs new PerformanceTester if parameters appear to be okay.
     * @param testFileFolderPath File folder, where files to be tested are in.
     * @param testReportPath     File path, where markdown report is saved.
     * @throws NullPointerException     if either one of parameters is null.
     * @throws IllegalArgumentException if first parameter is not a folder.
     */
    public PerformanceTester(String testFileFolderPath, String testReportPath) {
        Objects.requireNonNull(testFileFolderPath);
        Objects.requireNonNull(testReportPath);
        this.testFileFolder = new File(testFileFolderPath);
        this.testReport = new File(testReportPath);
        if (!this.testFileFolder.isDirectory()) {
            throw new IllegalArgumentException("Test file location is not a folder");
        }
        this.symbols = new DecimalFormatSymbols();
        this.symbols.setGroupingSeparator(' ');
        this.df = new DecimalFormat();
        this.df.setDecimalFormatSymbols(symbols);
        this.df.setGroupingSize(3);
        this.df.setMaximumFractionDigits(0);
    }

    /**
     * Runs compression testing to all files in testFileFolder.
     * @throws FileNotFoundException    if File testReport cannot be opened for writing.
     * @throws IllegalArgumentException if testFileFolder is empty.
     */
    public void testAll() throws FileNotFoundException {
        this.writer = new PrintWriter(this.testReport);
        this.writer.println("## Performance testing results");
        File[] testFiles = sortFileArray(testFileFolder.listFiles());
        if (testFiles.length == 0) {
            throw new IllegalArgumentException("Test file location is empty");
        }
        testHuffman(testFiles);
        testLzw4k(testFiles);
        testLzw64k(testFiles);
        this.writer.close();
    }

    private void testHuffman(File[] testFiles) {
        writeTableHeader("Huffman coding");
        for (File testFile : testFiles) {
            long[] testResults = testSingle("-" + Main.HUFFMAN, testFile.getPath());
            this.writer.println(testFile.getName() + " | "
                + this.df.format(testFile.length()) + " | " + this.df.format(testResults[0]) + " | "
                + (int) Math.round((1.0 * testResults[0] / testFile.length() * 100)) + "% | "
                + this.df.format(testResults[1]) + " | " + this.df.format(testResults[2]));
        }
        this.writer.println();
    }

    private void testLzw4k(File[] testFiles) {
        writeTableHeader("Lempel-Ziv-Welch (4k dictionary)");
        for (File testFile : testFiles) {
            long[] testResults = testSingle("-" + Main.LZW, testFile.getPath());
            this.writer.println(testFile.getName() + " | "
                + this.df.format(testFile.length()) + " | " + this.df.format(testResults[0]) + " | "
                + (int) Math.round((1.0 * testResults[0] / testFile.length() * 100)) + "% | "
                + this.df.format(testResults[1]) + " | " + this.df.format(testResults[2]));
        }
        this.writer.println();
    }

    private void testLzw64k(File[] testFiles) {
        writeTableHeader("Lempel-Ziv-Welch (64k dictionary)");
        for (File testFile : testFiles) {
            long[] testResults = testSingleLzw64k(
                new String[]{"-" + Main.LZW, "16"}, testFile.getPath());
            this.writer.println(testFile.getName() + " | "
                + this.df.format(testFile.length()) + " | " + this.df.format(testResults[0]) + " | "
                + (int) Math.round((1.0 * testResults[0] / testFile.length() * 100)) + "% | "
                + this.df.format(testResults[1]) + " | " + this.df.format(testResults[2]));
        }
        this.writer.println();
    }

    private void writeTableHeader(String header) {
        this.writer.println("### " + header);
        this.writer.println("Testfile | input size (bytes) | compressed size (bytes) "
            + "| compress ratio | compression time (ms) | decompression time (ms)");
        this.writer.println("--- | ---: | ---: | ---: | ---: | ---:");
    }

    /**
     * Runs compression and decompression test on a single file with both Huffman coding and LZW
     * compression.
     * @param encoding     "-H" for Huffman coding and "-L" for LZW compression
     * @param testFilePath String path to file which is to be tested.
     * @return long array with following data:
     *      {compressed size in bytes, compression time in ms, decompression time in ms}
     */
    private long[] testSingle(String encoding, String testFilePath) {
        long[] testResults = new long[3];
        File compressed = new File(this.testFileFolder.getPath() + "/compressed");

        long alku = System.currentTimeMillis();
        Main.main(new String[]{
            encoding, "-" + Main.INPUT, testFilePath, "-" + Main.OUTPUT, compressed.getPath()});
        long loppu = System.currentTimeMillis();

        testResults[0] = compressed.length();
        testResults[1] = loppu - alku;
        File decompressed = new File(this.testFileFolder.getPath() + "/decompressed");

        alku = System.currentTimeMillis();
        Main.main(new String[]{"-" + Main.DECOMPRESS, "-" + Main.INPUT,
            compressed.getPath(), "-" + Main.OUTPUT, decompressed.getPath()});
        loppu = System.currentTimeMillis();

        testResults[2] = loppu - alku;
        compressed.delete();
        decompressed.delete();

        return testResults;
    }

    private long[] testSingleLzw64k(String[] encoding, String testFilePath) {
        long[] testResults = new long[3];
        File compressed = new File(this.testFileFolder.getPath() + "/compressed");

        long alku = System.currentTimeMillis();
        Main.main(new String[]{encoding[0], encoding[1],
            "-" + Main.INPUT, testFilePath, "-" + Main.OUTPUT, compressed.getPath()});
        long loppu = System.currentTimeMillis();

        testResults[0] = compressed.length();
        testResults[1] = loppu - alku;
        File decompressed = new File(this.testFileFolder.getPath() + "/decompressed");

        alku = System.currentTimeMillis();
        Main.main(new String[]{"-" + Main.DECOMPRESS, "-" + Main.INPUT,
            compressed.getPath(), "-" + Main.OUTPUT, decompressed.getPath()});
        loppu = System.currentTimeMillis();

        testResults[2] = loppu - alku;
        compressed.delete();
        decompressed.delete();

        return testResults;
    }

    private File[] sortFileArray(File[] testFiles) {
        for (int i = 1; i < testFiles.length; i++) {
            File current = testFiles[i];
            int j = i - 1;
            while (j >= 0 && testFiles[j].getName().compareTo(current.getName()) > 0) {
                testFiles[j + 1] = testFiles[j];
                j--;
            }
            testFiles[j + 1] = current;
        }
        return testFiles;
    }

}
