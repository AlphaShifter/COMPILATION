#! /bin/bash
CODE="FOLDER_C1_CUSTOM_INPUT"
EXPECTED="FOLDER_C4_CUSTOM_EXPECTED"
a=75
for plan in $(ls FOLDER_C4_CUSTOM_EXPECTED); do
  base=$(basename $plan .txt)
  new=$(printf "TEST_%03d_$base.txt" "$a")
  newexpected=$(printf "TEST_%03d_"$base"_EXPECTED_OUTPUT.txt" "$a")
  mv "$CODE/$plan" "$CODE/$new"
  mv "$EXPECTED/$plan" "$EXPECTED/$newexpected"
  let a=a+1
done