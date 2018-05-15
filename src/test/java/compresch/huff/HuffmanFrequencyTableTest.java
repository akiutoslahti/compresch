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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HuffmanFrequencyTableTest {

    private File testFile;

    @Before
    public void setUp() {
        this.testFile = new File("test.txt");
        try {
            PrintWriter output = new PrintWriter(this.testFile);
            output.println("AYBABTU");
            output.close();
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
    }

    @After
    public void tearDown() {
        assertTrue(testFile.delete());
    }

    @Test
    public void constructHuffmanFrequencyTable() {
        HuffmanFrequencyTable freqTable = new HuffmanFrequencyTable();
        assertNotNull(freqTable);
    }

    @Test
    public void buildFreqTableFileNotExistsTest() {
        try {
            HuffmanFrequencyTable frequencyTable = new HuffmanFrequencyTable();
            frequencyTable.buildFreqTable(new File("notExist"));
            fail("expected IOException");
        } catch (IOException ioe) {
            assert true;
        }
    }

    @Test
    public void buildFreqTableTest() {
        HuffmanFrequencyTable frequencyTable = new HuffmanFrequencyTable();
        try {
            frequencyTable.buildFreqTable(this.testFile);
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
        assertEquals(2, frequencyTable.getFrequency((int)('A')));
        assertEquals(2, frequencyTable.getFrequency((int)('B')));
        assertEquals(1, frequencyTable.getFrequency((int)('T')));
        assertEquals(1, frequencyTable.getFrequency((int)('U')));
        assertEquals(1, frequencyTable.getFrequency((int)('Y')));
        assertEquals(1, frequencyTable.getFrequency((int)('\n')));
        assertEquals(1, frequencyTable.getFrequency(256));
        assertEquals(-1, frequencyTable.getFrequency(257));
        assertEquals(-1, frequencyTable.getFrequency(-1));
    }

}