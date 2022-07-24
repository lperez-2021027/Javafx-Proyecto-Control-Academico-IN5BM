Drop database if exists db_control_academico_in5bm;
create database if not exists db_control_academico_in5bm;
use db_control_academico_in5bm;

/*
* Estudiantes:			Carnet:
* 
* Luis Carlos Pérez		2021027
*
* Grupo: 2
* Codigo Tecnico: IN5BM
* Fecha de creacion: 26/04/2022
* Fecha de modificacion: --/--/--
*/

-- Creación de tablas instrucciones DDL
drop table if exists alumnos;
create table alumnos (
	carne varchar(7) not null,
	nombre1 varchar(15) not null,	
	nombre2 varchar(15),
	nombre3 varchar(15),
	apellido1 varchar(15) not null,
	apellido2 varchar(15),
    constraint pk_alumnos primary key (carne)
);


drop table if exists instructores;
create table instructores (
	id int auto_increment not null,
	nombre1 varchar(15) not null,	
	nombre2 varchar(15),
	nombre3 varchar(15),
	apellido1 varchar(15) not null,
	apellido2 varchar(15),
    direccion varchar(45),
    email varchar(45) not null,
    telefono varchar(8) not null,
    fecha_nacimiento date,
    constraint pk_instructores primary key (id)
);

drop table if exists salones;
create table salones (
	codigo_salon varchar(5) not null,
    descripcion varchar(45),
    capacidad_maxima int not null,
    edificio varchar(15),
    nivel int,
    constraint pk_salones primary key (codigo_salon)
);

drop table if exists carreras_tecnicas;
create table carreras_tecnicas (
	codigo_tecnico varchar(6) not null,
    carrera varchar(45) not null,
    grado varchar(10) not null,
    seccion char(1) not null,
    jornada varchar(10) not null,
    constraint pk_carreras_tecnicas primary key(codigo_tecnico)
);

drop table if exists horarios;
create table horarios (
	id int auto_increment not null,
    horario_inicio time not null,
    horario_final time not null,
    lunes tinyint(1),
    martes tinyint(1), 
    miercoles tinyint(1),
    jueves tinyint(1),
    viernes tinyint(1),
    constraint pk_horarios primary key(id)
);

drop table if exists cursos;
create table cursos (
	id int auto_increment not null,
    nombre_curso varchar(255) not null,
    ciclo year,
    cupo_maximo int,
    cupo_minimo int,
	carrera_tecnica_id varchar(6),
    horario_id int,
    instructor_id int,
    salon_id varchar(5),
    constraint pk_cusrsos primary key(id),
    constraint fk_cursos_carreras_tecnicas
    foreign key (carrera_tecnica_id)
    references carreras_tecnicas(codigo_tecnico)
    on delete cascade on update cascade,
    constraint fk_cursos_carreras_horarios
    foreign key (horario_id)
    references horarios(id)
    on delete cascade on update cascade,
    constraint fk_cursos_instructores
    foreign key (instructor_id)
    references instructores(id)
    on delete cascade on update cascade,
    constraint fk_cursos_salones
    foreign key (salon_id)
    references salones(codigo_salon)
    on delete cascade on update cascade
);

drop table if exists asignaciones_alumnos;
create table asignaciones_alumnos (
	id int not null auto_increment,
    alumno_id varchar(7),
    curso_id int,	
    fecha_asignacion datetime,
    constraint pk_asignaciones_alumnos primary key(id),
    constraint fk_asignaciones_alumnos_alumnos
    foreign key(alumno_id)
    references alumnos(carne)
    on delete cascade on update cascade,
    constraint fk_asignaciones_alumnos_cursos
    foreign key(curso_id)
    references cursos(id)
    on delete cascade on update cascade
);

drop table if exists roles;
create table roles (
	id int not null,
    descripcion varchar(50) not null,
    primary key (id)
);

drop table if exists usuarios;
create table usuarios (
	usuario varchar(25) not null,
    pass varchar(25) not null,
    nombre varchar(50) not null,
    roles_id int not null,
    primary key (usuario),
    constraint fk_usuarios_roles foreign key (roles_id) references roles(id)
    on delete cascade on update cascade
);

-- Inserción de datos instrucciones DML

-- Datos tabla roles
insert into roles (id, descripcion) 
values (1, "Administrador");
insert into roles (id, descripcion) 
values (2, "Estandar");

-- Datos tabla usuarios
insert into usuarios(usuario, pass, nombre, roles_id) 
values ("root", "admin", "Luis Pérez", 1);
insert into usuarios(usuario, pass, nombre, roles_id) 
values ("kinal", "12345", "Carlos Pérez", 2);

