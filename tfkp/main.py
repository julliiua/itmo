import numpy as np #для работы с мат. операциями
import matplotlib.pyplot as plt #для создания графиков

#принадлежит ли точка множеству Жюлиa
def is_in(z, c, max_iter):
    for i in range(max_iter):
        if abs(z) > 2: #тк любое С число в такой функции при |z|>0
                       # будет уходить в бесконечность, что
                       # не принадлелит мн-ву Жюлиа
            return i
        z = z ** 2 + c
    return max_iter #если точки принадлежит мн-ву

#построение графика
def plot_julia(xmin, xmax, ymin, ymax, width, height, c, max_iter):
    # Создаем массив точек на комплексной плоскости
    x = np.linspace(xmin, xmax, width)
    y = np.linspace(ymin, ymax, height)

    # Создаем пустое изображение
    img = np.zeros((height, width))

    #матрица, в которой записываем как быстро точка устремляется в бесконечность
    for i in range(height):
        for j in range(width):
            z = complex(x[j], y[i])
            img[i, j] = is_in(z, c, max_iter)

    # вычисляем значение для каждой точки
    plt.imshow(img, extent=(xmin, xmax, ymin, ymax), origin='lower', cmap='RdYlBu')
    plt.colorbar()
    plt.figtext(0.5,0.01, f"кол-во итераций: {max_iter}, зум х1, c :{c1}",ha='center', fontsize=11)
    plt.show()


# вся картинка
#xmin, xmax = -1.5, 1.5
#ymin, ymax = -1.5, 1.5

# зум 3х
#xmin, xmax = -0.3, 0.2
#ymin, ymax = 0.5, 1.0

# зум 30х
#xmin, xmax = -0.15, -0.05
#ymin, ymax = 0.7, 0.8

# зум с2 30х
xmin, xmax = -0.25, -0.15
ymin, ymax = -0.35, -0.25

# зум с3 30х
#xmin, xmax = -0.55, -0.45
#ymin, ymax = -0.2, -0.3

#размеры картинки
width, height = 2000, 2000

#Константа c (определяет форму множества Жюлиа)
c = complex(-0.5251993, 0.5251993)
c2 = complex(-0.8, 0.156)
c3 = complex(0.285, 0.01)

#кол-во итераций
max_iter1 = 100
max_iter2 = 250
max_iter3 = 500

# Построение
plot_julia(xmin, xmax, ymin, ymax, width, height, c, max_iter1)


