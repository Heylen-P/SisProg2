package com.company;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.io.File;
import java.util.stream.StreamSupport;

public class Main {

    public class Connection {
        public int stan;
        public char letter;

        Connection(int stan, char letter){
            this.stan = stan;
            this.letter = letter;
        }

        @Override
        public String toString() {
            return "{ to "+ stan +" " +letter + " } ";
        }
    }

    Map<Integer,ArrayList<Connection>> Ways=new HashMap<>();
    ArrayList<Integer> Final= new ArrayList<>();


    public static void main(String[] args) {
        new Main().start("./automat.txt");
    }
    public void start(String filename){

        try{
            InitializeFile(filename);
        }
        catch (FileNotFoundException e) {
            System.out.println(String.format("file %s not found", filename));
        }
    }

    public int W0(int begin, String str)
    {
        int curr_stan=begin;
        char[] ch = str.toCharArray();
        for(int i=0; i<str.length(); i++)
        {boolean flag=false;
            ArrayList<Connection> tran = Ways.get(curr_stan);
            for(Connection tr:tran)
            {
                if(tr.letter==ch[i])
                {curr_stan=tr.stan;
                    flag=true;}
            }

            if (flag==false)
            {System.out.println("Can't find way from stan : "+curr_stan+" with letter "+ch[i]);
                System.exit(0);}
        }
        if(Final.contains(curr_stan))
        {
            {System.out.println("w1 can't be an empty word");
                System.exit(0);}
        }
        return curr_stan;
    }


    public String Check_word(int beg, ArrayList<Integer> fin)
    {

        int curr_stan=beg;
        for(int f: fin)
        { if(beg==f)
        {
            System.out.println("w1 can't be an empty word");
            System.exit(0);
        }

            ArrayList<Character> ch = new ArrayList<Character>();
            while(curr_stan!=f)
            {
                if(!Ways.keySet().contains(curr_stan)){
                    System.out.println("Can't find a way");
                    System.exit(1);
                }
                ArrayList<Connection> tran = Ways.get(curr_stan);

                for(int i=0; i<tran.size();i++)
                {
                    Connection tr = tran.get(i);
                    if(tr.stan==f)
                    { ch.add(tr.letter);
                        return String.valueOf(ch); }
                    else
                        continue;
                }
                int prev_stan=curr_stan;
                boolean flag = tran.size()>1 ? true: false;
                for(int i=0; i<tran.size();i++)
                {

                    Connection tr = tran.get(i);
                    if(tr.stan==f)
                    { ch.add(tr.letter);
                        return String.valueOf(ch); }
                    if(Final.contains(tr.stan) && flag==false)
                    {
                        return "automat doesn't accept word w, w1 doesn't exist";
                    }
                    if(curr_stan== tr.stan)
                    {

                        Connection tr1=tran.get(i+1);
                        curr_stan=tr1.stan;
                        ch.add(tr1.letter);
                        break;
                    }
                    curr_stan=tr.stan;
                    ch.add(tr.letter);

                    break;
                }

            }
        }

        return "ol";
    }

    public void InitializeFile(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));

        int alphabet = sc.nextInt();
        int m = sc.nextInt();

        while (m-- > 0) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            char edge = sc.next().charAt(0);
            Connection transition1 = new Connection(to, edge);
            if (Ways.containsKey(from)) {
                ArrayList<Connection> arr = Ways.get(from);
                arr.add(transition1);

                Ways.put(from, arr);
            } else {
                ArrayList<Connection> arr = new ArrayList<>();
                arr.add(transition1);

                Ways.put(from, arr);
            }
        }

        for (Integer i : Ways.keySet()) {
            ArrayList<Connection> arr = Ways.get(i);
            //System.out.println(i);
           // System.out.println(arr);
        }
        int start_stan = sc.nextInt();

        String lin = sc.nextLine();
        String line = sc.nextLine();


        String[] line1 = line.split(" ");
        for (String l : line1) {
            Final.add(Integer.parseInt(l));
        }

        String w0 = sc.nextLine();
        System.out.println("w0="+w0);
        if (w0.isEmpty()) {
            System.out.println("w0 can't be empty");
        } else {
            if (w0.toUpperCase().chars().distinct().count() > alphabet) {
                System.out.println("more symbols then alphabet size-w1");
                System.exit(0);
            }

            try {
                int beg = W0(start_stan, w0);
                System.out.println(Check_word(beg, Final));
            } catch (java.util.NoSuchElementException e) {

            }
        }
    }
}