-- Datos tabla alumnos
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2016021', 'Luisa', 'Fernanda', "", 'Ortiz', 'Monterroso');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2018215', 'Esteban', 'Mauricio', "", 'Guerrero', 'Arriola');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2019356', 'Allan', 'Gabriel', "", 'Martinez', 'Mendoza');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2020165', 'Diego', 'Rene', "", 'Diaz', 'Mazariegos');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2020482', 'Bryan', 'Miguel', "", 'Herrera', 'Avila');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2021040', 'José', 'Roberto', "", 'Saldaña', 'Arrazola');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2021092', 'Marta', 'Isabela', "", 'Sanches', 'Ramirez');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2021346', 'Andres', 'Eduardo', "", 'Perez', 'Diaz');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2021356', 'Javier', 'Esteban', "", 'Garcia', 'Martinez');
insert into alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2)
values ('2020465', 'Jorge', 'Javier', "", 'Gutierrez', 'Estrada');

-- Datos tabla instructores
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Andres', 'Eduardo', "", 'Hernandez', 'Castillo', '5ta avenida', 'Andres@gmail.com', '64581245', '1998-04-21');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Juan', 'Esteban', "", 'Vasquez', 'Ramirez', '6ta avenida', 'Juan@gmail.com', '34685946', '2000-09-15');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Luis', 'Eduardo', "", 'Rodriguez', 'Campos', '4ta avenida', 'luis@gmail.com', '49567812', '2003-01-12');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Vicente', 'Francisco', "", 'Martinez', 'Salazar', '2da avenida', 'Vicente@gmail.com', '34695168', '1994-12-24');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('David', 'Miguel', "", 'Mazariegos', 'Diaz', '8va avenida', 'David@gmail.com', '64951234', '1990-03-10');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Guillermo', 'Sebastian', "", 'Ramirez', 'Monterroso', '11va avenida', 'Guillermo@gmail.com', '19468579', '1999-10-29');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Jose', 'Omar', "", 'Leon', 'Castillo', '15va avenida', 'Jose@gmail.com', '34916582', '2004-05-16');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Maria', 'Isabel', "", 'Gallardo', 'Herrera', '5ta avenida', 'Maria@gmail.com', '86495261', '1995-11-03');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Fernanda', 'Beatriz', "", 'Monterroso', 'Guadalupe', '1ra avenida', 'Fernanda@gmail.com', '48613562', '1976-08-23');
insert into instructores (nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
values ('Lucas', 'Raul', "", 'Enriqez', 'Galvez', '2da avenida', 'Lucas@gmail.com', '79231648', '1984-09-16');


-- Datos tabla salones
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-20','Salon de perito en informatica', 30, 'kinal', '2');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-21','Salon de perito en electronica', 45, 'kinal', '5');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-22','Salon de perito en mecanica automotriz', 25, 'kinal', '1');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-23','Salon de perito en diseño gráfico', 35, 'kinal', '4');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-24','Salon de perito en mecatrónica', 30, 'kinal', '1');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-25','Salon de perito en administración de empresas', 27, 'kinal', '7');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-26','Salon de perito en marketing', 20, 'kinal', '9');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-27','Salon de perito en negocios internacionales', 40, 'kinal', '8');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-28','Salon de perito en ingenieria industrial', 35, 'kinal', '2');
insert into salones (codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
values ('C-29','Salon de perito en contabilidad', 50, 'kinal', '3');

-- Datos tabla carreras_tecnicas
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('IN5BM','Perito en iformática', '5to', 'B' ,'Matutina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('EB4CV','Perito en electronica', '4to', 'C' ,'Vespertina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('MA5DM','Perito en mecanica automotriz', '5to', 'D' ,'Matutina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('DG6AV','Perito en diseño gráfico', '6to', 'A' ,'Vespertina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('MB4BM','Perito en mecatrónica', '4to', 'B' ,'Matutina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('IN5EV','Perito en administración de empresas', '5to', 'E' ,'Vespertina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('MD4AM','Perito en marketing digital', '4to', 'A' ,'Matutina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('NI5CV','Perito en negocios internacionales', '5to', 'C' ,'Vespertina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('II6EM','Perito en ingenieria industrial', '6to', 'E' ,'Matutina');
insert into carreras_tecnicas (codigo_tecnico, carrera, grado, seccion, jornada)
values ('CB4DV','Perito en contabilidad', '4to', 'D' ,'Vespertina');

-- Datos tabla horarios
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('07:05:00', '12:05:00', 1, 1, 0, 0, 0);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('07:15:00', '12:30:00', 0, 0, 1, 1, 0);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('08:05:00', '13:05:00', 0, 0, 0, 0, 1);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('13:05:00', '18:05:00', 1, 1, 0, 0, 0);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('14:05:00', '18:05:00', 0, 0, 1, 1, 0);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('14:30:00', '18:05:00', 0, 0, 0, 0, 1);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('08:25:00', '16:50:00', 1, 0, 1, 0, 1);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('10:15:00', '22:10:00', 1, 0, 0, 0, 0);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('11:30:00', '17:20:00', 1, 0, 1, 1, 0);
insert into horarios (horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
values ('14:00:00', '22:45:00', 0, 0, 0, 0, 0);

-- Datos tabla cursos
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en iformática", '2022', 35, 17, 'IN5BM', 1, 10, 'C-20');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en electronica", '2022', 35, 17, 'EB4CV', 2, 9, 'C-21');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en mecanica automotriz", '2022', 35, 17, 'MA5DM', 3, 8, 'C-22');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en contabilidad", '2022', 35, 17, 'CB4DV', 4, 7, 'C-23');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en diseño gráfico", '2022', 35, 17, 'DG6AV', 5, 6, 'C-24');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en mecatrónica", '2022', 35, 17, 'MB4BM', 1, 5, 'C-25');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en administración de empresas", '2022', 35, 17, 'IN5EV', 2, 4, 'C-26');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en marketing digital", '2022', 35, 17, 'MD4AM', 3, 3, 'C-27');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en negocios internacionales", '2022', 35, 17, 'NI5CV', 4, 2, 'C-28');
insert into cursos (nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
values ("Perito en ingenieria industrial", '2022', 35, 17, 'II6EM', 5, 1, 'C-29');

-- Datos tabla asignaciones_alumnos
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2016021", 1, '2021-12-25 08:00:08');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2018215", 2, '2021-12-26 10:11:40');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2019356", 8, '2021-12-27 09:30:10');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2020165", 9, '2021-12-28 11:07:48');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2020482", 7, '2022-01-09 07:34:17');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2021040", 5, '2022-01-10 12:07:58');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2021092", 3, '2022-01-11 13:52:23');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2021346", 4, '2022-01-13 09:16:45');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2021356", 10, '2022-01-15 11:25:15');
insert into asignaciones_alumnos (alumno_id, curso_id, fecha_asignacion)
values ("2020465", 6, '2022-01-18 07:45:08');

