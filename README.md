# Chip-8-Interpreter
"CHIP-8 is a programming language and virtual machine (VM) that was created in the 1970s. 
It was initially designed for the COSMAC VIP and Telmac 1800 8-bit microcomputers to make game development easier. 
The CHIP-8 VM was not tied to any specific hardware architecture, allowing it to be implemented on various platforms.
I initially faced uncertainty regarding the entry point for delving into game emulation."

# Goal
My goal was to create a Nintendo DS emulator due to nostalgia but after advice from others, 
I came to the conclusion to choose the CHIP-8. The CHIP-8 helped enhance my comprehension of fundamental concepts such as bit manipulation, 
register operations, CPU functionality, and graphics rendering. This project provided me with a valuable learning experience, 
significantly contributing to my understanding of these essential aspects. I found the process enjoyable and enriching, 
and recommend it to others seeking a starting point in the realm of game emulation and broader game device development.

# Challenegs
My biggest issue when developing the Chip 8 was how to implement the key input listeners. The keyboard input responds but the input has a lag or doesn't register at all sometimes.
I believe that using an external library such as libGDX would have been a better initial choice due to it having a faster response rate to keyboard input. Another issue I had was dealing
with collision detection and solving it using a 1-dimensional array to represent the entire screen. So far no issues have arisen since implementing a 2d array and help from others in the EmuDev discord.
The last challenge I had was an issue dealing with registers, I used test roms to help detect which registers were executing the opcode or flags wrong(a reference to test rom below). As I looked at different
sources for help the problem was that there were different ways to execute different opcodes depending on what games you wanted to run. I ended up following most of Tobiasvl references. 

# References
+ https://tobiasvl.github.io/blog/write-a-chip-8-emulator
+ https://github.com/mattmikolay/chip-8/wiki/CHIP%E2%80%908-Instruction-Set
+ https://github.com/corax89/chip8-test-rom
+ http://devernay.free.fr/hacks/chip8/C8TECH10.HTM

# Run project
https://www.jetbrains.com/guide/java/tips/clone-project-from-github/#:~:text=Clone%20a%20project%20from%20the%20IntelliJ%20IDEA%20welcome%20screen&text=Click%20the%20Get%20from%20VCS,into%20an%20IntelliJ%20IDEA%20project.
+ In folder called "Main" the game file is loaded into a method called loadGame() inside the constructor. To test different games go to file "rom" and copy any name into the method mentioned earlier. EX. loadGame("rom/pong2.c8") --> loadGame("rom/tetris.c8");


# Pictures
![Screenshot (19)](https://github.com/Hi-Baillie/Chip-8/assets/97775116/981ebe47-9f8d-4e01-bb22-682c98d819fd)
![Screenshot (25)](https://github.com/Hi-Baillie/Chip-8/assets/97775116/2a067321-9d0a-4047-b987-6cea2cc5db52)
![Screenshot (21)](https://github.com/Hi-Baillie/Chip-8/assets/97775116/06c0a96d-b8f5-4c58-a6fd-e68e21c74f2b)

