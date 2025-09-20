insert into artikl(id,naziv,proizvodjac)
values (nextval('artikl_seq'), 'Zlatna ogrlica', 'Zlatara D.O.O.'),
(nextval('artikl_seq'), 'Zlatni Delises', 'Fruit D.O.O.'),
(nextval('artikl_seq'), 'Cokoladno Mleko', 'Imlek'),
(nextval('artikl_seq'), 'Musli', 'Fun & Fit');

insert into dobavljac(id,naziv,kontakt,adresa)
values (nextval('dobavljac_seq'), 'Heraeus', '381 111 111', 'Bulevar oslobodjenja 111'),
(nextval('dobavljac_seq'), 'AD Imlek','38165426366', 'Industrijska zona bb, Beograd'),
(nextval('dobavljac_seq'), 'Fruit DOO','381 234 210', 'Futoska bb' ),
(nextval('dobavljac_seq'), 'Fun & Fit','011 3713500','Batajnicki Drum 12');

insert into porudzbina (id, datum, isporuceno, dobavljac, iznos, placeno)
values (nextval('porudzbina_seq'),to_date('01.03.2025.', 'dd.mm.yyyy.'),
        to_date('05.03.2025.', 'dd.mm.yyyy.'), 1,80000,TRUE),
        (nextval('porudzbina_seq'),to_date('08.02.2025.', 'dd.mm.yyyy.'),
        to_date('01.03.2025.', 'dd.mm.yyyy.'), 2,1000,FALSE),
        (nextval('porudzbina_seq'),to_date('01.01.2025.', 'dd.mm.yyyy.'),
        to_date('05.05.2025.', 'dd.mm.yyyy.'), 3, 2500,TRUE),
        (nextval('porudzbina_seq'),to_date('01.03.2025.', 'dd.mm.yyyy.'),
        to_date('05.03.2025.', 'dd.mm.yyyy.'), 4,3000,FALSE);

insert into stavka_porudzbine(id,redni_broj,kolicina,jedinica_mere,cena,artikl,porudzbina)
values (nextval('stavka_porudzbine_seq'), 1, 1, 'komad', 80000, 1, 1),
(nextval('stavka_porudzbine_seq'), 2, 1, 'komad', 80000, 1, 1),
(nextval('stavka_porudzbine_seq'), 1, 10, 'kg', 2500, 2, 3),
(nextval('stavka_porudzbine_seq'), 1, 5, 'litar', 1000,3, 2),
(nextval('stavka_porudzbine_seq'), 1, 10, 'kg', 3000, 4, 4);