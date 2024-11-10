#include "beag.inc"

; read binary number from keyboard, print it in decimal

start:
    li r1,0x4000  ; display address => r1
    li r2,0x4001  ; keyboard ready flag address => r2
    li r3,0x4002  ; keyboard keycode address => r3
    lli r7,0      ; r7 = 0 - will hold the value of the biary number

read:
    lw r4,(r2)    ; read keyboard ready flag value
    bz r4,read    ; if 0, keep looping
    lw r4,(r3)    ; otherwise, read ASCII code of the typed character

    ; check if the user typed '\n'

    lli r5,0xA    ; r5 = ASCII code of '\n'
    sub r6,r4,r5  ; r6 = r4-r5 = typed ASCII code - ASCII code of '\n'
    bz r6,print    ; if r6==0, it means r4==r5, the user typed '\n', we stop  

    ; otherwise check that the typed key code is between '0' and '1' inclusively

    lli r5,0x30    ; r5 = ASCII code of '0'
    sub r6,r4,r5  ; r6 = r4 - r5 = typed ASCII code - ASCII code of '0'
    bltz r6,read  ; if r6 < 0, it means r4 < r5 (typed code <'0'), keep looping 

    lli r5,0x31    ; r5 = ASCII code of '1'
    sub r5,r5,r4  ; r5 = r5-r4 = ASCII code of '1' - typed ASCII code
    bltz r5,read  ; if r5 < 0, it means r5 < r4 (typed code >'1'), keep looping

    ; recall that r6 = typed ASCII code - '0' 
    ; r6 == 0 if the user typed '0'
    ; r6 == 1 if the user typed '1'!
    ; r6 contains the value of the typed digit.
    ; let's add it to the value of the number n being accumulated in r7

    lli r4,2      ; r4 = 2
    mul r7,r7,r4  ; r7 = n*2
    add r7,r7,r6  ; r7 = n*2+r6

    b read        ; keep reading

print:
    lli r4,10     ; r4 = 10 (radix of the output number)
    lli r5,0x30   ; r5 = ASCII code of '0' 
    li r3,1       ; r3 = 1 (for address increment)
    li r2,string  ; r2 = address of character array string[]
    sw r0,(r2)    ; string[0] = '\0'
    add r2,r2,r3  ; r2=r2+1

div_loop:
    rem r6,r7,r4  ; r6 = n % 10 = the least significant decimal digit of n
    add r6,r6,r5  ; r6 = r6 + code of '0' = ASCII code of that digit
    sw r6,(r2)    ; write it into string[] at position r2
    add r2,r2,r3  ; r2=r2+1
    div r7,r7,r4  ; r7 = n / 10
    bnz r7,div_loop  ; keep loping while r7 != 0

    ; now print contents of string[] in reverse order

print_loop:
    sub r2,r2,r3  ; r2=r2-1 (move back one step)
    lw r6,(r2)    ; read next digit to print
    bz r6,finish  ; r6 = '\0' indicates end of string
    sw r6,(r1)    ; write digit to display
    b print_loop

finish:
    b finish

    #bank data
string:
    #res 7

