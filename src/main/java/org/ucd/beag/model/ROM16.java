package org.ucd.beag.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

// Read Only Memory made of 16-bit words
public class ROM16 implements Memory {

    protected int []array;

    public ROM16(int size) {
        array = new int[size];
    }

    public ROM16(int size, int[] init) {
        this(size);
        initArray(init);
    }

    public ROM16(int size, File f) throws FileNotFoundException {
        this(size);
        int []init = readFile(f);
        initArray(init);
    }

    private void initArray(int []init) {
        int max = init.length < array.length ? init.length : array.length;
        if (null != init) {
            for (int i = 0; i < max; i++) {
                array[i] = init[i];
            }
        }
    }

    @Override
    public int read(int address) {
        return array[address % array.length];
    }

    @Override
    public void write(int address, int data) {
    }

    public static int[] readFile(File f) throws FileNotFoundException {
        Scanner s = new Scanner(f);
        return readData(s);
    }

    public static int[] readData(Scanner s) {
        LinkedList<Integer> l = new LinkedList<Integer>();
        String str = s.nextLine();
        if (!str.matches("v2.0 raw"))
        {
            return null;
        }
        s.useRadix(16);
        while (s.hasNextInt(16)) {
            l.add(s.nextInt() & 0xffff);
        }

        int []res = new int[l.size()];
        for (int i=0; i<res.length; i++) {
            res[i] = l.poll();
        }
        return res;
    }

}
