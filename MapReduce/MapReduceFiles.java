import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;

public class MapReduceFiles {

  public static void main(String[] args) {

    if (args.length < 3) {
      System.err.println("usage: java MapReduceFiles file1.txt file2.txt file3.txt");

    }
    final int mapsize = Integer.parseInt(args[3]);
    final int reducesize = Integer.parseInt(args[4]);

    Map<String, ArrayList> input = new HashMap<String, ArrayList>();
    try {

      input.put(args[0], readFile(args[0]));
      input.put(args[1], readFile(args[1]));
      input.put(args[2], readFile(args[2]));
    }
    catch (IOException ex)
    {
        System.err.println("Error reading files...\n" + ex.getMessage());
        ex.printStackTrace();
        System.exit(0);
    }

    // APPROACH #1: Brute force



    // APPROACH #2: MapReduce



    // APPROACH #3: Distributed MapReduce
    {
      final Map<String, Map<String, Integer>> output = new HashMap<String, Map<String, Integer>>();

      // MAP:
      final long mapTime = System.currentTimeMillis();
      final List<MappedItem> mappedItems = new LinkedList<MappedItem>();

      final MapCallback<String, MappedItem> mapCallback = new MapCallback<String, MappedItem>() {
        @Override
        public synchronized void mapDone(String file, List<MappedItem> results) {
          mappedItems.addAll(results);
        }
      };

      List<Thread> mapCluster = new ArrayList<Thread>(input.size());

      Iterator<Map.Entry<String, ArrayList>> inputIter = input.entrySet().iterator();
      while(inputIter.hasNext()) {
        Map.Entry<String, ArrayList> entry = inputIter.next();
        final String file = entry.getKey();
        final ArrayList contents = entry.getValue();
        StringBuilder fileContents = new StringBuilder();
        ArrayList<StringBuilder> textgroups = new ArrayList<StringBuilder>();
        int t = contents.size();

        int mod = t % mapsize; 
        int loops = t/mapsize;
        loops = loops+1;

        //System.err.println(contents.size());


        for(int i = 0;i<loops;i++){ // loops to split the text file into the specified sized blocks
          int start = i*mapsize;
          if(i == loops-1){
             fileContents = new StringBuilder((int) mod); // create new stringbuilder of set size
          }
          else{
             fileContents = new StringBuilder((int) mapsize);
          }

          for(int j = start;j<start+mapsize;j++){ //loop through set amount of lines
              if(j < contents.size()){
                 fileContents.append(contents.get(j)); //build strings
              }
          }

          textgroups.add(fileContents);  //add split string to array

        }
        Thread myThreads[] = new Thread[loops]; //new array for threads
        for(int g = 0; g < textgroups.size(); g++){


           String text = textgroups.get(g).toString();


           myThreads[g] = new Thread(new Runnable() {
            @Override
            public void run() {
              map(file, text, mapCallback);
             }
           });
           mapCluster.add(myThreads[g]);
           myThreads[g].start();
        }
      }



      // wait for mapping phase to be over:
      for(Thread t : mapCluster) {
        try {
          t.join();
        } catch(InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      final long endmapTime = System.currentTimeMillis();


      // GROUP:

      final long groupTime = System.currentTimeMillis();
      Map<String, List<String>> groupedItems = new HashMap<String, List<String>>();

      Iterator<MappedItem> mappedIter = mappedItems.iterator();
      while(mappedIter.hasNext()) {
        MappedItem item = mappedIter.next();
        String word = item.getWord();
        String file = item.getFile();
        List<String> list = groupedItems.get(word);
        if (list == null) {
          list = new LinkedList<String>();
          groupedItems.put(word, list);
        }
        list.add(file);
      }
      final long endgroupTime = System.currentTimeMillis();

      // REDUCE:

       final long reduceTime = System.currentTimeMillis();
      final ReduceCallback<String, String, Integer> reduceCallback = new ReduceCallback<String, String, Integer>() {
        @Override
        public synchronized void reduceDone(String k, Map<String, Integer> v) {
          output.put(k, v);
        }
      };

      List<Thread> reduceCluster = new ArrayList<Thread>(groupedItems.size());

      Iterator<Map.Entry<String, List<String>>> groupedIter = groupedItems.entrySet().iterator();


      ArrayList<String> keys = new ArrayList<String>();
      ArrayList<List> arrayoflists = new ArrayList<List>();
      while(groupedIter.hasNext()) {
        Map.Entry<String, List<String>> entry = groupedIter.next();
        final String word = entry.getKey();
        keys.add(word);
        final List<String> list = entry.getValue();
        arrayoflists.add(list);



      }

      int t2 = keys.size();
      int modsize = t2 % reducesize;
      int loopsize = t2/reducesize;
      loopsize = loopsize+1;

      for (int i = 0;i<loopsize;i++){
        int start2 = reducesize * i;

        Thread t = new Thread(new Runnable() {
          @Override
          public void run() {
            for(int j = start2; j<start2+reducesize; j++){
              if(j<t2){
                reduce(keys.get(j), arrayoflists.get(j), reduceCallback); // for each thread reduce numer of words contained in arraylist
              }
            }
          }
        });
        reduceCluster.add(t);
        t.start();


      }



      // wait for reducing phase to be over:
      for(Thread t : reduceCluster) {
        try {
          t.join();
        } catch(InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      final long endreduceTime = System.currentTimeMillis();

      System.out.println(output); //output recorded times
      System.out.println("Total map time: " + (endmapTime - mapTime));
      System.out.println("Total group time: " + (endgroupTime - groupTime ));
      System.out.println("Total reduce time: " + (endreduceTime-reduceTime));

    }
  }
  /*---------------------end of main------------------------*/

  public static void map(String file, String contents, List<MappedItem> mappedItems) {
    String[] words = contents.trim().split("\\s+");
    for(String word: words) {
      mappedItems.add(new MappedItem(word, file));
    }
  }

  public static void reduce(String word, List<String> list, Map<String, Map<String, Integer>> output) {
    Map<String, Integer> reducedList = new HashMap<String, Integer>();
    for(String file: list) {
      Integer occurrences = reducedList.get(file);
      if (occurrences == null) {
        reducedList.put(file, 1);
      } else {
        reducedList.put(file, occurrences.intValue() + 1);
      }
    }
    output.put(word, reducedList);
  }

  public static interface MapCallback<E, V> {

    public void mapDone(E key, List<V> values);
  }

  public static void map(String file, String contents, MapCallback<String, MappedItem> callback) {
    String[] words = contents.trim().split("\\s+");
    List<MappedItem> results = new ArrayList<MappedItem>(words.length);
    for(String word: words) {

        for (int i = 0;i<4;i++){
          word  = clean(word);
        }



      results.add(new MappedItem(word, file));
    }
    callback.mapDone(file, results);
  }

  public static interface ReduceCallback<E, K, V> {

    public void reduceDone(E e, Map<K,V> results);
  }


  public static String clean(String word){ //method to check that input are proper words
    if (word.length() > 1){
      char end = word.charAt(word.length() - 1);
      char start = word.charAt(0);
      Boolean e = Character.isLetter(end);
      Boolean s = Character.isLetter(start);

      if (e == false){ // if it not a letter drop
        word = word.substring(0, word.length() - 1);
      }
      if(s == false){
        word = word.substring(1, word.length());
      }
    }
    else if(word.length() == 1){
      char st = word.charAt(0);
      Boolean check = Character.isLetter(st);
      if(check == false){
        word = "";
      }
    }


   return word;
  }

  public static void reduce(String word, List<String> list, ReduceCallback<String, String, Integer> callback) {

    Map<String, Integer> reducedList = new HashMap<String, Integer>();
    for(String file: list) {
      Integer occurrences = reducedList.get(file);
      if (occurrences == null) {
        reducedList.put(file, 1);
      } else {
        reducedList.put(file, occurrences.intValue() + 1);
      }
    }
    callback.reduceDone(word, reducedList);
  }

  private static class MappedItem {

    private final String word;
    private final String file;

    public MappedItem(String word, String file) {
      this.word = word;
      this.file = file;
    }

    public String getWord() {
      return word;
    }

    public String getFile() {
      return file;
    }

    @Override
    public String toString() {
      return "[\"" + word + "\",\"" + file + "\"]";
    }
  }

  private static ArrayList readFile(String pathname) throws IOException {
    File file = new File(pathname);
    ArrayList<String> content = new ArrayList<String>();
    StringBuilder fileContents = new StringBuilder((int) file.length());
    Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
    String lineSeparator = System.getProperty("line.separator");

    try {
      if (scanner.hasNextLine()) {
        content.add(scanner.nextLine()); // add lines from text file to arraylist
      }
      while (scanner.hasNextLine()) {
        content.add(lineSeparator + scanner.nextLine());
      }
      return content;
   } finally {
      scanner.close();
   }
  }
}
