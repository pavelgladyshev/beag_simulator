package org.ucd.beag.model;

import java.io.IOException;

import static org.ucd.beag.model.CPU.Instruction.*;

public class CPU {

    public Memory getProgMem() {
        return  progMem;
    }

    public class Instruction {
        public final static int ADD = 0x0;
        public final static int SUB = 0x1;
        public final static int MUL = 0x2;
        public final static int DIV = 0x3;
        public final static int JALR = 0x4;
        public final static int SW = 0x5;
        public final static int LW = 0x6;
        public final static int REM = 0x7;
        public final static int LHI = 0x8;
        public final static int LLI = 0x9;
        public final static int NOP1 = 0xA;
        public final static int NOP2 = 0xB;
        public final static int NOP3 = 0xC;
        public final static int BNZ = 0xD;
        public final static int BZ = 0xE;
        public final static int BLTZ = 0xF;
    };
    Registers r = new Registers();
    ProgramCounter pc = new ProgramCounter();
    InstructionRegister ir = new InstructionRegister();
    Memory progMem;
    Memory dataMem;

    public CPU(int[] initRegs, int initPC, Memory program, Memory data) {
        r = new Registers(initRegs);
        pc = new ProgramCounter(initPC);
        progMem = program;
        dataMem = data;
    }

    public void step() throws IOException {
        // Fetch next instruction from program memory
        ir.set(progMem.read(pc.get()));
        // Execute instruction
        switch (ir.opCode()) {
            case ADD:
                r.set(ir.rd(),ALU.add16(r.get(ir.rs1()),r.get(ir.rs2())));
                pc.add1();
                break;
            case SUB:
                r.set(ir.rd(),ALU.sub16(r.get(ir.rs1()),r.get(ir.rs2())));
                pc.add1();
                break;
            case MUL:
                r.set(ir.rd(),ALU.mul16(r.get(ir.rs1()),r.get(ir.rs2())));
                pc.add1();
                break;
            case DIV:
                r.set(ir.rd(),ALU.div16(r.get(ir.rs1()),r.get(ir.rs2())));
                pc.add1();
                break;
            case JALR:
                r.set(ir.rd(),pc.get());
                pc.set(ALU.add16(r.get(ir.rs1()),r.get(ir.rs2())));
                break;
            case SW:
                dataMem.write(r.get(ir.rs1()),r.get(ir.rs2()));
                pc.add1();
                break;
            case LW:
                r.set(ir.rd(), dataMem.read(r.get(ir.rs1())));
                pc.add1();
                break;
            case REM:
                r.set(ir.rd(),ALU.rem16(r.get(ir.rs1()),r.get(ir.rs2())));
                pc.add1();
                break;
            case LHI:
                r.set(ir.rd(),(r.get(ir.rd()) & 0xFF) | ((ir.imm8() & 0xFF)<<8));
                pc.add1();
                break;
            case LLI:
                r.set(ir.rd(),ir.imm8());
                pc.add1();
                break;
            case BNZ:
                if (ALU.int16se(r.get(ir.rd())) != 0) {
                    pc.set(ALU.add16(pc.get(),ir.imm8()));
                } else {
                    pc.add1();
                }
                break;
            case BZ:
                if (ALU.int16se(r.get(ir.rd())) == 0) {
                    pc.set(ALU.add16(pc.get(),ir.imm8()));
                } else {
                    pc.add1();
                }
                break;
            case BLTZ:
                if (ALU.int16se(r.get(ir.rd())) < 0) {
                    pc.set(ALU.add16(pc.get(),ir.imm8()));
                } else {
                    pc.add1();
                }
                break;
        }
    }

    public int getR(int n) { return r.get(n); };
    public int getPc() { return pc.get(); };

}
