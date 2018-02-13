for planet in $(ls FOLDER_5_OUTPUT2) 
do
  INPUT="FOLDER_5_OUTPUT2/"$planet
  OUTPUT="FOLDER_6_EXPECTED_OUTPUT/"$planet
  echo $planet
  printf "\nOur result:"
  sed -n 6,100p $INPUT
  printf "\nActual:"
  sed -n 6,100p $OUTPUT
  echo ">>>>>>>>>>>>><<<<<<<<<<<<<<"
  echo
done