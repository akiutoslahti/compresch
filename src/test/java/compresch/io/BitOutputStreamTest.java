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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class BitOutputStreamTest {

    @Test
    public void constructBitOutPutStreamTest() {
        try {
            new BitOutputStream(null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            File testFile = new File("test.txt");
            new BitOutputStream(new BufferedOutputStream(new FileOutputStream(testFile)));
            assertTrue(testFile.delete());
        } catch (FileNotFoundException fnfe) {
            fail("FileNotFoundException thrown but not expected");
        }
    }

    @Test
    public void writeTest() {
        try {
            File testFile = new File("test.txt");
            BitOutputStream output = new BitOutputStream(new BufferedOutputStream(
                    new FileOutputStream(testFile)));
            int[] numbers = new int[]{1, 0, 1, 0, 1, 0, 1, 0};
            int numberRead;
            for (int number : numbers) {
                output.write(number);
            }
            output.close();
            InputStream input = new BufferedInputStream(new FileInputStream(testFile));
            numberRead = input.read();
            input.close();
            assertTrue(testFile.delete());
            assertEquals(170, numberRead);
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @Test
    public void writeIncompleteTest() {
        try {
            File testFile = new File("test.txt");
            BitOutputStream output = new BitOutputStream(new BufferedOutputStream(
                    new FileOutputStream(testFile)));
            int[] numbers = new int[]{1, 0, 1, 0, 1};
            int numberRead;
            for (int i = 0; i < numbers.length; i++) {
                output.write(numbers[i]);
            }
            output.close();
            InputStream input = new BufferedInputStream(new FileInputStream(testFile));
            numberRead = input.read();
            input.close();
            assertTrue(testFile.delete());
            assertEquals(168, numberRead);
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @Test
    public void writeIllegalArgumentTest() {
        File testFile = new File("test.txt");
        try {
            BitOutputStream output = new BitOutputStream(new BufferedOutputStream(
                    new FileOutputStream(testFile)));
            output.write(2);
            fail("expected IllegalArgumentException");
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        } catch (IllegalArgumentException iae) {
            assert true;
        }
        assertTrue(testFile.delete());
    }

    @Test
    public void writeByteTest() {
        try {
            File testFile = new File("test.txt");
            BitOutputStream output = new BitOutputStream(new BufferedOutputStream(
                    new FileOutputStream(testFile)));
            byte[] numbers = new byte[]{46, 127, 99, 12, 6};
            byte[] numbersRead = new byte[numbers.length];
            for (byte number : numbers) {
                output.writeByte((number));
            }
            output.close();
            InputStream input = new BufferedInputStream(new FileInputStream(testFile));
            for (int i = 0; i < numbers.length; i++) {
                numbersRead[i] = (byte)(input.read());
            }
            input.close();
            assertTrue(testFile.delete());
            assertArrayEquals(numbers, numbersRead);
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

}