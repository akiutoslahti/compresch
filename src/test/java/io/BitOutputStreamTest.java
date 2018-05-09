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

package io;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import org.junit.Test;

public class BitOutputStreamTest {

    @Test
    public void newBitOutPutStreamTest() {
        try {
            new BitOutputStream(null);
            fail();
        } catch (NullPointerException npe) {
        }
        try {
            File testFile = new File("test.txt");
            new BitOutputStream(new BufferedOutputStream(new FileOutputStream(testFile)));
            testFile.delete();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error: " + fnfe);
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
            for (int i = 0; i < numbers.length; i++) {
                output.write(numbers[i]);
            }
            output.close();
            InputStream input = new BufferedInputStream(new FileInputStream(testFile));
            numberRead = input.read();
            input.close();
            testFile.delete();
            assertEquals(170, numberRead);
        } catch (Exception e) {

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
            testFile.delete();
            assertEquals(168, numberRead);
        } catch (Exception e) {

        }
    }

    @Test
    public void writeIllegalArgumentTest() {
        File testFile = new File("test.txt");
        try {
            BitOutputStream output = new BitOutputStream(new BufferedOutputStream(
                    new FileOutputStream(testFile)));
            output.write(2);
            fail();
        } catch (Exception e) {

        }
        testFile.delete();
    }

    @Test
    public void writeByteTest() {
        try {
            File testFile = new File("test.txt");
            BitOutputStream output = new BitOutputStream(new BufferedOutputStream(
                    new FileOutputStream(testFile)));
            byte[] numbers = new byte[]{7, 2, 5, 4, 9};
            byte[] numbersRead = new byte[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                output.writeByte((numbers[i]));
            }
            output.close();
            InputStream input = new BufferedInputStream(new FileInputStream(testFile));
            for (int i = 0; i < numbers.length; i++) {
                numbersRead[i] = (byte)(input.read());
            }
            input.close();
            testFile.delete();
            assertEquals(Arrays.toString(numbers), Arrays.toString(numbersRead));
        } catch (Exception e) {

        }
    }


}