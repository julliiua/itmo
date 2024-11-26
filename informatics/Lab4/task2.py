import json
import re

def code(text):
    text = re.sub(r'([А-ЯЁ][а-яё]+:)', lambda i: '{' + '\n' + '    ' + f'"{i.group()[:-1]}": [' + '\n' + '        {', text)
    text = re.sub(r'- (\w{4} \d{1}\.{0,1}\d{0,}):', lambda i: '            '+ f'"{i.group()[2:-1]}": '+'{',text)
    text = re.sub(r' {4}([а-яё /]+):', lambda i: '                '+ f'"{i.group()[4:-1]}": ',text)
    text = re.sub(r': ([^{\[].+)', lambda i:': '+ f'"{i.group()[3:]}",',text)
    text = re.sub(r'(,\n {8}) {4}[\"]', lambda i: f'{i.group()[1:-1]}'+'}'+ '\n' +'        },'+ '\n' +'        {'+ '\n' +'            "',text)
    text = re.sub(r'(,\n[{]\n {4}\"[А-ЯЁ][а-яё]+\":)', lambda i: '\n' + '            }'+'\n' + '        }' +'\n' + '    ],' +'\n' + f'{i.group()[-14:]}',text)
    text = re.sub(r'(,\n\.)', lambda i: f'{i.group()[3:]}'+ '\n' + '            }'+'\n' + '        }' +'\n' + '    ]'+'\n' +'}',text)
    return text


with open('расписание.yaml', 'r', encoding='utf-8') as file:
    data = file.read() +'.'

data = code(data)
# Запись в JSON-файл
with open('расписание доп 2.json', 'w', encoding='utf-8') as file:
    file.write(data)
