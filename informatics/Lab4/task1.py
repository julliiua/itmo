import yaml
import json

with open('расписание.yaml', 'r', encoding='utf-8') as file:
        data = yaml.safe_load(file)

# Записываем содержимое в файл JSON
with open('расписание доп 1.json', 'w', encoding='utf-8') as file:
        json.dump(data, file, ensure_ascii=False, indent=4)
