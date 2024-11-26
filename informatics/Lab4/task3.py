class YamlToJson: #создаем класс для преобразования файлов yaml в json
    def __init__(self, data, json_result): #принимает данные
        self.data = data#данные введеные
        self.json_result = {} #записываем преобразование

    def yaml_line(self, line): #посимвольное преобразование
        line = line.strip() #удаляем конечные и начальные пробелы
        if ": " in line: #разьиваем на "ключ : значение"
            key, value = line.split(": ", 1)
            return key.strip(), value.strip()
        elif ":" in line:
            key = line.replace(":", "").strip()
            return key, {}
        else:
            return line, None

    def yaml(self):
        current_indent = 0
        stack = [self.json_result]

        for line in self.data.splitlines(): # бежим по всем строкам, игнорируя пустые
            if not line.strip():
                continue

            indent = len(line) - len(line.lstrip())
            key, value = self.yaml_line(line)

            if indent > current_indent: #в зависимости от отступа создает новую вложеность или поднимаетяс по стеку
                if isinstance(stack[-1], dict):
                    stack[-1][last_key] = {}
                    stack.append(stack[-1][last_key])
            elif indent < current_indent:
                while current_indent > indent:
                    stack.pop()
                    current_indent -= 2

            #обновялем отступы
            current_indent = indent
            last_key = key

            if value is not None:
                stack[-1][key] = value

        return self.json_result

    '''def to_json(self):
        import json
        return json.dumps(self.json_result, ensure_ascii=False, indent=4)'''

with open('расписание.yaml', 'r', encoding='utf-8') as file:
    data = file.readlines()


    with open('расписание доп 3.json', 'w', encoding='utf-8') as file:
        for line in data:
            line = line.replace('-', ' ')
            file.write(line.rstrip() + '\n')
        for i in range(4):
            file.write('}' + '\n')