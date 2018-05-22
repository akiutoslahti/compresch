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

package compresch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.junit.Test;

public class MainTest {

    @Test
    public void encodeDecodeTests() {
        encodeDecodeTest("-H");
        encodeDecodeTest("-L");
    }

    private void encodeDecodeTest(String encoding) {
        File inputFile = new File("test.txt");
        File compressedFile = new File("test.txt.compressed");
        File decompressedFile = new File("test.txt.decompressed");
        try {
            PrintWriter writer = new PrintWriter(inputFile);
            String expected = "AYBABTU ONCE MORE\n";
            writer.write(expected);
            writer.close();
            Main.main(new String[]{encoding, inputFile.getName(), compressedFile.getName()});
            Main.main(new String[]{"-D", compressedFile.getName(), decompressedFile.getName()});
            InputStream input = new BufferedInputStream(new FileInputStream(decompressedFile));
            StringBuilder builder = new StringBuilder();
            int readBuffer;
            while ((readBuffer = input.read()) != -1) {
                builder.append((char)(readBuffer));
            }
            input.close();
            assertEquals(expected, builder.toString());
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
        assertTrue(inputFile.delete());
        assertTrue(compressedFile.delete());
        assertTrue(decompressedFile.delete());
    }

}