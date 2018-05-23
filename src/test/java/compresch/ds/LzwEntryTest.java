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

package compresch.ds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class LzwEntryTest {

    @Test
    public void constructLzwEntryTest() {
        try {
            new LzwEntry(null, 123);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        LzwEntry entry = new LzwEntry("AYBABTU", 123);
        assertNotNull(entry);
    }

    @Test
    public void gettersSettersTest() {
        LzwEntry entry = new LzwEntry("AYBABTU", 123);
        assertEquals("AYBABTU", entry.getKey());
        assertEquals(123, entry.getValue());
        assertNull(entry.getNextEntry());
        LzwEntry next = new LzwEntry("nexttest", 321);
        entry.setNextEntry(next);
        assertEquals(next, entry.getNextEntry());
    }

    @Test
    public void hashTest() {
        String testString = "AYBABTU";
        int hash = 0;
        for (int i = 0; i < testString.length(); i++) {
            hash = hash * 31 + testString.charAt(i);
        }
        hash += testString.length();
        LzwEntry entry = new LzwEntry("AYBABTU", 123);
        assertEquals(hash, entry.getHash());
        assertEquals(hash, entry.getHash());
    }

}