-- Procedimientos almacenados(CRUD)

/*
CRUD tabla alumnos
*/

-- Create
DELIMITER $$
drop procedure if exists sp_alumnos_create $$
create procedure sp_alumnos_create(
	in _carne varchar(15), 
    in _nombre1 varchar(15),
    in _nombre2 varchar(15),
    in _nombre3 varchar(15),
    in _apellido1 varchar(15),
    in _apellido2 varchar(15)
)
begin
	insert into alumnos (
		carne,
        nombre1,
        nombre2,
        nombre3,
        apellido1,
        apellido2
    ) 
    values (
		_carne,
        _nombre1,
        _nombre2,
        _nombre3,
        _apellido1,
        _apellido2
    );
end $$
DELIMITER ;

-- Read
DELIMITER $$
drop procedure if exists sp_alumnos_read $$
create procedure sp_alumnos_read()
begin
	select
		a.carne,
        a.nombre1,
        a.nombre2,
        a.nombre3,
        a.apellido1,
        a.apellido2
	from 
		alumnos as a;
end $$
DELIMITER ;

-- Read by id
DELIMITER $$
drop procedure if exists sp_alumnos_read_by_id $$
create procedure sp_alumnos_read_by_id(
	in _carne varchar(15)
)
begin
	select
		a.carne,
        a.nombre1,
        a.nombre2,
        a.nombre3,
        a.apellido1,
        a.apellido2
	from 
		alumnos as a
    where
		carne = _carne;
end $$
DELIMITER ;

-- Update
DELIMITER $$
drop procedure if exists sp_alumnos_update $$
create procedure sp_alumnos_update(
	in _carne varchar(15), 
    in _nombre1 varchar(15),
    in _nombre2 varchar(15),
    in _nombre3 varchar(15),
    in _apellido1 varchar(15),
    in _apellido2 varchar(15) 
)
begin
	update
		alumnos
	set
		nombre1 = _nombre1,
        nombre2 = _nombre2,
        nombre3 = _nombre3,
        apellido1 = _apellido1,
        apellido2 = _apellido2
	where
		carne = _carne;
end $$
DELIMITER ;

-- Delete
DELIMITER $$
drop procedure if exists sp_alumnos_delete $$
create procedure sp_alumnos_delete( 
	in _carne varchar(15)
)
begin
	delete from 
		alumnos
	where
		carne = _carne;
end $$
DELIMITER ;

-- Reporte alumnos
DELIMITER $$
drop procedure if exists sp_alumnos_report$$
create procedure sp_alumnos_report()
begin
	select
		a.carne,
        concat(
			a.nombre1, " ",
			if(a.nombre2 is null, "", a.nombre2), " ",
            if(a.nombre3 is null, "", a.nombre3), " ",
            a.apellido1, " ",
            if(a.apellido2 is null, "", a.apellido2)
        ) as nombre_completo
	from
		alumnos as a;
