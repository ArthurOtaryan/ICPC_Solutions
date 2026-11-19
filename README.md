# ICPC Solutions

Разборы и решения задач **ICPC 2026–2027 Northwestern Russia Regional Contest**
(Санкт-Петербург, 14 ноября 2026). Язык — Java.

Каждая задача лежит в своей папке `src/problems`:

- `<Буква>.java` — решение (один класс с `main`, без пакета);
- `README.md` — краткое условие, идея, почему это работает, сложность.

## Задачи

| # | Задача | Тема |
|---|--------|------|
| A | AALEX.EXE | перебор, сравнение строк |
| B | Binary Search Trees | комбинаторика на деревьях |
| C | Cable Network | геометрия, конструктив |
| D | Divide and Count | геометрия, разбор случаев |
| E | Efficient Packing | перебор, укладка коробок |
| F | Four Pieces | скобочные последовательности, подсчёт |
| G | Goin' Round | теория игр |
| H | Hidden Integer | интерактив, бинпоиск |
| I | Inverse Exchange | линейная алгебра над GF(2), конструктив |
| J | Just Stack It | жадность |
| K | KitKat Kit | неравенства, жадность |
| L | Lost on a Train | динамика по подстрокам, игра |
| M | Museum | MST, LCA, DSU |
| N | Nuts and Bolts | математика |

Решения добавляются по одной задаче в день.

## Как запустить

```bash
cd problems/A_AALEX_EXE
javac A.java
java A < input.txt
```

При отправке в тестирующую систему класс обычно нужно переименовать в `Main`.
