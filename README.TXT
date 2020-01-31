

PROJECT TITLE: Assignment 1
VERSION or DATE: Feb 4, 2019
AUTHORS: Loc Tran
STUDENT ID: 5152677
COURSE: CSC 547
USER INSTRUCTIONS:

First, compile Queen.java by typing javac Queen.java
Second, compile queens.java by typing javac queens.java
Third, run the program by typing the following command line
	java queens N M W B tmax solution.txt method

where 	N is the number of rows. N must be integer
	M is the number of columns. M must be integer
	W is the number of white queens. W must be integer
	B is the number of black queens. B must be integer
	tmax is an upper bound on the runtime (in seconds).
	solution is the name of the output file
	method is the method you want to use. method can be hill_climbing or simulated_annealing

E.g	java queens 10 10 16 12 10 solution.txt hill_climbing
	java queens 10 12 14 15 10.2 solution.txt simulated_annealing

my annealing schedule: Tk+1 = Tk/1.2
source: https://www.phy.ornl.gov/csep/mo/node32.html