end $$
DELIMITER ;
-- call sp_alumnos_report()

-- Contar registros
DELIMITER $$
drop procedure if exists sp_alumnos_count $$
create procedure sp_alumnos_count()
begin
	select
		count(carne)
	from
		alumnos;
end $$
DELIMITER ;
-- call sp_alumnos_count()

/*
CRUD tabla instructores
*/

-- Create
DELIMITER $$
drop procedure if exists sp_instructores_create $$
create procedure sp_instructores_create(
    in _nombre1 varchar(15),
    in _nombre2 varchar(15),
    in _nombre3 varchar(15),
    in _apellido1 varchar(15),
    in _apellido2 varchar(15),
    in _direccion varchar(45),
    in _email varchar(45),
    in _telefono varchar(8),
    in _fecha_nacimiento date
)
begin
	insert into instructores (
		nombre1,
        nombre2,
        nombre3,
        apellido1,
        apellido2,
        direccion,
        email,
        telefono,
        fecha_nacimiento
	)
    values (
		_nombre1,
        _nombre2,
        _nombre3,
        _apellido1,
        _apellido2,
        _direccion,
        _email,
        _telefono,
        _fecha_nacimiento
    );
end $$
DELIMITER ;

-- Read
DELIMITER $$
drop procedure if exists sp_instructores_read $$
create procedure sp_instructores_read()
begin
	select
		i.id,
        i.nombre1,
        i.nombre2,
        i.nombre3,
        i.apellido1,
        i.apellido2,
        i.direccion,
        i.email,
        i.telefono,
        i.fecha_nacimiento
	from instructores as i;
end $$
DELIMITER ;

-- Read by id
DELIMITER $$
drop procedure if exists sp_instructores_read_by_id $$
create procedure sp_instructores_read_by_id(
	in _id int
)
begin
	select
		i.id,
        i.nombre1,
        i.nombre2,
        i.nombre3,
        i.apellido1,
        i.apellido2,
        i.direccion,
        i.email,
        i.telefono,
        i.fecha_nacimiento
	from 
		instructores as i
	where
		id = _id;
end $$
DELIMITER ;

-- Update
DELIMITER $$
drop procedure if exists sp_instructores_update $$
create procedure sp_instructores_update(
	in _id int,
	in _nombre1 varchar(15),
    in _nombre2 varchar(15),
    in _nombre3 varchar(15),
    in _apellido1 varchar(15),
    in _apellido2 varchar(15),
    in _direccion varchar(45),
    in _email varchar(45),
    in _telefono varchar(8),
    in _fecha_nacimiento date
)
begin
	update 
		instructores 
	set
		nombre1 = _nombre1,
		nombre2 = _nombre2,
		nombre3 = _nombre3,
		apellido1 = _apellido1,
		apellido2 = _apellido2,
		direccion = _direccion,
		email = _email,
		telefono = _telefono,
		fecha_nacimiento = _fecha_nacimiento
	where
		id = _id;
end $$
DELIMITER ;

-- Delete
DELIMITER $$
drop procedure if exists sp_instructores_delete $$
create procedure sp_instructores_delete(
	in _id int
)
begin
	delete from
		instructores
	where
		id = _id;
end $$
DELIMITER ;

-- Reporte instructores
DELIMITER $$
drop procedure if exists sp_instructores_report$$
create procedure sp_instructores_report()
begin
	select 
		i.id, 
        concat(
			i.nombre1, 
			if(nombre2 is null, "", nombre2), " ",
			if(nombre3 is null, "", nombre3), " ", 
			apellido1, " ", 
			if(apellido2 is null, "", apellido2)
        ) as nombre_completo,
		i.direccion,
        i.email,
        i.telefono,
		i.fecha_nacimiento
	from 
		instructores as i
	order by i.id asc;
end $$
DELIMITER ;
-- call sp_instructores_report()

-- Contar registros 
DELIMITER $$
drop procedure if exists sp_instructores_count $$
create procedure sp_instructores_count()
begin
	select
		count(id)
	from
		asignaciones_alumnos;
end $$
DELIMITER ;
-- call sp_instructores_count()

/*
CRUD tabla salones
*/

-- Create
DELIMITER $$
drop procedure if exists sp_salones_create $$
create procedure sp_salones_create (
	in _codigo_salon varchar(5),
    in _descripcion varchar(45),
    in _capacidad_maxima int,
    in _edificio varchar(15),
    in _nivel int
)
begin
	insert into salones (
		codigo_salon,
        descripcion,
        capacidad_maxima,
        edificio,
        nivel
    )
    values (
		_codigo_salon,
        _descripcion,
        _capacidad_maxima,
        _edificio,
        _nivel
    );
