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

import compresch.Encoder;
import compresch.io.BitOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LzwEncoder implements Encoder {

    private File inputFile;
    private File outputFile;

    public LzwEncoder(File input, File output) {
        this.inputFile = input;
        this.outputFile = output;
    }

    public void encode() throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream(this.inputFile));
        LzwWriter output = new LzwWriter(new BitOutputStream(
            new BufferedOutputStream(new FileOutputStream(this.outputFile))));
        makeEncode(input, output);
        output.writePseudoEof();
        input.close();
        output.close();
    }

    private void makeEncode(InputStream input, LzwWriter output) throws IOException {
        LzwDictionary dict = new LzwDictionary();
        StringBuilder inputBuffer = new StringBuilder();
        while (true) {
            int readBuffer = input.read();
            if (readBuffer == -1) {
                output.write(dict.getCodeword(inputBuffer.toString()));
                break;
            }
            inputBuffer.append((char) (readBuffer));
            if (dict.getCodeword(inputBuffer.toString()) == -1) {
                dict.addEntry(inputBuffer.toString());
                output.write(dict.getCodeword(inputBuffer.substring(0, inputBuffer.length() - 1)));
                inputBuffer.delete(0, inputBuffer.length() - 1);
            }
        }
    }

}
