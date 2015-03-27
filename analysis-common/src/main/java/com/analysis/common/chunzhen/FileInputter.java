package com.analysis.common.chunzhen;

import java.io.*;

/**
 * Created by crazyy on 15/3/25.
 */
public class FileInputter implements Inputter {

    private String filepath = null;

    private FileReader fileReader = null;
    private BufferedReader bufferedReader = null;

    public FileInputter(String filepath) throws Exception {
        if(filepath == null || "".equals(filepath.trim())) {
            throw new NullPointerException("file path is null");
        }

        File file = new File(FileInputter.class.getResource("/").getPath() + filepath);

        fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);

        this.filepath = filepath;
    }

    @Override
    public String readLine() {
        String line = null;
        if(bufferedReader != null) {
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return line;
    }

    @Override
    public void close() {
        try {
            if(bufferedReader != null) bufferedReader.close();
            if(fileReader != null) fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
