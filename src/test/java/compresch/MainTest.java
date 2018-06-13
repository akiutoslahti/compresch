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

package compresch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    private File inputFile;
    private File compressedFile;
    private File decompressedFile;
    private String expected;

    @Before
    public void setUp() {
        this.inputFile = new File("test.txt");
        this.compressedFile = new File("test.txt.compressed");
        this.decompressedFile = new File("test.txt.decompressed");
        try {
            PrintWriter writer = new PrintWriter(inputFile);
            this.expected = "AYBABTU ONCE MORE\n";
            writer.write(expected);
            writer.close();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @After
    public void tearDown() {
        assertTrue(inputFile.delete());
        assertTrue(compressedFile.delete());
        assertTrue(decompressedFile.delete());
    }

    @Test
    public void huffmanTest() {
        Main.main(new String[]{"-" + Main.HUFFMAN, "-" + Main.INPUT, inputFile.getName(),
            "-" + Main.OUTPUT, compressedFile.getName()});
        Main.main(new String[]{"-" + Main.DECOMPRESS, "-" + Main.INPUT, compressedFile.getName(),
            "-" + Main.OUTPUT, decompressedFile.getName()});
        checkDiff();
    }

    @Test
    public void lzwDefaultTest() {
        Main.main(new String[]{"-" + Main.LZW, "-" + Main.INPUT, inputFile.getName(),
            "-" + Main.OUTPUT, compressedFile.getName()});
        Main.main(new String[]{"-" + Main.DECOMPRESS, "-" + Main.INPUT, compressedFile.getName(),
            "-" + Main.OUTPUT, decompressedFile.getName()});
        checkDiff();
    }

    @Test
    public void lzwWithArgumentTest() {
        Main.main(
            new String[]{"-" + Main.LZW, "16", "-" + Main.INPUT, inputFile.getName(),
                "-" + Main.OUTPUT, compressedFile.getName()});
        Main.main(
            new String[]{"-" + Main.DECOMPRESS, "-" + Main.INPUT, compressedFile.getName(),
                "-" + Main.OUTPUT, decompressedFile.getName()});
        checkDiff();
    }

    private void checkDiff() {
        try {
            InputStream input = new BufferedInputStream(new FileInputStream(decompressedFile));
            StringBuilder builder = new StringBuilder();
            int readBuffer;
            while ((readBuffer = input.read()) != -1) {
                builder.append((char) (readBuffer));
            }
            input.close();
            assertEquals(expected, builder.toString());
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

}