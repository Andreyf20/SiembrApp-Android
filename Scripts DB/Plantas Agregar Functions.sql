-- Agregar nuevo tipo de familia
CREATE OR REPLACE FUNCTION spAgregarFamilia(

    nombreInput varchar

)
RETURNS BOOLEAN
AS $$

    DECLARE
        idFamilia BIGINT := (SELECT F.idFamilia FROM familia F WHERE F.nombre LIKE nombreInput);
    BEGIN

        IF idFamilia IS NOT NULL THEN

            IF (SELECT F.borrado FROM familia F WHERE idFamilia = F.idFamilia) = False THEN
                RETURN False;
            ELSE
                
                UPDATE familia SET

                    familia.borrado = False,
                    familia.ultimaActualizacion = NOW();
            END IF;
			RETURN True;

        ELSE
            
            INSERT INTO familia(nombre,ultimaActualizacion,borrado)
            VALUES(nombreInput,NOW(),False);

        END IF;
		RETURN True;
    END;

$$ LANGUAGE PLPGSQL;

/*
select spAgregarFamilia('Fabaceae') as success;
SELECT * FROM familia;
*/



CREATE OR REPLACE FUNCTION spAgregarFenologia(

    nombreInput varchar

)
RETURNS BOOLEAN
AS $$

    DECLARE
        idFenologia BIGINT := (SELECT F.idFenologia FROM fenologia F WHERE F.nombre LIKE nombreInput);
    BEGIN

        IF idFenologia IS NOT NULL THEN

            IF (SELECT F.borrado FROM fenologia F WHERE idFenologia = F.idFamilia) = False THEN
                RETURN False;
            ELSE
                
                UPDATE fenologia SET

                    fenologia.borrado = False,
                    fenologia.ultimaActualizacion = NOW();
            END IF;
			RETURN True;

        ELSE
            
            INSERT INTO fenologia(nombre,ultimaActualizacion,borrado)
            VALUES(nombreInput,NOW(),False);

        END IF;
		RETURN True;
    END;

$$ LANGUAGE PLPGSQL;


/*
select spAgregarFenologia('Caducifolio') as success;
select * from fenologia;
*/

CREATE OR REPLACE FUNCTION spAgregarAgentePolinizador(

    nombreInput varchar

)
RETURNS BOOLEAN
AS $$

    DECLARE
        idAgentePolinizador BIGINT := (SELECT A.idAgentePolinizador FROM agentePolinizador A WHERE A.nombre LIKE nombreInput);
    BEGIN

        IF idAgentePolinizador IS NOT NULL THEN

            IF (SELECT A.borrado FROM agentePolinizador A WHERE idAgentePolinizador = A.idAgentePolinizador) = False THEN
                RETURN False;
            ELSE
                
                UPDATE agentePolinizador SET

                    agentePolinizador.borrado = False,
                    agentePolinizador.ultimaActualizacion = NOW();
            END IF;
			RETURN True;

        ELSE
            
            INSERT INTO agentePolinizador(nombre,ultimaActualizacion,borrado)
            VALUES(nombreInput,NOW(),False);

        END IF;
		RETURN True;
    END;

$$ LANGUAGE PLPGSQL;

/*
SELECT spAgregarAgentePolinizador('Insectos') as success;
select * from agentePolinizador;
*/

CREATE OR REPLACE FUNCTION spAgregarMetodoDispersion(

    nombreInput varchar
    -- descripcion (?)
)
RETURNS BOOLEAN
AS $$

    DECLARE
        idMetodoDispersion BIGINT := (SELECT M.idMetodoDispersion FROM metodoDispersion M WHERE M.nombre LIKE nombreInput);
    BEGIN

        IF idMetodoDispersion IS NOT NULL THEN

            IF (SELECT M.borrado FROM metodoDispersion M WHERE idMetodoDispersion = M.idMetodoDispersion) = False THEN
                RETURN False;
            ELSE
                
                UPDATE metodoDispersion SET

                    metodoDispersion.borrado = False,
                    metodoDispersion.ultimaActualizacion = NOW();
            END IF;
			RETURN True;

        ELSE
            
            INSERT INTO metodoDispersion(nombre,ultimaActualizacion,borrado)
            VALUES(nombreInput,NOW(),False);

        END IF;
		RETURN True;
    END;