end $$
DELIMITER ;

-- Read
DELIMITER $$
drop procedure if exists sp_salones_read $$
create procedure sp_salones_read()
begin
	select
		s.codigo_salon,
        s.descripcion,
        s.capacidad_maxima,
        s.edificio,
        s.nivel
	from 
		salones as s;
end $$
DELIMITER ;

-- Read by id
DELIMITER $$
drop procedure if exists sp_salones_read_by_id $$
create procedure sp_salones_read_by_id(
	in _codigo_salon varchar(5)
)
begin
	select
		s.codigo_salon,
        s.descripcion,
        s.capacidad_maxima,
        s.edificio,
        s.nivel
	from 
		salones as s
	where
		codigo_salon = _codigo_salon;
end $$
DELIMITER ;

-- Update
DELIMITER $$
drop procedure if exists sp_salones_update $$
create procedure sp_salones_update(
	in _codigo_salon varchar(5),
    in _descripcion varchar(45),
    in _capacidad_maxima int,
    in _edificio varchar(15),
    in _nivel int
)
begin
	update 
		salones 
	set
        descripcion = _descripcion,
        capacidad_maxima = _capacidad_maxima,
        edificio = _edificio,
        nivel = _nivel
	where
		codigo_salon = _codigo_salon;
end $$
DELIMITER ;

-- Delete 
DELIMITER $$
drop procedure if exists sp_salones_delete $$
create procedure sp_salones_delete(
	in _codigo_salon varchar(5)
)
begin
	delete from
		salones
	where
		codigo_salon = _codigo_salon;
end $$
DELIMITER ;

-- Reporte salones
DELIMITER $$
drop procedure if exists sp_salones_report $$
create procedure sp_salones_report()
begin
	select
		s.codigo_salon,
        if(s.descripcion is null, "", s.descripcion) as descripcion,
        s.capacidad_maxima,
        if(s.edificio is null, "", s.edificio) as edificio,
        if(s.nivel is null, "", s.nivel) as nivel
	from
		salones as s
	order by s.codigo_salon asc;
end $$
DELIMITER ;
-- call sp_salones_report()

-- Contar registros
DELIMITER $$
drop procedure if exists sp_salones_count $$
create procedure sp_salones_count()
begin
	select
		count(codigo_salon)
	from
		salones;
end $$
DELIMITER ;
-- call sp_salones_count()

/*
CRUD tabla carreras_tecnicas
*/

-- Create
DELIMITER $$
drop procedure if exists sp_carreras_tecnicas_create $$
create procedure sp_carreras_tecnicas_create(
	in _codigo_tecnico varchar(6),
    in _carrera varchar(45),
    in _grado varchar(10),
    in _seccion char(1),
    in _jornada varchar(10)
)
begin
	insert into carreras_tecnicas (
		codigo_tecnico,
        carrera,
        grado,
        seccion,
        jornada
	)
	values (
		_codigo_tecnico,
        _carrera,
        _grado,
        _seccion,
        _jornada
	);
end $$
DELIMITER ;

-- Read
DELIMITER $$
drop procedure if exists sp_carreras_tecnicas_read $$
create procedure sp_carreras_tecnicas_read()
begin
	select 
		ct.codigo_tecnico,
        ct.carrera,
        ct.grado,
        ct.seccion,
        ct.jornada
	from
		carreras_tecnicas as ct;
end $$
DELIMITER ;

-- Read by id
DELIMITER $$
drop procedure if exists sp_carreras_tecnicas_read_by_id $$
create procedure sp_carreras_tecnicas_read_by_id(
	in _codigo_tecnico varchar(6)
)
begin
	select 
		ct.codigo_tecnico,
        ct.carrera,
        ct.grado,
        ct.seccion,
        ct.jornada
	from
		carreras_tecnicas as ct
	where
		codigo_tecnico = _codigo_tecnico;
end $$
DELIMITER ;

-- Update

DELIMITER $$
drop procedure if exists sp_carreras_tecnicas_update $$
create procedure sp_carreras_tecnicas_update(
	in _codigo_tecnico varchar(6),
    in _carrera varchar(45),
    in _grado varchar(10),
    in _seccion char(1),
    in _jornada varchar(10)
)
begin
	update
		carreras_tecnicas
	set
        carrera = _carrera,
        grado = _grado,
        seccion = _seccion,
        jornada = _jornada
	where
		codigo_tecnico = _codigo_tecnico;
end $$
DELIMITER ;

-- Delete
DELIMITER $$
drop procedure if exists sp_carreras_tecnicas_delete $$
create procedure sp_carreras_tecnicas_delete(
	in _codigo_tecnico varchar(6)
)
begin
	delete from
		carreras_tecnicas
	where
		codigo_tecnico = _codigo_tecnico;
