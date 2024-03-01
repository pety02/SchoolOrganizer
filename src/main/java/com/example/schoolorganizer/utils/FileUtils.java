package com.example.schoolorganizer.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

public class FileUtils {
    public static byte[] compressFile(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] temp = new byte[4 * 1024];
        int size = 0;
        while (!deflater.finished()) {
            size = deflater.deflate(temp);
            outputStream.write(temp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }

        return outputStream.toByteArray();
    }
}
