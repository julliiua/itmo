with open('расписание.yaml', 'r', encoding='utf-8') as file:
    lines = file.readlines()

json_lines = []
indent = 0
for line in lines:

    str_current = line.strip()# Удаление начальных и конечных пробелов
    now_indent = len(line) - len(line.lstrip()) # вычисляем количество отступов
    if now_indent > indent:
        indent = now_indent

    if now_indent < indent: # если меньше, то мы обязаны поставить закрывающуюся скобку
        indent = now_indent
        json_lines[-1] = json_lines[-1][:-1]
        json_lines.append(' ' * 16 + f'}},')
    # Преобразование строки YAML в строку JSON
    key, value = str_current.split(':', 1)

    if not value:  # если объект пустой, то
        if key[0] == '-':
            json_lines.append(' ' * 12 + f'"{key[2:]}": ' + '{')
        else:
            json_lines.append(' ' * 4 + f'"{key}":' + '\n' + ' ' * 8 + '{')
    else:
        json_lines.append(' ' * (len(line) - len(line.lstrip())) * 4 + f'"{key}": "{value}",')

json_lines[-1] = json_lines[-1][:-1]

# Запись в JSON-файл
with open('расписание.json', 'w', encoding='utf-8') as file:
    file.write('{\n')
    for line in json_lines:
        file.write(line + '\n')
    for i in range(4):
        file.write('}' + '\n')