end $$
DELIMITER ;

-- Reporte carreras tecnicas
DELIMITER $$
drop procedure if exists sp_carreras_tecnicas_report $$
create procedure sp_carreras_tecnicas_report()
begin
	select
		ct.codigo_tecnico,
        ct.carrera,
        ct.grado,
        ct.seccion,
        ct.jornada
	from
		carreras_tecnicas as ct;
end $$
DELIMITER ;
-- call sp_carreras_tecnicas_report()

-- Contar registros
DELIMITER $$
drop procedure if exists sp_carreras_tecnicas_count $$
create procedure sp_carreras_tecnicas_count()
begin
	select
		count(codigo_tecnico)
	from
		carreras_tecnicas;
end $$
DELIMITER ;
-- call sp_carreras_tecnicas_count()

/*
CRUD tabla horarios
*/

-- Create
DELIMITER $$
drop procedure if exists sp_horarios_create $$
create procedure sp_horarios_create(
	in _horario_inicio time,
    in _horario_final time,
    in _lunes tinyint(1),
    in _martes tinyint(1),
    in _miercoles tinyint(1),
    in _jueves tinyint(1),
    in _viernes tinyint(1)
)
begin
	insert into horarios (
		horario_inicio,
        horario_final,
        lunes,
        martes,
        miercoles,
        jueves,
        viernes
	)
	values (
		_horario_inicio,
        _horario_final,
        _lunes,
        _martes,
        _miercoles,
        _jueves,
        _viernes
	);
end $$
DELIMITER ;

-- Read
DELIMITER $$
drop procedure if exists sp_horarios_read $$
create procedure sp_horarios_read()
begin
	select
		h.id,
		h.horario_inicio,
        h.horario_final,
        h.lunes,
        h.martes,
        h.miercoles,
        h.jueves,
        h.viernes
	from
		horarios as h;
end $$
DELIMITER ;

-- Read by id
DELIMITER $$
drop procedure if exists sp_horarios_read_by_id $$
create procedure sp_horarios_read_by_id(
	in _id int
)
begin
	select
		h.id,
		h.horario_inicio,
        h.horario_final,
        h.lunes,
        h.martes,
        h.miercoles,
        h.jueves,
        h.viernes
	from
		horarios as h
	where
		id = _id;
end $$
DELIMITER ;

-- Update
DELIMITER $$
drop procedure if exists sp_horarios_update $$
create procedure sp_horarios_update(
	in _id int,
	in _horario_inicio time,
    in _horario_final time,
    in _lunes tinyint(1),
    in _martes tinyint(1),
    in _miercoles tinyint(1),
    in _jueves tinyint(1),
    in _viernes tinyint(1)
)
begin
	update
		horarios
	set
		horario_inicio = _horario_inicio,
        horario_final = _horario_final,
        lunes = _lunes,
        martes = _martes,
        miercoles = _miercoles,
        jueves = _jueves,
        viernes = _viernes
	where
		id = _id;
end $$
DELIMITER ;

-- Delete
DELIMITER $$
drop procedure if exists sp_horarios_delete $$
create procedure sp_horarios_delete(
	in _id int
)
begin
	delete from
		horarios
	where
		id = _id;
end $$
DELIMITER ;

-- Reporte horarios
DELIMITER $$
drop procedure if exists sp_horarios_report $$
create procedure sp_horarios_report()
begin
	select
		h.id,
        h.horario_inicio,
        h.horario_final,
        if(h.lunes is true, "Si", "No") as lunes,
        if(h.martes is true, "Si", "No") as martes,
        if(h.miercoles is true, "Si", "No") as miercoles,
        if(h.jueves is true, "Si", "No") as jueves,
        if(h.viernes is true, "Si", "No") as viernes
	from
		horarios as h
	order by id asc;
end $$
DELIMITER ;
-- call sp_horarios_report()

-- Contar registros
DELIMITER $$
drop procedure if exists sp_horarios_count $$
create procedure sp_horarios_count()
begin
	select
		count(id)
	from
		horarios;
end $$
DELIMITER ;
-- call sp_carreras_tecnicas_count()

/*
CRUD tabla cursos
*/

-- Create
DELIMITER $$
drop procedure if exists sp_cursos_create $$
create procedure sp_cursos_create(
	in _nombre_curso varchar(255),
    in _ciclo year,
    in _cupo_maximo int,
    in _cupo_minimo int,
    in _carreras_tecnicas_id varchar(6),
    in _horario_id int,
    in _instructor_id int,
    in _salon_id varchar(5)
)
begin
	insert into cursos(
		nombre_curso,
        ciclo,
        cupo_maximo,
        cupo_minimo,
        carrera_tecnica_id,
        horario_id,
        instructor_id,
        salon_id
	)
	values (
		_nombre_curso,
        _ciclo,
        _cupo_maximo,
        _cupo_minimo,
        _carreras_tecnicas_id,
        _horario_id,
        _instructor_id,
        _salon_id
	);
