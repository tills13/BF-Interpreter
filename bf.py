import fileinput
import sys
import optparse
import re

index = 0
cells = [0]
def main():
	p = optparse.OptionParser()
	p.add_option("--file", dest="f", default = None)
	#p.add_option("--verbose", dest="verbose", default = False)
	options, arguments = p.parse_args()

	mfile = open(options.f, 'r')
	program = ''.join(mfile.readlines())
	#verbose = options.verbose
	parse(program, False)

def parse(minput, verbose):
	global index
	global cells
	pc = 0

	while(pc < len(minput)):
		if verbose:
			print (minput[:pc] + "*" + minput[pc] + "*" + minput[pc + 1:])
			for i in range(len(cells)):
				if (i == index): print("[%d]" % cells[i]),
				else: print(" %d " % cells[i]),
			print

		char = minput[pc]
		if (char == '>'):
			if (len(cells) <= (index + 1)):
				cells.append(0)
			index += 1
		elif (char == '<'): index -= 1
		elif (char == '.'): print(chr(cells[index])),
		elif (char == ','): cells[index] = ord(sys.stdin.read(1))
		elif (char == '['):
			pc += 1
			lcount = 1
			lend = pc
			while True:
				#print lcount, minput[lend]
				if minput[lend] == ']': lcount -= 1
				elif minput[lend] == '[': lcount += 1
				lend += 1
				if (lcount == 0): break

			if (cells[index] != 0): parse(minput[pc:lend - 1], verbose)
			else: pc = lend + 1
			pc -= 2
		elif (char == '-'): cells[index] -= 1
		elif (char == '+'): cells[index] += 1
		pc += 1

main()