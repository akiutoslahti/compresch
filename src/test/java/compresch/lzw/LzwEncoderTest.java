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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LzwEncoderTest {

    private File testInputFile;
    private File testOutputFile;
    private final String testInputFilePath = "test.txt";
    private final String testOutputFilePath = "output.txt";

    @Before
    public void setUp() {
        this.testInputFile = new File(this.testInputFilePath);
        this.testOutputFile = new File(this.testOutputFilePath);
        try {
            PrintWriter output = new PrintWriter(this.testInputFile);
            output.write("Pretending this is something to be encoded.\n");
            output.close();
        } catch (FileNotFoundException fnfe) {
            fail("FileNotFoundException thrown but not expected");
        }
    }

    @After
    public void tearDown() {
        assertTrue(this.testInputFile.delete());
        if (this.testOutputFile.exists()) {
            assertTrue(this.testOutputFile.delete());
        }
    }

    @Test
    public void constructLzwEncoderNullTest() {
        try {
            new LzwEncoder(null, null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            new LzwEncoder(this.testInputFilePath, null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            new LzwEncoder(null, this.testOutputFilePath);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
    }

    @Test
    public void constructLzwEncoderTest() {
        LzwEncoder lzwEncoder = new LzwEncoder(
            this.testInputFilePath, this.testOutputFilePath);
        assertNotNull(lzwEncoder);
    }

    @Test
    public void encodeFailTest() {
        LzwEncoder lzwEncoder =
            new LzwEncoder("notExists", this.testOutputFilePath);
        try {
            lzwEncoder.encode();
            fail("expected IOException");
        } catch (IOException ioe) {
            assert true;
        }
    }

}