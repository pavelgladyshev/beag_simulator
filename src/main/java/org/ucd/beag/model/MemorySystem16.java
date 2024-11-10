package org.ucd.beag.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class MemorySystem16 implements Memory {

    class MemoryRegion16 {
        public int start;
        public int size;
        public Memory device;

        MemoryRegion16(int start, int size, Memory device) {
            this.start = start;
            this.size = size;
            this.device = device;
        }
    }

    ArrayList<MemoryRegion16> regions = new ArrayList<MemoryRegion16>();

    public void add(int start, int size, Memory device) {
        regions.add(new MemoryRegion16(start,size,device));
        regions.sort(new Comparator<MemoryRegion16>() {
            @Override
            public int compare(MemoryRegion16 t0, MemoryRegion16 t1) {
                return t0.start < t1.start ? -1  : (t0.start == t1.start ? 0 : 1);
            }
        });
    }

    @Override
    public int read(int address) throws IOException {
        for (MemoryRegion16 r:
             regions) {
            if((address >= r.start) && (address < (r.start+r.size))) {
                return r.device.read(address - r.start);
            }
        }
        return 0xffff;
    }

    @Override
    public void write(int address, int value) {
        for (MemoryRegion16 r:
                regions) {
            if((address >= r.start) && (address < (r.start+r.size))) {
                r.device.write(address - r.start, value);
                break;
            }
        }
    }

}
