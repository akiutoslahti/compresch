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
import compresch.io.EncodingChecker;
import compresch.lzw.LzwDecoder;
import compresch.lzw.LzwEncoder;
import compresch.util.PerformanceTester;
import java.io.IOException;
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
    private static final String TEST = "T";

    /**
     * Compresses/decompresses input file to output file depending on given arguments.
     * @param args [-D/-H/-L] [input] [output] or [-h]
     */
    public static void main(String[] args) {
        Options options = buildOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmdLine = parser.parse(options, args);
            if (args.length == 3 && cmdLine.getOptions().length == 1 && !cmdLine.hasOption(HELP)) {
                String inputPath = args[1];
                String outputPath = args[2];

                if (cmdLine.hasOption(HUFFMAN)) {
                    HuffmanEncoder.encode(inputPath, outputPath);
                }

                if (cmdLine.hasOption(LZW)) {
                    LzwEncoder.encode(inputPath, outputPath);
                }

                if (cmdLine.hasOption(DECOMPRESS)) {
                    String encoding = EncodingChecker.readEncoding(inputPath);
                    if (encoding.equals("HUF")) {
                        HuffmanDecoder.decode(inputPath, outputPath);
                    }
                    if (encoding.equals("LZW")) {
                        LzwDecoder.decode(inputPath, outputPath);
                    }
                }

                if (cmdLine.hasOption(TEST)) {
                    PerformanceTester tester = new PerformanceTester(inputPath, outputPath);
                    tester.testAll();
                }

            } else if (args.length == 1 && cmdLine.hasOption(HELP)) {
                printHelp(options);
            } else {
                System.out.println("Invalid arguments. Use -h to print help.");
            }

        } catch (ParseException | IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
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

        options.addOption(Option.builder(TEST)
            .longOpt("test")
            .desc("run performance tests\n - INPUT: folder\n - OUTPUT: markdown")
            .build());

        return options;
    }

}
