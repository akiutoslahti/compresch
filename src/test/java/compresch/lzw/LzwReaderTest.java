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

package compresch.lzw;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import compresch.io.BitInputStream;
import compresch.io.BitOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LzwReaderTest {

    private File testFile;
    private int[] numbersExpected;

    @Before
    public void setUp() {
        this.testFile = new File("test.txt");
        this.numbersExpected = new int[10];
        Random rng = new Random();
        for (int i = 0; i < this.numbersExpected.length; i++) {
            this.numbersExpected[i] = (rng.nextInt(4096));
        }
        try {
            BitOutputStream output =
                new BitOutputStream(new BufferedOutputStream(new FileOutputStream(this.testFile)));
            for (Integer i : this.numbersExpected) {
                for (int j = 11; j >= 0; j--) {
                    output.write((i >> j) & 1);
                }
            }
            output.close();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @After
    public void tearDown() {
        assertTrue(this.testFile.delete());
    }

    @Test
    public void constructLzwReaderTest() {
        try {
            new LzwReader(null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            new LzwReader(new BitInputStream(new BufferedInputStream(
                new FileInputStream(new File("notExist")))));
            fail("expected IOException");
        } catch (IOException ioe) {
            assert true;
        }
        try {
            LzwReader reader = new LzwReader(new BitInputStream(
                new BufferedInputStream(new FileInputStream(this.testFile))));
            assertNotNull(reader);
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @Test
    public void readTest() {
        try {
            LzwReader reader = new LzwReader(new BitInputStream(
                new BufferedInputStream(new FileInputStream(this.testFile))));
            int[] numbersRead = new int[this.numbersExpected.length];
            for (int i = 0; i < this.numbersExpected.length; i++) {
                numbersRead[i] = reader.read();
            }
            reader.close();
            assertArrayEquals(this.numbersExpected, numbersRead);
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @Test
    public void readPastEofTest() {
        try {
            LzwReader reader = new LzwReader(new BitInputStream(
                new BufferedInputStream(new FileInputStream(this.testFile))));
            for (int i = 0; i < this.numbersExpected.length + 1; i++) {
                reader.read();
            }
            fail("expected IOException");
        } catch (IOException ioe) {
            assert true;
        }
    }
}
