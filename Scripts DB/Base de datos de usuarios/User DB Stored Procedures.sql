-- Archivo para stored procedures y funciones de la base de datos de usuarios

/*
Password encription 
Ref: https://dbtut.com/index.php/2018/10/01/column-level-encryption-with-pgcrypto-on-postgresql/
    #Encrypt: "PGP_SYM_ENCRYPT('The value to be entered in the column:','AES_KEY');
    #Decrypt: "PGP_SYM_DECRYPT(column_name::bytea,'AES_KEY');
*/

DROP EXTENSION IF EXISTS pgcrypto;
CREATE EXTENSION pgcrypto;

CREATE OR REPLACE FUNCTION spCrearUsuario(

    nombreInput varchar,
    correoInput varchar,
    contrasennaInput varchar,
    trabajaAsadaInput boolean,
    ubicacionInput varchar
)
RETURNS BOOLEAN
AS $$

    DECLARE
        idUsuarioBuscado BIGINT := (SELECT U.idUsuario FROM Users U WHERE U.correo LIKE correoInput);
    BEGIN

        IF idUsuarioBuscado IS NOT NULL THEN
            
            IF ((SELECT U.borrado FROM Users U WHERE U.idUsuario = idUsuarioBuscado) = False) THEN
				RETURN FALSE;
            ELSE
                	
						UPDATE Users SET
							borrado = False,
							ultimaActualizacion = NOW()
							WHERE Users.idUsuario = idUsuarioBuscado;
			END IF;
            RETURN TRUE;
        ELSE

                INSERT INTO Users(
                    nombre,
                    correo,
                    contrasenna,
                    trabajaAsada,
                    ubicacion,
                    ultimaActualizacion,
                    borrado
                )
                VALUES(
                    nombreInput,
                    correoInput,
                    Crypt(contrasennaInput,'md5'),
                    trabajaAsadaInput,
                    ubicacionInput,
                    NOW(),
                    FALSE
                );

			RETURN TRUE;
        END IF;
    END;
$$ LANGUAGE PLPGSQL;

/*
SELECT spCrearUsuario('Administrador','admin@siembrapp.com','admin',True,'CR') as success;
select * from Users;
*/

-- Login
CREATE OR REPLACE FUNCTION spLogin(correoInput VARCHAR,passwordInput VARCHAR)
RETURNS BOOLEAN AS $$

	DECLARE
		correoEncontrado VARCHAR := (SELECT U.correo FROM users U WHERE U.correo = correoInput and U.borrado = False);
        encryptedPass VARCHAR := (SELECT U.contrasenna FROM users U where U.correo = correoEncontrado);
        pass VARCHAR := Crypt(passwordInput,'md5');
	BEGIN
		
		IF correoEncontrado IS NOT NULL THEN
			IF (pass LIKE encryptedPass) THEN
				RETURN TRUE;
			ELSE
				RETURN FALSE;
			END IF;
		ELSE
			BEGIN
				RETURN FALSE;
			END;
		END IF;
	END;
$$ LANGUAGE PLPGSQL;

/*
select spLogin('admin@siembrapp.com','admin') as success;
*/