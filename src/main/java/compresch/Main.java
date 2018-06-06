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

    public static final String DECOMPRESS = "d";
    public static final String HUFFMAN = "h";
    public static final String TEST = "t";
    public static final String INPUT = "i";
    public static final String LZW = "l";
    public static final String HELP = "help";
    public static final String OUTPUT = "o";

    /**
     * Compresses/decompresses input file to output file depending on given arguments.
     * @param args [-D/-H/-L] [input] [output] or [-h]
     */
    public static void main(String[] args) {
        Options options = buildOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmdLine = parser.parse(options, args);

            if (cmdLine.getOptions().length == 3 && !cmdLine.hasOption(HELP)
                && cmdLine.hasOption(INPUT) && cmdLine.hasOption(OUTPUT)) {

                String inputPath = cmdLine.getOptionValue(INPUT);
                String outputPath = cmdLine.getOptionValue(OUTPUT);

                if (cmdLine.hasOption(HUFFMAN)) {
                    HuffmanEncoder.encode(inputPath, outputPath);
                }

                if (cmdLine.hasOption(LZW)) {
                    String optionalArgument = cmdLine.getOptionValue(LZW, "12");
                    int codewordlength = Integer.parseInt(optionalArgument);
                    if (codewordlength < 9 || codewordlength > 16) {
                        throw new IllegalArgumentException(
                            "LZW codeword length not in valid range: [9, 16]");
                    }
                    LzwEncoder.encode(inputPath, outputPath, codewordlength);
                }

                if (cmdLine.hasOption(DECOMPRESS)) {
                    String encoding = EncodingChecker.readEncoding(inputPath);
                    if (encoding.equals("HUF")) {
                        HuffmanDecoder.decode(inputPath, outputPath);
                    }
                    if (encoding.equals("LZW")) {
                        int codewordLength = EncodingChecker.readLzwCodewordLength(inputPath);
                        LzwDecoder.decode(inputPath, outputPath, codewordLength);
                    }
                }

                if (cmdLine.hasOption(TEST)) {
                    PerformanceTester tester = new PerformanceTester(inputPath, outputPath);
                    tester.testAll();
                }

            } else if (args.length == 1 && cmdLine.hasOption(HELP)) {
                printHelp(options);
            } else {
                System.out.println("Invalid arguments. Use --help to print help");
            }

        } catch (NumberFormatException nfe) {
            System.out.println("Invalid argument for option: '-" + LZW + "'");
        } catch (ParseException | IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(
            "java -jar compresch [OPTION] [-i inputfile] [-o outputfile]", options);
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

        options.addOption(Option.builder(null)
            .longOpt(HELP)
            .desc("print this message")
            .build());

        options.addOption(Option.builder(INPUT)
            .longOpt("input-file")
            .desc("input file/folder")
            .hasArg()
            .argName("input file")
            .build());

        options.addOption(Option.builder(LZW)
            .longOpt("lempel-ziv-welch")
            .desc("compress file using Lempel-Ziw-Welch\n"
                + "- optional argument: codeword length in range [9, 16] ")
            .optionalArg(true)
            .numberOfArgs(1)
            .type(Integer.class)
            .argName("codeword length")
            .build());

        options.addOption(Option.builder(OUTPUT)
            .longOpt("ouput-file")
            .desc("ouput file")
            .hasArg()
            .argName("output file")
            .build());

        options.addOption(Option.builder(TEST)
            .longOpt("test")
            .desc("run performance tests\n - input: folder\n - output: markdown")
            .build());

        return options;
    }

}
