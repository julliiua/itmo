with open('расписание.yaml', 'r', encoding='utf-8') as file:
    lines = file.readlines()

json_lines = ['{']
indent = 0
flag = False
ki = 0
knext = 0
for line in lines:
    if ':' in line:
        flag = True
        k = (len(line) - len(line.lstrip()))
        now_indent = len(line) - len(line.lstrip())  # вычисляем количество отступов

        if now_indent > indent:
            indent = now_indent

        if now_indent < indent:  # если меньше, то мы обязаны поставить закрывающуюся скобку
            ind = indent
            indent = now_indent
            json_lines[-1] = json_lines[-1][:-1]
            json_lines.append(' ' *ind *4 + '},')
        k = (len(line) - len(line.lstrip()))
        line = line.strip()  # Убираем лишние пробелы и пустые строки

        key, value = line.split(":", 1)
        key = key.strip()
        value = value.strip()
        if not value:  # если объект пустой, то
            if key[0] == '-':
                if k!=0:
                    ki = ki + 1
                knext = ki+2
                json_lines.append(' ' * ki*4 + f'"{key[2:]}": ' + '\n' + ' '*(ki+1)*4+ '{')   #пара


            else:
                if k ==0:
                    ki = ki+2
                json_lines.append(' ' * 4 + f'"{key}":' + '\n' + ' ' * ki*4 + '{') # день недели
        elif knext!=0:
            json_lines.append(' ' * knext * 4 + f'"{key}": "{value}",')
    elif line == "":
        json_lines.append('null')
    else:
        json_lines.append(' ' * 4 + f'"{line}"')
if flag:
    json_lines[-1] = json_lines[-1][:-1]
    for i in range(k):
        json_lines.append('}')
else:
    json_lines.append('}')
with open('расписание доп 3.json', 'w', encoding='utf-8') as file:
    for line in json_lines:
        file.write(line + '\n')