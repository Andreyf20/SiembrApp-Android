CREATE OR REPLACE FUNCTION spVerEspecie(especieInput varchar)
RETURNS TABLE(
            familia varchar,
            fenologia varchar,
            polinizador varchar,
            metodoDispersion varchar,

            nombreComun varchar,
            nombreCientifico varchar,
            origen varchar,
            minRangoAltitudinal real,
            maxRangoAltitudinal real,
			metros real,
            requerimientosDeLuz varchar,
            habito varchar,
            frutos varchar,
            texturaFruto varchar,
            flor varchar,
            usosConocidos varchar,
            paisajeRecomendado varchar
)
AS
$$
    BEGIN

        IF (especieInput NOT LIKE '*') THEN
            RETURN QUERY
                SELECT

                F.nombre,
                FL.nombre,
                AP.nombre,
                MD.nombre,

                P.nombreComun,
                P.nombreCientifico,
                P.origen,
                P.minRangoAltitudinal,
                P.maxRangoAltitudinal,
                P.metros,
                P.requerimientosDeLuz,
                P.habito,
                P.frutos,
                P.texturaFruto,
                P.flor,
                P.usosConocidos,
                P.paisajeRecomendado

                FROM plantas P
                LEFT JOIN familia F ON P.idFamilia = F.idFamilia
                LEFT JOIN fenologia FL ON P.idFenologia  = FL.idFenologia
                LEFT JOIN agentePolinizador AP ON P.idAgentePolinizador = AP.idAgentePolinizador
                LEFT JOIN metodoDispersion MD ON P.idMetodoDispersion = MD.idMetodoDispersion
                WHERE LOWER(P.nombreComun) LIKE lower(especieInput) and P.borrado = False;
        ELSE
            RETURN QUERY
                SELECT

                F.nombre,
                FL.nombre,
                AP.nombre,
                MD.nombre,

                P.nombreComun,
                P.nombreCientifico,
                P.origen,
                P.minRangoAltitudinal,
                P.maxRangoAltitudinal,
                P.metros,
                P.requerimientosDeLuz,
                P.habito,
                P.frutos,
                P.texturaFruto,
                P.flor,
                P.usosConocidos,
                P.paisajeRecomendado

                FROM plantas P
                LEFT JOIN familia F ON P.idFamilia = F.idFamilia
                LEFT JOIN fenologia FL ON P.idFenologia  = FL.idFenologia
                LEFT JOIN agentePolinizador AP ON P.idAgentePolinizador = AP.idAgentePolinizador
                LEFT JOIN metodoDispersion MD ON P.idMetodoDispersion = MD.idMetodoDispersion
                WHERE P.borrado = False;
        END IF;
        
    END;
$$
LANGUAGE PLPGSQL;

/*
select * from spVerEspecie('*');
select * from spVerEspecie('Saragundí');
*/