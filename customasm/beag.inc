
#bankdef program
{
    #bits 16
    #addr 0x0000
    #addr_end 0x1FFF
    #outp 0
}

#bankdef data
{
    #bits 16
    #addr 0x2000
    #addr_end 0x3FFF
    #outp 16*0x2000
}

#subruledef register
{
    r0 => 0x0
    r1 => 0x1
    r2 => 0x2
    r3 => 0x3
    r4 => 0x4
    r5 => 0x5
    r6 => 0x6
    r7 => 0x7
}

#ruledef
{
    add {r: register},{rs1: register},{rs2: register}  => 0x0 @ r @ rs1 @ rs2
}

#ruledef
{
    sub {r: register},{rs1: register},{rs2: register}  => 0x1 @ r @ rs1 @ rs2
}

#ruledef
{
    mul {r: register},{rs1: register},{rs2: register}  => 0x2 @ r @ rs1 @ rs2
}

#ruledef
{
    div {r: register},{rs1: register},{rs2: register}  => 0x3 @ r @ rs1 @ rs2
}

#ruledef
{
    rem {r: register},{rs1: register},{rs2: register}  => 0x7 @ r @ rs1 @ rs2
}

#ruledef
{
    jalr {r: register},{rs1: register},{rs2: register}  => 0x4 @ r @ rs1 @ rs2
}

#ruledef
{
    lw {r: register},({ra: register}) => 0x6 @ r @ ra @ 0x0
}

#ruledef
{
    sw {r: register},({ra: register}) => 0x5 @ 0x0 @ ra @ r
}

#ruledef
{
    lhi {r: register},{value: i8} => 0x8 @ r @ value
}

#ruledef
{
    lli {r: register},{value: i8} => 0x9 @ r @ value
}

#ruledef
{
    bnz {r: register},{addr} => 
    {
        reladdr = addr - $ 
        assert(reladdr <=  0x7f)
        assert(reladdr >= !0x7f)
        0xD @ r @ reladdr`8
    } 

}

#ruledef
{
    bz {r: register},{addr} =>
    {
        reladdr = addr - $
        assert(reladdr <=  0x7f)
        assert(reladdr >= !0x7f)
        0xE @ r @ reladdr`8
    } 
}

#ruledef
{
    bltz {r: register},{addr} => 
    {
        reladdr = addr - $ 
        assert(reladdr <=  0x7f)
        assert(reladdr >= !0x7f)
        0xF @ r @ reladdr`8
    } 
}

#ruledef
{
    li {r: register},{value: i16} => {
       hi = value[15:8]
       lo = value[7:0]
       asm {
          lli {r},{lo}
          lhi {r},{hi}
       }
    }
}

#ruledef
{
    b {addr} =>
    asm {
       bz r0,{addr}
    }
}

#ruledef
{
    nop => asm{ add r0,r0,r0 }
}

#bank program      
