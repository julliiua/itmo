with open('расписание.yaml', 'r', encoding='utf-8') as file:
    lines = file.readlines()

json_lines = []
indent_level = 0

for line in lines:
    # Удаление начальных и конечных пробелов
    stripped_line = line.strip()

    # Проверка уровня отступов для преобразования
    current_indent = len(line) - len(line.lstrip())
    if current_indent > indent_level:
        indent_level = current_indent

    if current_indent < indent_level:
        indent_level = current_indent
        json_lines[-1] = json_lines[-1][:-1]
        json_lines.append(' ' * 16 + f'}},')

    # Преобразование строки YAML в строку JSON
    if ':' in stripped_line:
        key, value = stripped_line.split(':', 1)
        key = key.strip()
        value = value.strip()

        if not value:
            if key[0]=='-':
                json_lines.append(' '*12+f'"{key[2:]}": ' +'{')
            else:
                json_lines.append(' '*4+f'"{key}":' +'\n'+ ' '*8 +'{')
        else:
            json_lines.append(' ' * (len(line) - len(line.lstrip()))*4 + f'"{key}": "{value}",')
json_lines[-1] = json_lines[-1][:-1]



# Запись в JSON-файл
with open('расписание.json', 'w', encoding='utf-8') as file:
    file.write('{\n')
    for line in json_lines:
        file.write(line.rstrip() + '\n')
    for i in range(4):
        file.write('}'+'\n')
