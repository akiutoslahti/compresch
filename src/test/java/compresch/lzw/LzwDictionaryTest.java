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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class LzwDictionaryTest {

    @Test
    public void constructLzwDictionaryTest() {
        LzwDictionary dictionary = new LzwDictionary();
        assertNotNull(dictionary);
        assertEquals("A", dictionary.getSymbol((int)('A')));
        assertEquals((int)('A'), dictionary.getCodeword("A"));
    }

    @Test
    public void addEntryTest() {
        LzwDictionary dictionary = new LzwDictionary();
        assertTrue(dictionary.addEntry("AYBABTU"));
        assertEquals("AYBABTU", dictionary.getSymbol(256));
        assertEquals(256, dictionary.getCodeword("AYBABTU"));
        for (int i = 257; i < 4095; i++) {
            assertTrue(dictionary.addEntry("AYBABTU-" + i));
        }
        assertFalse(dictionary.addEntry("AYBABTU-AGAIN"));
        assertEquals("AYBABTU-4094", dictionary.getSymbol(4094));
        assertEquals(4093, dictionary.getCodeword("AYBABTU-4093"));
    }

    @Test
    public void getSymbolTest() {
        LzwDictionary dictionary = new LzwDictionary();
        for (int i = 0; i < 256; i++) {
            assertEquals((char)(i) + "",dictionary.getSymbol(i));
        }
        try {
            dictionary.getSymbol(-1);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            assert true;
        }
        try {
            dictionary.getSymbol(256);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            assert true;
        }
    }

    @Test
    public void getCodewordTest() {
        LzwDictionary dictionary = new LzwDictionary();
        for (int i = 0; i < 256; i++) {
            assertEquals(i, dictionary.getCodeword((char)(i) + ""));
        }
        assertEquals(-1, dictionary.getCodeword("STILL-AYBABTU"));
    }

}