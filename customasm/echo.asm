#include "beag.inc"

li r1,0x4000
li r2,0x4001
li r3,0x4002

loop:
  lw r4,(r2)
  bz r4,loop
  
  lw r4,(r3)
  sw r4,(r1)
  b loop
