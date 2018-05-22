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

import compresch.huff.HuffmanDecoder;
import compresch.huff.HuffmanEncoder;
import compresch.lzw.LzwDecoder;
import compresch.lzw.LzwEncoder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    private static final String DECOMPRESS = "D";
    private static final String HUFFMAN = "H";
    private static final String LZW = "L";
    private static final String HELP = "h";

    /**
     * Compresses/decompresses input file to output file depending on given arguments.
     * @param args [-c/-d] [input file] [output file]
     */
    public static void main(String[] args) {
        Options options = buildOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmdLine = parser.parse(options, args);
            if (args.length == 3 && cmdLine.getOptions().length == 1 && !cmdLine.hasOption(HELP)) {
                File inputFile = new File(args[1]);
                File outputFile = new File(args[2]);
                if (!inputFile.exists()) {
                    throw new FileNotFoundException("Input file not found: " + inputFile.getName());
                }

                if (cmdLine.hasOption(HUFFMAN)) {
                    Encoder encoder = new HuffmanEncoder(inputFile, outputFile);
                    encoder.encode();
                }

                if (cmdLine.hasOption(LZW)) {
                    Encoder encoder = new LzwEncoder(inputFile, outputFile);
                    encoder.encode();
                }

                if (cmdLine.hasOption(DECOMPRESS)) {
                    String encoding = readEncoding(inputFile);
                    if (encoding.equals("HUF")) {
                        Decoder decoder = new HuffmanDecoder(inputFile, outputFile);
                        decoder.decode();
                    }
                    if (encoding.equals("LZW")) {
                        Decoder decoder = new LzwDecoder(inputFile, outputFile);
                        decoder.decode();
                    }
                }

            } else if (args.length == 1 && cmdLine.hasOption(HELP)) {
                printHelp(options);
            } else {
                System.out.println("Invalid arguments. Use -h to print help.");
            }

        } catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readEncoding(File inputFile) throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream(inputFile));
        int[] readBytes = new int[3];
        for (int i = 0; i < readBytes.length; i++) {
            readBytes[i] = input.read();
        }
        input.close();
        StringBuilder builder = new StringBuilder();
        for (int i : readBytes) {
            builder.append((char)(i));
        }
        if (builder.toString().equals("HUF") || builder.toString().equals("LZW")) {
            return builder.toString();
        } else {
            throw new UnsupportedEncodingException("Input file doesn't contain valid header.");
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar compresch [OPTION] [INPUT] [OUTPUT]", options);
    }

    private static Options buildOptions() {
        Options options = new Options();

        options.addOption(Option.builder(DECOMPRESS)
            .longOpt("decompress")
            .desc("decompress file")
            .build());

        options.addOption(Option.builder(HUFFMAN)
            .longOpt("huffman-coding")
            .desc("compress file using Huffman coding")
            .build());

        options.addOption(Option.builder(LZW)
            .longOpt("lempel-ziv-welch")
            .desc("compress file using Lempel-Ziw-Welch")
            .build());

        options.addOption(Option.builder(HELP)
            .longOpt("help")
            .desc("print this message")
            .build());

        return options;
    }

}
