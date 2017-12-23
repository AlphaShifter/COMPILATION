# # !/bin/bash
# make
# for planet in $(ls FOLDER_4_INPUT) 
# do
#   INPUT="FOLDER_4_INPUT/"$planet
#   OUTPUT="FOLDER_5_OUTPUT/"$(basename $INPUT .txt)"_Expected_Output.txt" 
#   # echo $OUTPUT
#   java -jar COMPILER $INPUT $OUTPUT
# done

COUNT=0
echo "FAILED TESTS:"
for planet in $(ls FOLDER_5_OUTPUT) 
do
  INPUT="FOLDER_5_OUTPUT/"$planet
  OUTPUT="FOLDER_6_EXPECTED_OUTPUT/"$planet
  echo $(diff -q $INPUT $OUTPUT 2> /dev/null)| grep -Poe "(?!=5_OUTPUT/TEST_)(\d{2})"|uniq
  # diff -y $INPUT $OUTPUT 2> /dev/null
  # let "COUNT=COUNT+1"
done
# echo "Counter is "$COUNT
# echo $RESULT |tr " " "\n" 
# echo "Failed "$(echo $RESULT |tr " " "\n"|wc -l)" Out of 20"
# for planet in $(ls FOLDER_5_OUTPUT) 
# do
#   INPUT="FOLDER_5_OUTPUT/"$planet
#   OUTPUT="FOLDER_6_EXPECTED_OUTPUT/"$planet
#   diff -y $INPUT $OUTPUT 2> /dev/null
# done