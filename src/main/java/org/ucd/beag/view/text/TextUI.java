package org.ucd.beag.view.text;

import org.ucd.beag.model.CPU;
import org.ucd.beag.model.InstructionRegister;
import org.ucd.beag.model.Memory;
import org.ucd.beag.view.Command;
import org.ucd.beag.view.UI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TextUI implements UI {

    boolean isRunning = false;

    InputStream inputStream;
    Scanner in;
    PrintStream out;

    public TextUI(InputStream in, OutputStream out) {
        this.inputStream = in;
        this.in = new Scanner(inputStream);
        this.out = new PrintStream(out);
    }

    public void displayWelcome() {
        out.println("Welcome to Riomhaire Beag simulator!");
        out.println("Enter h for help.");
        out.println();
    }

    public void displayState(CPU cpu) throws IOException {
        out.format("\nCPU State:\n");
        out.format("r0=%04x  r1=%04x  r2=%04x  r3=%04x r4=%04x  r5=%04x  r6=%04x  r7=%04x  pc=%04x\n\n",
                cpu.getR(0),cpu.getR(1),cpu.getR(2),cpu.getR(3),cpu.getR(4),cpu.getR(5),
                cpu.getR(6),cpu.getR(7),cpu.getPc());
        disassemble(cpu.getProgMem(),cpu.getPc(),4);
    }

    private void disassemble(Memory progMem, int start, int size) throws IOException {
        out.println("     Addr  Instr  Mnemonic");
        out.println("     ---------------------");
        for (int i=0; i<size; i++) {
            if (i==0) {
                out.print("pc-> ");
            } else {
                out.print("     ");
            }
            int instr = progMem.read(start+i);
            out.format("%04x: %04x   %s\n",start+i,instr,toAssemblyMnemonic(instr));
        }
    }

    private String toAssemblyMnemonic(int instr) {
        InstructionRegister ir = new InstructionRegister();
        ir.set(instr);
        return switch (ir.opCode()) {
            case CPU.Instruction.ADD -> String.format("add r%d,r%d,r%d", ir.rd(),ir.rs1(),ir.rs2());
            case CPU.Instruction.SUB -> String.format("sub r%d,r%d,r%d", ir.rd(),ir.rs1(),ir.rs2());
            case CPU.Instruction.MUL -> String.format("mul r%d,r%d,r%d", ir.rd(),ir.rs1(),ir.rs2());
            case CPU.Instruction.DIV -> String.format("div r%d,r%d,r%d", ir.rd(),ir.rs1(),ir.rs2());
            case CPU.Instruction.JALR -> String.format("jalr r%d,r%d,r%d", ir.rd(),ir.rs1(),ir.rs2());
            case CPU.Instruction.REM -> String.format("rem r%d,r%d,r%d", ir.rd(),ir.rs1(),ir.rs2());
            case CPU.Instruction.SW -> String.format("sw r%d,(r%d)", ir.rs2(),ir.rs1());
            case CPU.Instruction.LW -> String.format("lw r%d,(r%d)", ir.rd(),ir.rs1());
            case CPU.Instruction.LLI -> String.format("lli r%d,%d", ir.rd(),ir.imm8());
            case CPU.Instruction.LHI -> String.format("lhi r%d,%d", ir.rd(),ir.imm8() & 0xFF);
            case CPU.Instruction.BNZ -> String.format("bnz r%d,%d", ir.rd(),ir.imm8());
            case CPU.Instruction.BZ -> String.format("bz r%d,%d", ir.rd(),ir.imm8());
            case CPU.Instruction.BLTZ -> String.format("bltz r%d,%d", ir.rd(),ir.imm8());
            default -> "-NONE-";
        };
    }


    public void displayMemory(Memory mem, int start, int size) throws IOException {
        out.println("Addr    0    1    2    3    4    5    6    7");
        out.println("---------------------------------------------");
        for (int i = start; i < start+size; i++) {
            if ((i % 8) == 0) {
                out.format("%04x  ",i);
            }
            out.format("%04x ",mem.read(i));
            if ((i % 8) == 7) {
                out.println();
            }
        }
        out.println();
    }

    @Override
    public void displayHelp() {
        out.println();
        out.println("Simulator commands:");
        out.println("  s             perform single instruction and stop");
        out.println("  c             run non-stop until operators types something");
        out.println("  x addr size   print hex dump of data memory");
        out.println("  h             print this message");
        out.println("  q             quit");
    }

    public Command getCommand() throws IOException {

        if(!isRunning) {
            out.print("> ");
            String s = in.next();
            if (s.matches("s")) {
                return new Command(Command.STEP,0,0);
            } else if (s.matches("h")) {
                return new Command(Command.HELP,0,0);
            } else if (s.matches("x")) {
                return new Command(Command.DISPLAY_MEM,in.nextInt(16),in.nextInt(16));
            } else if (s.matches("c")) {
                out.println("Continuing. Enter anything to stop...");
                isRunning = true;
                return new Command(Command.CONTINUE,0,0);
            } else if (s.matches("q")) {
                return new Command(Command.QUIT,0,0);
            } else return new Command(Command.NONE,0,0);
        } else {
                if (inputStream.available()>0) {
                    in.next();
                    isRunning = false;
                    return new Command(Command.STOP,0,0);
                } else {
                    return new Command(Command.NONE,0,0);
                }
        }
    }
}
