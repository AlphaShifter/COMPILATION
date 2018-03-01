import sys
import os
from subprocess import Popen, PIPE
import random
import re

COMPILER = "COMPILER"



input_folder = "FOLDER_C1_CUSTOM_INPUT"
expected_output = "FOLDER_C4_CUSTOM_EXPECTED"



inter_folder = "FOLDER_C2_INTERMIDATE_FILES___" #Where to put all the generated files
mips_folder = inter_folder+ "/FOLDER_C2_CUSTOM_MIPS" #MIPS files
output_folder = inter_folder + "/FOLDER_C3_CUSTOM_OUTPUT"#Outut Files
SPIM_INTRO = "SPIM_INTRO.txt"

suffix = ""

w_header_subfolder = "w_header"
w_h_prefix = "w_h_"

test_result_file = "tester_result.txt"


RED = 31
GREEN = 32
YELLOW = 33
BLUE = 34
def printc(text, color, bg = 17):
	print "\033[48;5;%sm\033[1m\033[%sm%s\033[0m" % (bg, color, text)


def main(argv):
	# if(len(argv) <= 1 or (argv[1] != "--do-not-compile" and argv[1] != "-x")):
	# 	print "Compiling the compiler..."
	# 	os.system("make > /dev/null")
	# os.system("clear")

	# print "Clearing folders.."
	# os.system("rm -r %s"% inter_folder)
	# os.system("rm -r %s/%s" % (inter_folder, w_header_subfolder))
	# os.system("mkdir %s"% inter_folder)
	# os.system("mkdir %s"% output_folder)
	# os.system("mkdir %s"% mips_folder)

	# print "Generates and runs MIPS files for the inputs (This might take a few minutes...)"
	direc = os.listdir(input_folder)
	# for file in direc:
	# 	path = os.path.join(input_folder, file)
	# 	if os.path.isdir(path):
	# 		continue
	# 	# print file
	# 	input_file = "%s/%s" % (input_folder, file)
	# 	mips_file = "%s/%s" % (mips_folder, file)
	# 	output_file = "%s/%s" % (output_folder, file)
	# 	print file
	# 	os.system("java -jar %s %s %s > /dev/null 2>&1" % (COMPILER, input_file, mips_file))
	# 	os.system("spim -f %s > %s " % (mips_file, output_file))

	# os.system("clear")

	failtures = 0
	total = 0
	notfound = 0
	f = open(test_result_file, 'w+')


	print "Making expected outputs...."
	#gluing header to expected outputs
	os.system("mkdir %s/%s" %(inter_folder, w_header_subfolder))
	exp_direc = os.listdir(expected_output)
	for exp_file in exp_direc:
		file_w_header = "%s/%s%s" % (w_header_subfolder, w_h_prefix, exp_file)
		os.system("cat %s %s/%s > %s/%s" % (SPIM_INTRO, expected_output, exp_file, inter_folder, file_w_header))

	os.system("clear")

	f.write("The following inputs returned the wrong output:\n")
	for file in direc:
		file_w_header = "%s/%s%s" % (w_header_subfolder, w_h_prefix, file)
		process = Popen(['diff', "%s/%s" % (output_folder, file), "%s/%s%s.txt" % (inter_folder, file_w_header[:-4],suffix)], stdout=PIPE, stderr=PIPE)
		stdout, stderr = process.communicate()
		print stderr
		if stdout == "" and stderr == "":
			total += 1
		elif stdout != "":
			print "%s\t--\tWRONG!" % (file)
			print stdout
			total += 1
			failtures += 1
			f.write(file + "\n")
		else:
			print "\n%s%s.txt -- file not found" % (file_w_header[:-4], suffix)
			notfound += 1

	

	col = RED

	if(failtures < total/10):
		col = YELLOW


	if(failtures == 0):
		col = GREEN

	print "\n\n"
	printc("%s out of %s passed the test. " % (total - failtures, total), col)
	printc("%s have no expected output to compare to" % notfound, col)

	print "\n\n"

	f.write("\n\n\n")
	printc("Tests that failed completely (MIPS program crashed):", BLUE, bg=0)
	#print "---x---x---x---x---x---x---x---x---x---x--\n"
	f.write("Tests that failed completely (MIPS program crashed):\n")
	f.close()
	os.system("""grep -rnw %s/ -e "Exception" """ % output_folder)
	os.system("""grep -rnw %s/ -e "symbols are undefined" """ % output_folder)
	os.system("""grep -rnw %s/ -e "symbols are undefined" >> %s """ % (output_folder, test_result_file))
	print "\n\nResult was written to file: %s" % test_result_file

main(sys.argv)