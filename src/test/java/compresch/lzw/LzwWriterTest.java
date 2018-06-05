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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LzwWriterTest {

    private File testFile;
    private final String testFilePath = "test.txt";

    @Before
    public void setUp() {
        this.testFile = new File(this.testFilePath);
    }

    @After
    public void tearDown() {
        if (this.testFile.exists()) {
            assertTrue(testFile.delete());
        }
    }

    @Test
    public void constructLzwWriterTest() {
        try {
            new LzwWriter(null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        } catch (FileNotFoundException fnfe) {
            fail("FileNotFoundException thrown but not expected");
        }
        try {
            LzwWriter writer = new LzwWriter(this.testFilePath);
            assertNotNull(writer);
            writer.close();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @Test
    public void writeTest() {
        try {
            LzwWriter writer = new LzwWriter((this.testFilePath));
            int[] numbersToWrite =
                new int[]{0x471, 0xdb9, 0x331, 0xa8d, 0x6cc, 0x344, 0x70f, 0xbfe};
            for (int i : numbersToWrite) {
                writer.write(i);
            }
            writer.close();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
        try {
            InputStream input = new BufferedInputStream(new FileInputStream(this.testFile));
            int[] numbersExpected =
                new int[]{0x47, 0x1d, 0xb9, 0x33, 0x1a, 0x8d, 0x6c, 0xc3, 0x44, 0x70, 0xfb, 0xfe};
            int[] numbersRead = new int[numbersExpected.length];
            for (int i = 0; i < numbersExpected.length; i++) {
                numbersRead[i] = input.read();
            }
            assertArrayEquals(numbersExpected, numbersRead);
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @Test
    public void writeIllegalTest() {
        int[] illegalNumbers = new int[]{-1, 4096};
        for (int i : illegalNumbers) {
            try {
                LzwWriter writer = new LzwWriter(this.testFilePath);
                writer.write(i);
                fail("expected IllegalArgumentException");
            } catch (IOException ioe) {
                fail("IOException thrown but not expected");
            } catch (IllegalArgumentException iae) {
                assert true;
            }
        }
    }
}
