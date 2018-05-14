package compresch;

import compresch.huff.HuffmanDecoder;
import compresch.huff.HuffmanEncoder;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("Faulty paramenters.");
        }

        String action = args[0];
        File inputFile = new File(args[1]);
        File outputFile = new File(args[2]);

        if (action.equals("-c")) {
            compress(inputFile, outputFile);
        } else if (action.equals("-d")) {
            decompress(inputFile, outputFile);
        } else {
            throw new IllegalArgumentException("Unknown command.");
        }

    }

    private static void compress(File input, File output) {
        HuffmanEncoder encoder = new HuffmanEncoder(input, output);
        encoder.encode();
    }

    private static void decompress(File input, File output) {
        HuffmanDecoder decoder = new HuffmanDecoder(input, output);
        decoder.decode();
    }

}
