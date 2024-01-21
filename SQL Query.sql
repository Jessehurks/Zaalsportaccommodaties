drop database if exists zaalsportaccommodaties;
create database zaalsportaccommodaties;
use zaalsportaccommodaties;

create table accommodaties
(
	accommodatie varchar(255) primary key
);

create table zalen
(
	zaalnaam varchar(255) primary key,
    accommodatie varchar(255)
);

create table zaalgegevens
(
	zaalnaam varchar(255) primary key,
    telefoonnummer varchar(255),
    website varchar(255),
	constraint zaalnaam_fk foreign key (zaalnaam) references zalen(zaalnaam)
);

create table zaallocaties
(
	zaalnaam varchar(255) primary key,
    adres varchar(255),
    postcode varchar(255),
    woonplaats varchar(255),
    xkoord varchar(255),
    ykoord varchar(255),
    constraint zaalnaam_locatie_fk foreign key (zaalnaam) references zalen(zaalnaam)
);

create table gebruikers
(
	gebruikersnaam varchar(255) primary key,
    wachtwoord varchar(255),
    type varchar(255)
);

insert into gebruikers (gebruikersnaam, wachtwoord, type) values ("1", "1", "Beheerder");
insert into gebruikers (gebruikersnaam, wachtwoord, type) values ("2", "2", "Zaaleigenaar");