$$ LANGUAGE PLPGSQL;

/*
SELECT spAgregarMetodoDispersion('Autocoria') as success;
select * from metodoDispersion;
*/


CREATE OR REPLACE FUNCTION spAgregarPlanta(

    nombreComunInput varchar,
    nombreCientificoInput varchar,
    origenInput varchar,
    minRangoAltitudinalInput real,
    maxRangoAltitudinalInput real,
    metrosInput real,
    requerimientosDeLuzInput varchar,
    habitoInput varchar,
    
    familiaInput varchar,
    fenologiaInput varchar,
    agentePolinizadorInput varchar,
    metodoDispersionInput varchar,

    frutosInput varchar,
    texturaFrutoInput varchar,
    florInput varchar,

    usosConocidosInput varchar,
    paisajeRecomendadoInput varchar

)
RETURNS BOOLEAN
AS $$

    DECLARE
        idFamilia BIGINT := (SELECT F.idFamilia FROM familia F WHERE LOWER(F.nombre) LIKE LOWER(familiaInput));
        idFenologia BIGINT := (SELECT F.idFenologia FROM fenologia F WHERE LOWER(F.nombre) LIKE LOWER(fenologiaInput));
        idAgentePolinizador BIGINT := (SELECT A.idAgentePolinizador FROM agentePolinizador A WHERE LOWER(A.nombre) LIKE LOWER(agentePolinizadorInput));
        idMetodoDispersion BIGINT := (SELECT M.idMetodoDispersion FROM metodoDispersion M WHERE LOWER(M.nombre) LIKE LOWER(metodoDispersionInput));
    BEGIN

        -- TO DO Verificacion en caso de plantas.borrado = True

        INSERT INTO plantas(
            idFamilia,
            idFenologia,
            idAgentePolinizador,
            idMetodoDispersion,

            nombreComun,
            nombreCientifico,
            origen,
            minRangoAltitudinal,
            maxRangoAltitudinal,
			metros,
            requerimientosDeLuz,
            habito,
            frutos,
            texturaFruto,
            flor,
            usosConocidos,
            paisajeRecomendado,

            -- Mantenimineto
            ultimaActualizacion,
            borrado
        )
        VALUES(
            idFamilia,
            idFenologia,
            idAgentePolinizador,
            idMetodoDispersion,

            nombreComunInput,
            nombreCientificoInput,
            origenInput,
            minRangoAltitudinalInput,
            maxRangoAltitudinalInput,
            metrosInput,
            requerimientosDeLuzInput,
            habitoInput,
            frutosInput,
            texturaFrutoInput,
            florInput,
            usosConocidosInput,
            paisajeRecomendadoInput,

            NOW(),
            False
        );
        RETURN TRUE;

    END;
$$ LANGUAGE PLPGSQL;

/* 
{
    select spAgregarPlanta(
        'Saragundí',
        'Senna Reticulata',
        'Nativa',
        0,
        500,
        1800,
        'Requiere de luz',
        'ARB de 5 a 10m de altura y de >= 12.5cm de diámetro',
        'Fabaceae',
        'Caducifolio',
        'Insectos',
        'Autocoria',
        'Legumbre',
        'Seco',
        'Amarilla',
        'Ornamental, Medicinal (Follaje)',
        'CHRTG, CBIMA, PILA, TNN'

    ) as success;

    select * from plantas;
}
*/

-- Agregar filas catalogo
SELECT spAgregarFamilia('Fabaceae') as success;
SELECT spAgregarFenologia('Caducifolio') as success;
SELECT spAgregarAgentePolinizador('Insectos') as success;
SELECT spAgregarMetodoDispersion('Autocoria') as success;