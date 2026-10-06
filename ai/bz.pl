% ============================================================
% Лабораторная работа №1
% Модуль 1. Базы знаний и онтологии
%
% Тема: База знаний о маршрутах и правилах игры Ticket to Ride
%
% Цель:
% разработать базу знаний на языке Prolog, описывающую
% основные объекты игры, отношения между ними и правила,
% позволяющие получать новые сведения с помощью логического вывода.
%
% В данной модели:
% - город является точкой маршрута
% - перегон является отдельным участком между двумя городами
% - составной маршрут состоит из нескольких перегонов
% - каждый построенный перегон приносит игроку очки в соответствии с его длиной
% - за выполненный маршрут игрок получает дополнительные очки
% - за невыполненный выбранный маршрут из итогового счёта вычитаются его очки
% - игрок имеет цвет игровых фишек
% ============================================================

% ------------------------------------------------------------
% 1. ГОРОДА
% ------------------------------------------------------------
city(petrograd).
city(moscow).
city(warszawa).
city(riga).
city(smolensk).
city(vilna).

% ------------------------------------------------------------
% 2. ИГРОКИ
% ------------------------------------------------------------
player(yuliya).
player(dasha).

player_route(yuliya, petrograd_warszawa).
player_route(dasha, moscow_riga).

% ------------------------------------------------------------
% 3. ЦВЕТ ФИШЕК ИГРОКОВ
% ------------------------------------------------------------
player_color(yuliya, black).
player_color(dasha, purple).

% ------------------------------------------------------------
% 4. ПЕРЕГОНЫ
% ------------------------------------------------------------
segment(petrograd_moscow).
segment(petrograd_vilna).
segment(moscow_smolensk).
segment(smolensk_vilna).
segment(warszawa_vilna).
segment(riga_vilna).

segment_connects(petrograd_moscow, petrograd, moscow).
segment_connects(petrograd_vilna, petrograd, vilna).
segment_connects(moscow_smolensk, moscow, smolensk).
segment_connects(smolensk_vilna, smolensk, vilna).
segment_connects(warszawa_vilna, warszawa, vilna).
segment_connects(riga_vilna, riga, vilna).

% ------------------------------------------------------------
% 5. ЦВЕТА ПЕРЕГОНОВ
% ------------------------------------------------------------
color(red).
color(blue).
color(green).
color(yellow).
color(orange).
color(white).

segment_color(petrograd_moscow, white).
segment_color(petrograd_vilna, blue).
segment_color(moscow_smolensk, orange).
segment_color(smolensk_vilna, yellow).
segment_color(warszawa_vilna, red).
segment_color(riga_vilna, green).

% ------------------------------------------------------------
% 6. ДЛИНА ПЕРЕГОНОВ
% ------------------------------------------------------------
segment_length(petrograd_moscow, 4).
segment_length(petrograd_vilna, 4).
segment_length(moscow_smolensk, 2).
segment_length(smolensk_vilna, 3).
segment_length(warszawa_vilna,3).
segment_length(riga_vilna, 4).

% ------------------------------------------------------------
% 7. МАРШРУТЫ
% ------------------------------------------------------------
route(petrograd_warszawa).
route(moscow_riga).

route_points(petrograd_warszawa, 6).
route_points(moscow_riga, 8).

route_segment(petrograd_warszawa, petrograd_vilna).
route_segment(petrograd_warszawa, warszawa_vilna).

route_segment(moscow_riga, moscow_smolensk).
route_segment(moscow_riga, smolensk_vilna).
route_segment(moscow_riga, riga_vilna).

% ------------------------------------------------------------
% 8. ЗАНЯТЫЕ ПЕРЕГОНЫ
% ------------------------------------------------------------
occupies_segment(yuliya, petrograd_vilna).
occupies_segment(yuliya, warszawa_vilna).
occupies_segment(dasha, smolensk_vilna).


% ------------------------------------------------------------
% ПРАВИЛА 
%   1. Свободный перегон
% ------------------------------------------------------------
available_segment(Segment) :-
    segment(Segment),
    \+ occupies_segment(_, Segment).

