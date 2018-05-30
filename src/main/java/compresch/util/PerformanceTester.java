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
import java.util.Objects;

public class PerformanceTester {

    private File testFileFolder;
    private File testReport;
    private PrintWriter writer;

    public PerformanceTester(File testFileFolder, File testReport) {
        Objects.requireNonNull(testFileFolder);
        Objects.requireNonNull(testReport);
        if (!testFileFolder.isDirectory()) {
            throw new IllegalArgumentException("Test file location is not a folder");
        }
        this.testFileFolder = testFileFolder;
        this.testReport = testReport;
    }

    public void testAll() throws FileNotFoundException {
        this.writer = new PrintWriter(this.testReport);
        this.writer.println("#Performance testing results#");
        File[] testFiles = this.testFileFolder.listFiles();
        if (testFiles.length == 0) {
            throw new IllegalArgumentException("Test file location is empty");
        }
        testHuffman(testFiles);
        testLzw(testFiles);
        this.writer.close();
    }

    private void testHuffman(File[] testFiles) {
        writeTableHeader("Huffman coding");
        for (File testFile : testFiles) {
            long[] testResults = testSingle("-H", testFile.getPath());
            this.writer.println(testFile.getName() + " | " + testFile.length() + " | "
                + testResults[0] + " | "
                + (int) (1.0 * testResults[0] / testFile.length() * 100) + "% | "
                + testResults[1] + " | " + testResults[2]);
        }
        this.writer.println();
    }

    private void testLzw(File [] testFiles) {
        writeTableHeader("Lempel-Ziv-Welch");
        for (File testFile : testFiles) {
            long[] testResults = testSingle("-L", testFile.getPath());
            this.writer.println(testFile.getName() + " | " + testFile.length() + " | "
                + testResults[0] + " | "
                + (int) (1.0 * testResults[0] / testFile.length() * 100) + "% | "
                + testResults[1] + " | " + testResults[2]);
        }
        this.writer.println();
    }

    private void writeTableHeader(String header) {
        this.writer.println("##" + header + "##");
        this.writer.println("Testfile | input size (bytes) | compressed size (bytes) "
            + "| compress ratio | compression time (ms) | decompression time (ms)");
        this.writer.println("--- | ---: | ---: | ---: | ---: | ---:");
    }

    private long[] testSingle(String encoding, String testFilePath) {
        long[] testResults = new long[3];
        File compressed = new File(this.testFileFolder.getPath() + "/compressed");

        long alku = System.currentTimeMillis();
        Main.main(new String[]{encoding, testFilePath, compressed.getPath()});
        long loppu = System.currentTimeMillis();

        testResults[0] = compressed.length();
        testResults[1] = loppu - alku;
        File decompressed = new File(this.testFileFolder.getPath() + "/decompressed");

        alku = System.currentTimeMillis();
        Main.main(new String[]{
            "-D", compressed.getPath(), decompressed.getPath()});
        loppu = System.currentTimeMillis();

        testResults[2] = loppu - alku;
        compressed.delete();
        decompressed.delete();

        return testResults;
    }

}
