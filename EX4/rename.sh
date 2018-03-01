CODE="FOLDER_C1_CUSTOM_INPUT"
EXPECTED="FOLDER_C4_CUSTOM_EXPECTED"
a=75
for i in $(ls FOLDER_C4_CUSTOM_EXPECTED); do
  new=$(printf "TEST_%04d_$i" "$a")
  mv -i -- "$CODE/$i" "$CODE/$new.txt"
  mv -i -- "$EXPECTED/$i" "$EXPECTED/$new_EXPECTED_OUTPUT.txt"
  let a=a+1
done