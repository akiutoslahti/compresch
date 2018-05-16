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

package compresch.huff;

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

public class HuffmanEncoderTest {

    private File testInputFile;
    private File testOutputFile;

    @Before
    public void setUp() {
        this.testInputFile = new File("test.txt");
        this.testOutputFile = new File("output.txt");
        try {
            PrintWriter output = new PrintWriter(this.testInputFile);
            output.write("Pretend this is something to be encoded.\n");
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
    public void constructHuffmanEncoderNullTest() {
        try {
            new HuffmanEncoder(null, null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            new HuffmanEncoder(this.testInputFile, null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            new HuffmanEncoder(null, this.testOutputFile);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
    }

    @Test
    public void constructHuffmanEncoderTest() {
        HuffmanEncoder huffmanEncoder = new HuffmanEncoder(this.testInputFile, this.testOutputFile);
        assertNotNull(huffmanEncoder);
    }

    @Test
    public void encodeFailTest() {
        HuffmanEncoder huffmanEncoder =
            new HuffmanEncoder(new File("notExists"), this.testOutputFile);
        try {
            huffmanEncoder.encode();
            fail("expected IOException");
        } catch (IOException ioe) {
            assert true;
        }
    }
}
