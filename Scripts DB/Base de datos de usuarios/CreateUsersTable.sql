
DROP TABLE IF EXISTS Users;

CREATE TABLE public.Users(

    idUsuario SERIAL PRIMARY KEY,

    nombre varchar NOT NULL,

    correo varchar NOT NULL,
	
	contrasenna varchar NOT NULL,
	
    trabajaAsada boolean NOT NULL,

    ubicacion varchar NOT NULL,

    ultimaActualizacion TIMESTAMP NOT NULL,
    borrado boolean NOT NULL

);