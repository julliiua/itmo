import yaml
import toml

with open('расписание.yaml', 'r', encoding='utf-8') as file:
        data = yaml.safe_load(file)

with open('расписание.toml', 'w', encoding='utf-8') as file:
        toml.dump(data,file)