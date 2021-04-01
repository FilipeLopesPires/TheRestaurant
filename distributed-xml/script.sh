#!/bin/bash

count=0
ARRAY2=("theRestaurant_Repository" "theRestaurant_Bar" "theRestaurant_Kitchen" "theRestaurant_Table" "theRestaurant_Chef" "theRestaurant_Waiter" "theRestaurant_Student")
ARRAY=()
for i in {0..15}
do
  if ping -c 1 l040101-ws0$i.ua.pt &> /dev/null
  then 
   ((count++))
   echo "PC $i is up"
    
  
    ((ARRAY[i] =$i))
  elif ping -c 1 l040101-ws$i.ua.pt &>> /dev/null
   then
  ((count++))
  echo "PC $i is up"
  ((ARRAY[$i]=$i)) 
    else
  echo "PC $i is down"
  fi
done
bar=""
repo=""
kitchen=""
table=""
ARRAY3=()
counter3=0
for i in "${ARRAY[@]}"
do
    if [ $counter3 -lt 1 ]
      then

      repo+="l040101-ws0"$i".ua.pt"
      ((counter3++))
    elif [ $counter3 -lt 2 ]
      then
      bar+="l040101-ws0"$i".ua.pt"
      ((counter3++))
    elif [ $counter3 -lt 3 ]
      then
      kitchen+="l040101-ws0"$i".ua.pt"
      ((counter3++))
    elif [ $counter3 -lt 4 ]
      then
      table+="l040101-ws0"$i".ua.pt"
      ((counter3++))
    fi

done
echo $repo $bar $kitchen $table
count=0
for i in "${ARRAY[@]}"
do

  if [ $count -lt 7 ]
    then
    if [ $i -gt 9 ]
        then
      sshpass -p 'qwerty' ssh cd0304@l040101-ws$i.ua.pt 'rm -R ./*'
      sshpass -p 'qwerty' scp ${ARRAY2[$count]}'.zip' cd0304@l040101-ws$i.ua.pt:
      sshpass -p 'qwerty' ssh cd0304@l040101-ws$i.ua.pt 'unzip ' ${ARRAY2[$count]}'.zip'
      
    else
      sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'rm -R ./*'
      sshpass -p 'qwerty' scp ${ARRAY2[$count]}'.zip' cd0304@l040101-ws0$i.ua.pt:
      sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'unzip ' ${ARRAY2[$count]}'.zip'
     
    if [ $i -eq 1 ]
      then

      xterm -hold -e "sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'bash '${ARRAY2[$count]}'.sh '" &
     elif [ $i -eq 2 ]
      then
      xterm -hold -e "sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'bash '${ARRAY2[$count]}'.sh '$repo' 22730'" &
     elif [ $i -eq 3 ]
      then
     xterm -hold -e "sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'bash '${ARRAY2[$count]}'.sh '$repo' 22730'" &
     elif [ $i -eq 4 ]
      then
      xterm -hold -e "sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'bash '${ARRAY2[$count]}'.sh '$repo' 22730'" &
     elif [ $i -eq 5 ]
      then
      xterm -hold -e "sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'bash '${ARRAY2[$count]}'.sh '$repo' 22730 '$bar' 22732 '$kitchen' 22731'" &
     elif [ $i -eq 6 ]
      then
      xterm -hold -e "sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'bash '${ARRAY2[$count]}'.sh '$repo' 22730 '$bar' 22732 '$kitchen' 22731 '$table' 22733'" &
    else

      xterm -hold -e "sshpass -p 'qwerty' ssh cd0304@l040101-ws0$i.ua.pt 'bash '${ARRAY2[$count]}'.sh '$repo' 22730 '$bar' 22732 '$table' 22733'" &
      echo unzip ${ARRAY2[$count]}
      echo 'bash '${ARRAY2[$count]}'.sh'$bar' 22732 '$table' 22733'
    fi
     fi
    ((count++))

  fi
done


echo "Online Workstations:"
echo "${ARRAY[*]}"
