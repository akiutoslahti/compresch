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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import compresch.huff.HuffmanDecoder;
import compresch.huff.HuffmanEncoder;
import compresch.lzw.LzwDecoder;
import compresch.lzw.LzwEncoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EncodeDecodeTest {

    private File inputFile;
    private File compressedFile;
    private File decompressedFile;
    private final String inputFilePath = "testInput";
    private final String compressedFilePath = "compressed";
    private final String decompressedFilePath = "decompressed";
    private final String biblePath = "./src/test/resources/bible.txt";

    @Before
    public void setUp() {
        this.inputFile = new File(this.inputFilePath);
        this.compressedFile = new File(this.compressedFilePath);
        this.decompressedFile = new File(this.decompressedFilePath);
    }

    @After
    public void tearDown() {
        if (this.inputFile.exists()) {
            assertTrue(this.inputFile.delete());
        }
        assertTrue(this.compressedFile.delete());
        assertTrue(this.decompressedFile.delete());
    }

    @Test
    public void huffmanRandomBytesTest() {
        createRandomInput();
        try {
            HuffmanEncoder.encode(this.inputFilePath, this.compressedFilePath);
            HuffmanDecoder.decode(this.compressedFilePath, this.decompressedFilePath);
        } catch (IOException ioe) {
            fail("IOEXception thrown but not expected");
        }
        assertTrue(checkDiff(null));
    }

    @Test
    public void lzwRandomBytesTest() {
        createRandomInput();
        try {
            LzwEncoder.encode(this.inputFilePath, this.compressedFilePath, 12);
            LzwDecoder.decode(this.compressedFilePath, this.decompressedFilePath, 12);
        } catch (IOException ioe) {
            fail("IOEXception thrown but not expected");
        }
        assertTrue(checkDiff(null));
    }

    private void createRandomInput() {
        Random rng = new Random();
        try {
            OutputStream output = new BufferedOutputStream(new FileOutputStream(this.inputFile));
            for (int i = 0; i < 524288; i++) {
                output.write(rng.nextInt(256));
            }
            output.close();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @Test
    public void huffmanTextTest() {
        createTextInput();
        try {
            HuffmanEncoder.encode(this.inputFilePath, this.compressedFilePath);
            HuffmanDecoder.decode(this.compressedFilePath, this.decompressedFilePath);
        } catch (IOException ioe) {
            fail("IOEXception thrown but not expected");
        }
        assertTrue(checkDiff(null));
    }

    @Test
    public void lzwTextTest() {
        createTextInput();
        try {
            LzwEncoder.encode(this.inputFilePath, this.compressedFilePath, 12);
            LzwDecoder.decode(this.compressedFilePath, this.decompressedFilePath, 12);
        } catch (IOException ioe) {
            fail("IOEXception thrown but not expected");
        }
        assertTrue(checkDiff(null));
    }

    private void createTextInput() {
        try {
            PrintWriter output = new PrintWriter(this.inputFile);
            output.write("Aki was here!\n");
            output.append("and still is...\n");
            output.append("\t... or is he?\n");
            output.close();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @Test
    public void huffmanBibleTest() {
        try {
            HuffmanEncoder.encode(this.biblePath, this.compressedFilePath);
            HuffmanDecoder.decode(this.compressedFilePath, this.decompressedFilePath);
        } catch (IOException ioe) {
            fail("IOEXception thrown but not expected");
        }
        assertTrue(checkDiff(this.biblePath));
    }

    @Test
    public void lzwBibleTest() {
        for (int i = 9; i < 17; i++) {
            try {
                LzwEncoder.encode(this.biblePath, this.compressedFilePath, i);
                LzwDecoder.decode(this.compressedFilePath, this.decompressedFilePath, i);
            } catch (IOException ioe) {
                fail("IOEXception thrown but not expected");
            }
            assertTrue(checkDiff(this.biblePath));
        }
    }

    private boolean checkDiff(String alternateInputPath) {
        File testFile;
        if (alternateInputPath != null) {
            testFile = new File(alternateInputPath);
        } else {
            testFile = this.inputFile;
        }
        if (testFile.length() != this.decompressedFile.length()) {
            return false;
        }
        try {
            InputStream orig = new BufferedInputStream(new FileInputStream(testFile));
            InputStream copy = new BufferedInputStream(new FileInputStream(this.decompressedFile));
            while (true) {
                int origByte = orig.read();
                int copyByte = copy.read();
                if (origByte != copyByte) {
                    return false;
                }
                if (origByte == -1) {
                    break;
                }
            }
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
        return true;
    }

}
