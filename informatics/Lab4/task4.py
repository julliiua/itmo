import time
start_time = time.perf_counter()
for i in range(100):
    import task0
end_time = time.perf_counter()

print(f"Основное - {end_time - start_time}")

start_time = time.perf_counter()
for i in range(100):
    import task1
end_time = time.perf_counter()

print(f"Доп. №1  - {end_time - start_time}")

start_time = time.perf_counter()
for i in range(100):
    import task2
end_time = time.perf_counter()

print(f"Доп. №2  - {end_time - start_time}")

start_time = time.perf_counter()
for i in range(100):
    import task3
end_time = time.perf_counter()

print(f"Доп. №3  - {end_time - start_time}")