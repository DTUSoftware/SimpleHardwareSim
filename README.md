# SimpleHardwareSim

02332 Compiler Construction - Mandatory Assignment: A Simple Hardware Simulator

## How to use
To use the compiler, firstly navigate to the directory of the 
project with a terminal that supports make.

On Windows it is recommended to use the cygwin terminal, 
(remember to install make with the cygwin install). On Linux 
whatever terminal should be fine.

To make the abstract syntax tree white the command
```
make tree
```
which will make the abstract syntax tree, so it can be inspected.

To run the test "03-trafiklys", type the command
```
make
```
since it's the default, test to make, else it can be run by typing
```
make test
```
or 
```
make 03
```
To run the other tests "01-hello-world" or "02-trafiklys-minimal"
either type 
```
make 01
```
or 
```
make 02
```