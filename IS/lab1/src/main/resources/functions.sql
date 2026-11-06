create or replace function delete_dragons_by_age(p_age integer) returns integer language plpgsql as $$
declare n integer;
begin
    if exists (select 1 from team t join dragon d on d.id = t.dragon_id where d.age is not distinct from p_age) then
        raise exception 'Дракон связан с командой. Удаление невозможно.';
    end if;
    delete from dragon where age is not distinct from p_age;
    get diagnostics n = row_count;
    return n;
end;
$$;

create or replace function average_wingspan() returns double precision language sql as $$
    select avg(wingspan)::double precision from dragon;
$$;

create or replace function dragons_starting_with(p_prefix text) returns setof dragon language sql as $$
    select * from dragon where name like p_prefix || '%' order by id;
$$;

create or replace function kill_dragon(p_id bigint) returns boolean language plpgsql as $$
begin
    update dragon set alive = false where id = p_id;
    if not found then raise exception 'Дракон не найден'; end if;
    return true;
end;
$$;

create or replace function create_team(p_name text, p_dragon_id bigint) returns bigint language plpgsql as $$
declare n bigint;
begin
    if p_name is null or trim(p_name) = '' then raise exception 'Укажите название команды'; end if;
    insert into team(name, dragon_id) values (p_name, p_dragon_id) returning id into n;
    return n;
end;
$$;
