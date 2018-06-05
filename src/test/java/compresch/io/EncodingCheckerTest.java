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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class EncodingCheckerTest {

    @Test
    public void nullFileTest() {
        try {
            EncodingChecker.readEncoding(null);
            fail("NullPointerException expected");
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        } catch (NullPointerException npe) {
            assert true;
        }
    }

    @Test
    public void notExistingTest() {
        try {
            EncodingChecker.readEncoding("notExists.txt");
            fail("IOException expected");
        } catch (IOException ioe) {
            assert true;
        }
    }

    @Test
    public void noValidHeaderTest() {
        String testFilePath = "test.txt";
        try {
            OutputStream output = new BufferedOutputStream(new FileOutputStream(testFilePath));
            String notvalid = "AYBABTU";
            for (int i = 0; i < notvalid.length(); i++) {
                output.write((byte) (notvalid.charAt(i)));
            }
            output.close();
            EncodingChecker.readEncoding(testFilePath);
            fail("UnsupportedEncodingException expected");
        } catch (UnsupportedEncodingException uee) {
            assert true;
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
        assertTrue(new File(testFilePath).delete());
    }

}