% ------------------------------------------------------------
%   2. Построенный перегон выбранного маршрута
% ------------------------------------------------------------
built_route_segment(Player, Route, Segment) :-
    player_route(Player, Route),
    route_segment(Route, Segment),
    occupies_segment(Player, Segment).

% ------------------------------------------------------------
%   3. Выполнение маршрута
% ------------------------------------------------------------
all_route_segments_owned(Player, Route) :-
    \+ (route_segment(Route, Segment),
        \+ occupies_segment(Player, Segment)).


completed_route(Player, Route) :-
    player_route(Player, Route),
    all_route_segments_owned(Player, Route).
% ------------------------------------------------------------
%   4. Получение очков
% ------------------------------------------------------------
% Очки за построенный перегон
segment_points(Player, Segment, Points) :-
occupies_segment(Player, Segment),
segment_length(Segment, Points).

% Положительные очки за выполненный маршрут
completed_route_points(Player, Route, Points) :-
completed_route(Player, Route),
route_points(Route, Points).

% Отрицательные очки за невыполненный маршрут
failed_route_points(Player, Route, Points) :-
player_route(Player, Route),
\+ completed_route(Player, Route),
route_points(Route, RoutePoints),
Points is -RoutePoints.

% Сумма очков за все построенные перегоны
total_segment_points(Player, Total) :-
findall(Points, segment_points(Player, _, Points), PointsList),
sum_list(PointsList, Total).

% Итоговая сумма очков с учётом маршрутных карт
earned_points(Player, Total) :-
total_segment_points(Player, SegmentPoints),
findall(Points, completed_route_points(Player, _, Points), CompletedPoints),
findall(Points, failed_route_points(Player, _, Points), FailedPoints),
sum_list(CompletedPoints, CompletedTotal),
sum_list(FailedPoints, FailedTotal),
Total is SegmentPoints + CompletedTotal + FailedTotal.

% ------------------------------------------------------------
%   5. Построение перегона
% ------------------------------------------------------------
player_cards(yuliya, blue, 4).
player_cards(yuliya, red, 2).
player_cards(yuliya, white, 4).
player_cards(dasha, green, 4).
player_cards(dasha, orange, 2).
player_cards(dasha, yellow, 3).

can_build_segment(Player, Segment) :-
    segment(Segment),
    segment_color(Segment, Color),
    segment_length(Segment, Length),
    player_cards(Player, Color, Cards),
    Cards >= Length,
    available_segment(Segment).

% ------------------------------------------------------------
%   6. Построение станций 
% ------------------------------------------------------------
station_built(yuliya, moscow).

can_build_station(Player, City) :-
    player(Player),
    city(City),
    segment_connects(Segment, City, _),
    occupies_segment(OtherPlayer, Segment),
    Player \= OtherPlayer.

% ------------------------------------------------------------
%   7. Использование перегона
% ------------------------------------------------------------
can_use_segment(Player, Segment, FromCity) :-
    occupies_segment(Player, Segment),
    segment_connects(Segment, City1, City2),
    (FromCity = City1 ; FromCity = City2).

can_use_segment(Player, Segment, FromCity) :-
    station_built(Player, FromCity),
    segment_connects(Segment, FromCity, _),
    occupies_segment(OtherPlayer, Segment),
    Player \= OtherPlayer.

% ============================================================
% ЗАПРОСЫ
%   1. Проверка существование города (факт)
%   ?- city(moscow).
%   
%   2. Показать цвет каждого игрока (запрос с переменной)
%   ?- player_color(Player, Color).
%   
%   3. Проверка двух фактов сразу (логическое или)
%   ?- player(yuliya), player_color(yuliya, black).
%   
%   4. Логическое ИЛИ
%   ?- player_color(Player, black) ; player_color(Player, purple).
%   
%   5. Логическое НЕ
%   ?- \+ available_segment(petrograd_vilna).
%   
%   6. Требуем выполнить правила
%   ?- built_route_segment(yuliya, petrograd_warszawa, Segment).
%   
%   7. Получение итогового количества очков игрока с использованием правила и переменной
%   ?- earned_points(yuliya, Points).
%   
%   8. Можем ли построить сводный перегон
%   ?- can_build_segment(Player, Segment).
% ============================================================
