package com.acceldata.service;

import com.acceldata.dto.KeyValue;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;

@Service
public class FileService {

    private static final String FILE_PATH = "data_store.txt";

    public void addEntry(KeyValue keyValue) {
        try(FileWriter fw = new FileWriter(FILE_PATH, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                ) {
            String entry = keyValue.getKey() + (keyValue.getValue() == null ? "" : " " + keyValue.getValue());
            out.println(entry);
            out.flush();
        } catch (IOException ioException) {
            throw new RuntimeException("This shouldn't happen!", ioException);
        }
    }

    public void reloadCache(HashMap<String, String> map) {
        try(FileReader fr = new FileReader(FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
        ) {
            String st;
            while ((st = br.readLine()) != null) {
                String[] lineArr = st.split(" ");
                if(lineArr.length == 2) {
                    map.put(lineArr[0], lineArr[1]);
                } else if(lineArr.length == 1) {
                    map.remove(lineArr[0]);
                }
            }
        } catch (IOException ioException) {
            throw new RuntimeException("This shouldn't happen!", ioException);
        }
    }
}
