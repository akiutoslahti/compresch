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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class MainTest {

    @Test
    public void encodeDecodeTests() {
        encodeDecodeTest("-H");
        encodeDecodeTest("-L");
    }

    private void encodeDecodeTest(String encoding) {
        try {
            File testFile = downloadBible();
            File compressed = new File("compressed");
            File decompressed = new File("decompressed");
            Main.main(new String[]{encoding, testFile.getName(), compressed.getName()});
            Main.main(new String[]{"-D", compressed.getName(), decompressed.getName()});
            assertTrue(checkDiff(testFile, decompressed));
            deleteFile(testFile);
        } catch (IOException ioe) {
            fail();
        }
    }

    private File downloadBible() throws IOException {
        File bibleFile = new File("testBible.txt");
        URL bibleUrl = new URL("http://www.gutenberg.org/cache/epub/10/pg10.txt");
        FileUtils.copyURLToFile(bibleUrl, bibleFile, 1000, 1000);
        return bibleFile;
    }

    private void deleteFile(File file) {
        assertTrue(file.delete());
    }

    private boolean checkDiff(File inputFile, File decompressedFile) {
        if (inputFile.length() != decompressedFile.length()) {
            return false;
        }
        try {
            InputStream orig = new BufferedInputStream(new FileInputStream(inputFile));
            InputStream copy = new BufferedInputStream(new FileInputStream(decompressedFile));
            while (true) {
                int origByte = orig.read();
                int copyByte = copy.read();
                if (origByte != copyByte) {
                    return false;
                }
                if (origByte == -1) {
                    break;
                }
            }
        } catch (IOException ioe) {
            fail("IOException thrown but not expected");
        }
        return true;
    }

}