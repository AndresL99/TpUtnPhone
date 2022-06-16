insert into cities values ("Buenos Aires", 1,11);
insert into cities values ("Mar del plata", 2,223);
insert into cities values ("La Plata", 3,221);
insert into cities values ("Pilar", 4,230);
insert into cities values ("Tandil", 5,249);
insert into cities values ("Mendoza", 6,260);
insert into cities values ("San Juan", 7,264);
insert into cities values ("Pilar", 8,230);
insert into cities values ("Tandil", 9,249);
insert into cities values ("Bahia Blanca", 10,291);
insert into cities values ("Cordoba", 11,351);
insert into cities values ("Santa Clara del Mar",12,2236);
insert into cities values ("Villa Gesell",13,225);

insert into users values ("nicolas", "roldan", 41306781, 2,"Buenos Aires",'nicoRoldan',1,'nico123');
insert into users values ("Andres", "Lerner", 41458332, 1,"Buenos Aires",'andreslerner','1','andres123');

insert into clients values(1, 41458332);

#insert into telephone_Lines values("2235583444",41306781);
insert into telephone_Lines values("1147475566",1);
insert into telephone_Lines values("2254583444",1);

insert into tariffs values (1,1,1,1.0,'15:00:00','20:00:00');
insert into tariffs values (2,2,1,1.0,'12:00:00','23:59:59');
insert into tariffs values (3,12,1,1.0,'12:00:00','23:59:59');
insert into tariffs values (5,12,2,20.0,'12:00:00','23:59:59');
insert into tariffs values (4,2,12,5.0,'12:00:00','23:59:59');


delimiter $$
create procedure get_clients_and_bill()
begin
	declare vTelephoneNumber varchar(30);

    declare vFinished integer default 0;

    declare cur Cursor for select telephone_lines.telephone_number from telephone_lines;

declare continue handler for not found set vFinished=1;

open cur;

getPersonas : loop
    		fetch cur into vTelephoneNumber;
        if (vFinished = 1) then
			leave getPersonas;
end if;
call liquidate_client(vTelephoneNumber);

end loop getPersonas;
close cur;
end;
$$

delimiter $$
CREATE PROCEDURE liquidate_client(pNumberCliente varchar(30))
begin
	declare vTotal float;
    declare vCant float;
    declare vDummy int;
    declare vIdClient int;
    declare vLiquidacion int;
select count(c.id_call), sum(c.duration * t.prece_x_minute) into vCant , vTotal
from calls c
         inner join tariffs t
                    on c.id_tariff = t.id_tariff
where c.id_bill is null
  and c.id_number_origin = pNumberCliente;
select tl.id_client into vIdClient from telephone_lines tl where tl.telephone_number= pNumberCliente;
insert into bills(id_client,number_calls,price_total,date_facturation,date_expiration) values (vIdClient,vCant,vTotal,now(),now());
#Se toma el id_bill
    set vLiquidacion = last_insert_id();
update calls
set id_bill = vLiquidacion
where id_number_origin = pNumberCliente and id_bill is null;
END;
$$