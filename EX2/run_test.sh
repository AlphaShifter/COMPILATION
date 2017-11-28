#!/bin/bash
java -jar PARSER ./FOLDER_4_INPUT/TEST_01_Print_Primes.txt ./FOLDER_5_OUTPUT/1.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_02_Bubble_Sort.txt ./FOLDER_5_OUTPUT/2.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_03_Merge_Lists.txt ./FOLDER_5_OUTPUT/3.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_04_Matrices.txt ./FOLDER_5_OUTPUT/4.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_05_Classes.txt ./FOLDER_5_OUTPUT/5.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_06_Print_Primes_Error.txt ./FOLDER_5_OUTPUT/6.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_07_Bubble_Sort_Error.txt ./FOLDER_5_OUTPUT/7.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_08_Merge_Lists_Error.txt ./FOLDER_5_OUTPUT/8.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_09_Matrices_Error.txt ./FOLDER_5_OUTPUT/9.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_10_Classes_Error.txt ./FOLDER_5_OUTPUT/10.txt
diff ./FOLDER_5_OUTPUT/1.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_01_Print_Primes_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/2.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_02_Bubble_Sort_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/3.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_03_Merge_Lists_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/4.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_04_Matrices_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/5.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_05_Classes_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/6.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_06_Print_Primes_Error_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/7.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_07_Bubble_Sort_Error_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/8.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_08_Merge_Lists_Error_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/9.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_09_Matrices_Error_Expected_Output.txt
diff ./FOLDER_5_OUTPUT/10.txt ./FOLDER_6_EXPECTED_OUTPUT/TEST_10_Classes_Error_Expected_Output.txt
