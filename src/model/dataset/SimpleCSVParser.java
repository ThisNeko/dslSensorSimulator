package model.dataset;

import com.opencsv.CSVReader;
import java.io.*;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.ParseException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;




public class SimpleCSVParser {
private String name;
private String in;
private String out;


 public  SimpleCSVParser(String name,String in, String out){
       this.name=name;
       this.in = in;
       this.out =out;

 }



 public  void run() throws IOException, InterruptedException {

  Map<Integer, String> sensorvalue = new HashMap<>();

  String csvFile = this.in;
  PrintWriter pw = new PrintWriter(new File(out));
  CSVReader reader = null;
  try {
   reader = new CSVReader(new FileReader(csvFile));
   String[] line;


   while ((line = reader.readNext()) != null) {
    if(this.name.equals(line[1])){
     int foo = Integer.parseInt(line[0]);
             sensorvalue.put(foo,line[2]);
    }

   }
  } catch (IOException e) {
   e.printStackTrace();
  }



  StringBuilder sb = new StringBuilder();

  Set<Entry<Integer, String>> setHm = sensorvalue.entrySet();
  Iterator<Entry<Integer, String>> it = setHm.iterator();

  while(it.hasNext()){
   Entry<Integer, String> e = it.next();
   //System.out.println(e.getKey() + " : " + e.getValue());
   sb.append(this.name+" , ");
   sb.append(e.getKey()+" , ");
   sb.append(e.getValue());
   sb.append('\n');
  }
  pw.write(sb.toString());
  pw.close();

 }

}