end $$
DELIMITER ;

CALL sp_cursos_create('Perito', 2022, 1960, 1960, 'CB4DV', 2, 2, 'C-20');

-- Read
DELIMITER $$
drop procedure if exists sp_cursos_read $$
create procedure sp_cursos_read()
begin
	select 
		c.id,
		c.nombre_curso,
        c.ciclo,
        c.cupo_maximo,
        c.cupo_minimo,
        c.carrera_tecnica_id,
        c.horario_id,
        c.instructor_id,
        c.salon_id
	from
		cursos as c;
end $$
DELIMITER ;

-- Read by id
DELIMITER $$
drop procedure if exists sp_cursos_read_by_id $$
create procedure sp_cursos_read_by_id(
	in _id int
)
begin
	select 
		c.id,
		c.nombre_curso,
        c.ciclo,
        c.cupo_maximo,
        c.cupo_minimo,
        c.carrera_tecnica_id,
        c.horario_id,
        c.instructor_id,
        c.salon_id
	from
		cursos as c
	where
		id = _id;
end $$
DELIMITER ;

-- Update
DELIMITER $$
drop procedure if exists sp_cursos_update $$
create procedure sp_cursos_update(
	in _id int,
    in _nombre_curso varchar(255),
    in _ciclo year,
    in _cupo_maximo int,
    in _cupo_minimo int,
    in _carreras_tecnicas_id varchar(6),
    in _horario_id int,
    in _instructor_id int,
    in _salon_id varchar(5)
)
begin
	update
		cursos
	set
		nombre_curso = _nombre_curso,
        ciclo = _ciclo,
        cupo_maximo = _cupo_maximo,
        cupo_minimo = _cupo_minimo,
        carrera_tecnica_id = _carreras_tecnicas_id,
        horario_id = _horario_id,
        instructor_id = _instructor_id,
        salon_id = _salon_id
	where
		id = _id;
end $$
DELIMITER ;

-- Delete
DELIMITER $$
drop procedure if exists sp_cursos_delete $$
create procedure sp_cursos_delete(
	in _id int
)
begin
	delete from
		cursos
	where
		id = _id;
end $$
DELIMITER ;

-- Reporte cursos
DELIMITER $$
drop procedure if exists sp_cursos_report $$
create procedure sp_cursos_report()
begin
	select
		c.id,
        c.nombre_curso,
        if(c.ciclo is null, "", c.ciclo) as ciclo,
        if(c.cupo_maximo is null, "", c.cupo_maximo) as cupo_maximo,
        if(c.cupo_minimo is null, "", c.cupo_minimo) as cupo_minimo,
        c.carrera_tecnica_id,
        c.horario_id,
        h.horario_inicio,
        h.horario_final,
        c.instructor_id,
        concat(
			i.nombre1, " ",
            i.apellido1
        ) as nombre_instructor,
        c.salon_id
	from
		cursos as c
        inner join horarios as h
        inner join instructores as i
	on 
		c.horario_id = h.id and c.instructor_id = i.id
	order by c.id asc;
end $$
DELIMITER ;
-- call sp_cursos_report();

-- Reporte cursos por id
DELIMITER $$
drop procedure if exists sp_cursos_report_by_id $$
create procedure sp_cursos_report_by_id(in id_ int)
begin
	select
		c.id,
        c.nombre_curso,
        if(c.ciclo is null, "", c.ciclo) as ciclo,
        if(c.cupo_maximo is null, "", c.cupo_maximo) as cupo_maximo,
        if(c.cupo_minimo is null, "", c.cupo_minimo) as cupo_minimo,
        c.carrera_tecnica_id,
        c.horario_id,
        h.horario_inicio,
        h.horario_final,
        c.instructor_id,
        concat(
			i.nombre1, " ",
            i.apellido1
        ) as nombre_instructor,
        c.salon_id
	from
		cursos as c
        inner join horarios as h
        inner join instructores as i
	on 
		c.horario_id = h.id and c.instructor_id = i.id
	where
		id_ = c.id
	order by c.id asc;
end $$
DELIMITER ;
-- call sp_cursos_report_by_id(2);

-- Contar registros
DELIMITER $$
drop procedure if exists sp_cursos_count $$
create procedure sp_cursos_count()
begin
	select
		count(id)
	from
		cursos;
end $$
DELIMITER ;
-- call sp_cursos_count()

/*
CRUD tabla asignaciones_alumnos
*/

