import pandas as pd
import matplotlib.pyplot as plt

file_path = 'dop3.csv'
data = pd.read_csv(file_path)

data['<DATE>'] = pd.to_datetime(data['<DATE>'], format='%d/%m/%Y')

boxplot_data = []
labels = []

data = data.rename(columns={
    '<OPEN>': 'открытие',
    '<HIGH>': 'макс',
    '<LOW>': 'мин',
    '<CLOSE>': 'закрытие'
})

dates = ['2018-09-07', '2018-10-09', '2018-11-07', '2018-12-07']
for date in dates:
    for column in ['открытие', 'макс', 'мин', 'закрытие']:

        col = data[data['<DATE>'] == date][column]
        boxplot_data.append(col)
        labels.append(f"{column}\n{date}")
plt.figure(figsize=(12, 6))
box = plt.boxplot(boxplot_data, labels=labels, patch_artist=True)
colors = ['lightgreen','lightgreen','lightgreen','lightgreen','lightcoral','lightcoral','lightcoral','lightcoral','lightblue','lightblue','lightblue','lightblue','peachpuff','peachpuff','peachpuff','peachpuff']
for patch, color in zip(box['boxes'],colors):
    patch.set_facecolor(color)
plt.grid(axis='y', linestyle='--', alpha=0.5)
plt.xticks(rotation = 45)
plt.tight_layout()
plt.title('Дополнительное задание 3')
plt.show()
