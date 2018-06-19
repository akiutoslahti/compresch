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
 * Test 'script' to test single file with all possible dictionary sizes.
 * Produces a markdown formatted report.
 */
public class LzwDictionarySizeTester {

    private File testFile;
    private File testReport;
    private PrintWriter writer;
    private DecimalFormatSymbols symbols;
    private DecimalFormat df;

    /**
     * Constructs LzwDictionarySizeTester.
     * @param testFilePath   File path, where file to be tested is in.
     * @param testReportPath File path, where markdown report is saved.
     * @throws NullPointerException     if either one of parameters is null.
     * @throws IllegalArgumentException if first parameter is not a folder.
     */
    public LzwDictionarySizeTester(String testFilePath, String testReportPath) {
        Objects.requireNonNull(testFilePath);
        Objects.requireNonNull(testReportPath);
        this.testFile = new File(testFilePath);
        this.testReport = new File(testReportPath);
        this.symbols = new DecimalFormatSymbols();
        this.symbols.setGroupingSeparator(' ');
        this.df = new DecimalFormat();
        this.df.setDecimalFormatSymbols(symbols);
        this.df.setGroupingSize(3);
        this.df.setMaximumFractionDigits(0);
    }

    /**
     * Runs compression testing to testFile with all possible LZW dictionary sizes.
     * @throws FileNotFoundException if File testReport cannot be opened for writing.
     */
    public void test() throws FileNotFoundException {
        this.writer = new PrintWriter(this.testReport);
        this.writer.println("## Dictionary size testing results");
        this.writer.println("Testfile: " + this.testFile.getName()
            + ", size: " + this.df.format(this.testFile.length()) + "\n");
        this.writer.println("Dictionary size | compressed size (bytes) "
            + "| compress ratio | compression time (ms) | decompression time (ms)");
        this.writer.println("---: | ---: | ---: | ---: | ---:");
        testAllDictionarySizes();
        this.writer.close();
    }

    private void testAllDictionarySizes() {
        for (int i = 9; i < 17; i++) {
            long[] testResults = testSingleDictionarySize(String.valueOf(i));
            this.writer.println(this.df.format((int) Math.pow(2, i)) + " | "
                + this.df.format(testResults[0]) + " | "
                + (int) Math.round((1.0 * testResults[0] / testFile.length() * 100)) + "% | "
                + this.df.format(testResults[1]) + " | " + this.df.format(testResults[2]));
        }
    }

    private long[] testSingleDictionarySize(String dictSize) {
        long[] testResults = new long[3];
        File compressed = new File(this.testFile.getPath() + ".compressed");

        long alku = System.currentTimeMillis();
        Main.main(new String[]{"-" + Main.LZW, dictSize,
            "-" + Main.INPUT, this.testFile.getPath(), "-" + Main.OUTPUT, compressed.getPath()});
        long loppu = System.currentTimeMillis();

        testResults[0] = compressed.length();
        testResults[1] = loppu - alku;
        File decompressed = new File(this.testFile.getPath() + ".decompressed");

        alku = System.currentTimeMillis();
        Main.main(new String[]{"-" + Main.DECOMPRESS, "-" + Main.INPUT,
            compressed.getPath(), "-" + Main.OUTPUT, decompressed.getPath()});
        loppu = System.currentTimeMillis();

        testResults[2] = loppu - alku;
        compressed.delete();
        decompressed.delete();
        return testResults;
    }

}
