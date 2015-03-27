package com.analysis.common.chunzhen;

import java.io.*;

/**
 * Created by crazyy on 15/3/25.
 */
public class FileOutputter implements Outputter {

    private String filepath = null;

    private FileWriter fileWriter = null;
    private BufferedWriter bufferedWriter = null;

    public FileOutputter(String filepath) throws Exception {

        if(filepath == null || "".equals(filepath.trim())) {
            throw new NullPointerException("file path is null");
        }

        File file = new File(FileInputter.class.getResource("/").getPath() + filepath);

        fileWriter = new FileWriter(file);
        bufferedWriter = new BufferedWriter(fileWriter);

        this.filepath = filepath;

    }

    @Override
    public void write(String line) {
        if(bufferedWriter != null) {
            try {
                bufferedWriter.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        try {
            if(bufferedWriter != null) bufferedWriter.close();
            if(fileWriter != null) fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
