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

    @Before
    public void setUp() {
        this.inputFile = new File("testInput");
        this.compressedFile = new File("compressed");
        this.decompressedFile = new File("decompressed");
    }

    @After
    public void tearDown() {
        assertTrue(this.inputFile.delete());
        assertTrue(this.compressedFile.delete());
        assertTrue(this.decompressedFile.delete());
    }

    @Test
    public void huffmanRandomBytesTest() {
        Encoder encoder = new HuffmanEncoder(this.inputFile, this.compressedFile);
        Decoder decoder = new HuffmanDecoder(this.compressedFile, this.decompressedFile);
        randomBytesTest(encoder, decoder);
    }

    @Test
    public void huffmanTextTest() {
        Encoder encoder = new HuffmanEncoder(this.inputFile, this.compressedFile);
        Decoder decoder = new HuffmanDecoder(this.compressedFile, this.decompressedFile);
        textTest(encoder, decoder);
    }

    @Test
    public void lzwRandomBytesTest() {
        Encoder encoder = new LzwEncoder(this.inputFile, this.compressedFile);
        Decoder decoder = new LzwDecoder(this.compressedFile, this.decompressedFile);
        randomBytesTest(encoder, decoder);
    }

    @Test
    public void lzwTextTest() {
        Encoder encoder = new HuffmanEncoder(this.inputFile, this.compressedFile);
        Decoder decoder = new HuffmanDecoder(this.compressedFile, this.decompressedFile);
        textTest(encoder, decoder);
    }

    private void randomBytesTest(Encoder encoder, Decoder decoder) {
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
        assertTrue(encodeDecodeTest(encoder, decoder));
    }

    private void textTest(Encoder encoder, Decoder decoder) {
        try {
            PrintWriter output = new PrintWriter(this.inputFile);
            output.write("Aki was here!\n");
            output.append("and still is...\n");
            output.append("\t... or is he?\n");
            output.close();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
        assertTrue(encodeDecodeTest(encoder, decoder));
    }

    private boolean encodeDecodeTest(Encoder encoder, Decoder decoder) {
        compress(encoder);
        decompress(decoder);
        return checkDiff();
    }

    private void compress(Encoder encoder) {
        try {
            encoder.encode();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    private void decompress(Decoder decoder) {
        try {
            decoder.decode();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    private boolean checkDiff() {
        if (this.inputFile.length() != this.decompressedFile.length()) {
            return false;
        }
        try {
            InputStream orig = new BufferedInputStream(new FileInputStream(this.inputFile));
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