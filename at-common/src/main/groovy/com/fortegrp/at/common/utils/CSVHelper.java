package com.fortegrp.at.common.utils;

import  org.apache.commons.csv.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by odiachuk on 10/28/16.
 */
public class CSVHelper {

    public static final String SRC_TEST_RESOURCES = "src/test/resources/";
    public static final char DELIMITER = ',';

    String FILE_NAME = "";

    List<CSVRecord> allRecords = null;

    CSVHelper (String fileName){
        this.FILE_NAME = SRC_TEST_RESOURCES + fileName;
    }

    public int searchValueInFirstColumn(String value){
        return searchValueInColumn(value, 0);
    }

    public String searchKeyValue(String value){
        return getValueFromCell(searchValueInColumn(value, 0),1);
    }

    public int searchValueInColumn(String value, int column){
        if (allRecords == null){
            setAllRecords();
        }

        for(int i = 0; i < allRecords.size(); i++){
            if (allRecords.get(i).get(column).equals(value))
                return i;
        }

        return -1;
    }

    public String getValueFromCell(int row, int column){
        String result = null;

        if (allRecords == null) {
            setAllRecords();
        }

        result = allRecords.get(row).get(column);

        return result;
    }

    private void setAllRecords() {
        FileReader sheetReader = null;
        CSVParser parser = null;
        try {
//            File suiteFile = ((File) FileUtils.listFiles(new File(SRC_TEST_RESOURCES),
//                    new RegexFileFilter(SUITE_NAME), DirectoryFileFilter.DIRECTORY).toArray()[0]).getAbsoluteFile();
            sheetReader = new FileReader(FILE_NAME);
            parser = new CSVParser(sheetReader,
                    CSVFormat.DEFAULT.withDelimiter(DELIMITER).withIgnoreEmptyLines());
            allRecords = parser.getRecords();
        } catch (Exception e) {
            System.out.println(e.toString());
            // TODO
        } finally {
            try {
                parser.close();
                sheetReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
