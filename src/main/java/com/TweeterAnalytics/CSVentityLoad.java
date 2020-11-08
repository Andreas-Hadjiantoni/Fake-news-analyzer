package com.TweeterAnalytics;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
/*
* Constructor x = User.class.getConstructor(new Class[]{String[].class});
x.newInstance(new Object[]{row});
* */

public class CSVentityLoad<V extends Entity> {

    private final Constructor<? extends V> constructor;

    public CSVentityLoad( Class<? extends V> implementation ) throws NoSuchMethodException {
        this.constructor = implementation.getConstructor(new Class[]{String[].class});
    }

    public HashMap<BigInteger, V> loadData( String path ) {

        CSVParser parser = null;
        String[] row;
        ArrayList list = new ArrayList<V>();

        try {

            File csvData = new File(path);
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.RFC4180);
            parser.iterator().next();

//            csvData.length()

            for (CSVRecord record : parser) {
                row = recordToArray(record);
                try {

                list.add( constructor.newInstance(new Object[]{row}) );
                } catch ( Exception e ) {
                    System.out.println("Could not append entity in memory!");
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (parser != null) {
                try {
                    parser.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return entitiesToHashMap( list );
    }

    private String[] recordToArray(CSVRecord r) {
        String[] array = new String[r.size()];

        for (int i = 0; i < r.size(); i++) {
            array[i] = r.get(i);
        }
        return array;
    }

    private HashMap<BigInteger, V> entitiesToHashMap(ArrayList<V> vs ) {
        HashMap result = new HashMap<BigInteger, User>();

        for (V v : vs)
            result.put(v.getId(), v);

        return result;
    }

}
