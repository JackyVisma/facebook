package com.mkyong.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProvaCSVutils {
	
	private FileWriter file;
	
	ProvaCSVutils(String fileName) throws IOException{

        this.file = new FileWriter(fileName);
	}

    public void writeCsv(List<String> row) throws IOException{
        CSVUtils.writeLine(this.file, row);

    }
    
    public void close() throws IOException{
    	this.file.flush();
    	this.file.close();
    }

}