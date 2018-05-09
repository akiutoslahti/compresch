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

package compresch.io;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BitInputStreamTest {

    private File testFile;
    private byte[] numbers;

    @Before
    public void setUp() {
        this.testFile = new File("test.txt");
        this.numbers = new byte[]{118, 99, 12, 1, 16};
        try {
            OutputStream output = new BufferedOutputStream(new FileOutputStream(this.testFile));
            for (int i = 0; i < this.numbers.length; i++) {
                output.write(this.numbers[i]);
            }
            output.close();
        } catch (Exception e) {
        }
    }

    @After
    public void tearDown() {
        this.testFile.delete();
    }

    @Test
    public void newBitInputStreamTest() {
        try {
            new BitInputStream(null);
            fail();
        } catch (NullPointerException npe) {
        }
        try {
            new BitInputStream(new BufferedInputStream(new FileInputStream(
                    new File("notfound.txt"))));
            fail();
        } catch (FileNotFoundException fnfe) {
        }
        try {
            new BitInputStream(new BufferedInputStream(new FileInputStream(this.testFile)));
        } catch (FileNotFoundException fnfe) {
        }
    }

    @Test
    public void readTest() {
        try {
            BitInputStream input = new BitInputStream(new BufferedInputStream(
                    new FileInputStream(this.testFile)));
            int[] readByte = new int[8];
            for (int i = 0; i < 8; i++) {
                readByte[i] = input.read();
            }
            int[] expectedByte = new int[]{0, 1, 1, 1, 0, 1, 1, 0};
            input.close();
            assertArrayEquals(expectedByte, readByte);
        } catch (Exception e) {
        }
    }

    @Test
    public void readEndOfFileTest() {
        try {
            BitInputStream input = new BitInputStream(new BufferedInputStream(
                    new FileInputStream(this.testFile)));
            for (int i = 0; i < 40; i++) {
                input.read();
            }
            assertEquals(-1, input.read());
            assertEquals(-1, input.read());
            input.close();
        } catch (Exception e) {
        }
    }

    @Test
    public void readByteTest() {
        try {
            BitInputStream input = new BitInputStream(new BufferedInputStream(
                    new FileInputStream(this.testFile)));
            byte[] inputRead = new byte[this.numbers.length];
            for (int i = 0; i < this.numbers.length; i++) {
                inputRead[i] = (byte)(input.readByte());
            }
            input.close();
            assertArrayEquals(this.numbers, inputRead);
        } catch (Exception e) {
        }
    }

}