echo -e "Задание 1: \n IP-адреса, которые сделали более 1000 запросов за сутки:"
awk '{split($4, date_arr, ":"); date=substr(date_arr[1],2); ip_str[$1 " " date]++} END {for (a in ip_str) if (ip_str[a] > 1000) print a, ip_str[a]}' access.log | sort -k3 -nr 

echo -e "Задание 2: \n Среднее время ответа сервера по статусу 200 за последние 7 дней:"
grep " 200 " access.log | awk  '{split($4, date_arr, ":"); date=substr(date_arr[1],2); split(date,day_arr,"/"); day=day_arr[1];if ((day>="20")) sum+=$10; count++} END {print sum/count}'