-- Create
DELIMITER $$
drop procedure if exists sp_asignaciones_alumnos_create $$
create procedure sp_asignaciones_alumnos_create(
    in _alumno_id varchar(7),
    in _curso_id int,
    in _fecha_asignacion datetime
)
begin
	insert into asignaciones_alumnos(
        alumno_id,
        curso_id,
        fecha_asignacion
	)
	values (
        _alumno_id,
        _curso_id,
        _fecha_asignacion
	);
end $$
DELIMITER ;

-- Read
DELIMITER $$
drop procedure if exists sp_asignaciones_alumnos_read $$
create procedure sp_asignaciones_alumnos_read()
begin
	select 
		al.id,
        al.alumno_id,
        al.curso_id,
        al.fecha_asignacion
	from
		asignaciones_alumnos as al;
end $$
DELIMITER ;

-- Read by id
DELIMITER $$
drop procedure if exists sp_asignaciones_alumnos_read_by_id $$
create procedure sp_asignaciones_alumnos_read_by_id(
	in _id int
)
begin
	select 
		al.id,
        al.alumno_id,
        al.curso_id,
        al.fecha_asignacion
	from
		asignaciones_alumnos as al
	where
		id = _id;
end $$
DELIMITER ;

-- Update
DELIMITER $$
drop procedure if exists sp_asignaciones_alumnos_update $$
create procedure sp_asignaciones_alumnos_update(
	in _id int,
    in _alumno_id varchar(7),
    in _curso_id int,
    in _fecha_asignacion datetime
)
begin
	update
		asignaciones_alumnos
	set
		alumno_id = _alumno_id,
        curso_id = _curso_id,
        fecha_asignacion = _fecha_asignacion
	where
		id = _id;
end $$
DELIMITER ;

-- Delete
DELIMITER $$
drop procedure if exists sp_asignaciones_alumnos_delete $$
create procedure sp_asignaciones_alumnos_delete(
	in _id int
)
begin
	delete from
		asignaciones_alumnos
	where
		id = _id;
end $$
DELIMITER ;

-- Reporte asignaciones alumnos
DELIMITER $$
drop procedure if exists sp_asignaciones_alumnos_report $$
create procedure sp_asignaciones_alumnos_report()
begin
	select 
		al.id,
        al.alumno_id,
        concat(
			a.nombre1, " ",
			if(a.nombre2 is null, "", a.nombre2), " ",
            if(a.nombre3 is null, "", a.nombre3), " ",
            a.apellido1, " ",
            if(a.apellido2 is null, "", a.apellido2)
        ) as nombre_completo,
        c.nombre_curso,
        al.curso_id,
        al.fecha_asignacion
	from
		asignaciones_alumnos as al 
        inner join alumnos as a
        inner join cursos as c
	on
		al.alumno_id = a.carne and al.curso_id = c.id;
end $$
DELIMITER ;
-- call sp_asignaciones_alumnos_report()

-- Reporte asignaciones alumnos por id
DELIMITER $$
drop procedure if exists sp_asignaciones_alumnos_report_by_id $$
create procedure sp_asignaciones_alumnos_report_by_id (in id_ int)
begin
	select 
		al.id,
        al.alumno_id,
        concat(
			a.nombre1, " ",
			if(a.nombre2 is null, "", a.nombre2), " ",
            if(a.nombre3 is null, "", a.nombre3), " ",
            a.apellido1, " ",
            if(a.apellido2 is null, "", a.apellido2)
        ) as nombre_completo,
        c.nombre_curso,
        al.curso_id,
        al.fecha_asignacion
	from
		asignaciones_alumnos as al 
        inner join alumnos as a
        inner join cursos as c
	on
		al.alumno_id = a.carne and al.curso_id = c.id
	where
		al.id = id_;
end $$
DELIMITER ;
-- call sp_asignaciones_alumnos_report_by_id(1)

-- Contar registros
DELIMITER $$
drop procedure if exists sp_asignaciones_alumnos_count $$
create procedure sp_asignaciones_alumnos_count()
begin
	select
		count(id)
	from
		asignaciones_alumnos;
end $$
DELIMITER ;
-- call sp_asignaciones_alumnos_count()

-- Procedimientos para verificación de Login
DELIMITER $$
drop procedure if exists sp_comprobacion_campos $$
create procedure sp_comprobacion_campos()
begin
	select
		u.usuario,
        u.pass
	from
		usuarios as u;
end $$
DELIMITER ;
-- call sp_comprobacion_campos();

-- Procedimientos para verificación el rol
DELIMITER $$
drop procedure if exists sp_comprobacion_roles $$
create procedure sp_comprobacion_roles(in _usuario varchar(25), in _pass varchar(25))
begin
	select
		u.nombre,
        r.id,
        r.descripcion
	from
		usuarios as u
        inner join roles as r
	on
		u.roles_id = r.id
	where
		_usuario = u.usuario and
        _pass = u.pass;
end $$
DELIMITER ;
-- call sp_comprobacion_roles("root", "admin");