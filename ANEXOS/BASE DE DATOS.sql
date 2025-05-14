/*****************************************************************************************************************
 NOMBRE         : Create Schema               
 CREADO POR     : DTACO                
*****************************************************************************************************************/
DO $$
DECLARE SchemaName VARCHAR := 'datos';
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_namespace WHERE nspname = SchemaName) THEN
        EXECUTE FORMAT('CREATE SCHEMA "%s"', SchemaName);
    END IF;
END $$;

/*****************************************************************************************************************
 NAME           : Create Table
 CREATED BY     : DTACO                
*****************************************************************************************************************/

CREATE TABLE "datos".Persona (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INT,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion TEXT,
    telefono VARCHAR(20)
);

--CREATE INDEX idx_identificacion ON "datos".Persona(identificacion);

/*****************************************************************************************************************
 NAME           : Create Table
 CREATED BY     : DTACO                
*****************************************************************************************************************/

CREATE TABLE "datos".Cliente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    idPersona UUID UNIQUE NOT NULL,
    clienteid VARCHAR(50) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT FK_datos_Persona_id_Cliente_idPersona FOREIGN KEY (idPersona) REFERENCES "datos".Persona(id)
);

--CREATE INDEX idx_clienteid ON "datos".Cliente(clienteid);
--CREATE INDEX idx_cliente_estado ON "datos".Cliente(estado);

/*****************************************************************************************************************
 NAME           : Create Table
 CREATED BY     : DTACO                
*****************************************************************************************************************/

CREATE TABLE "datos".Cuenta (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    idPersona UUID NOT NULL,
    numeroCuenta VARCHAR(50) UNIQUE NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    saldo DECIMAL(10,2) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT FK_Datos_Cliente_id_Cuenta_idPersona FOREIGN KEY (idPersona) REFERENCES "datos".Persona(id)
);

--CREATE INDEX idx_numero_cuenta ON "datos".Cuenta(numeroCuenta);

/*****************************************************************************************************************
 NAME           : Create Table
 CREATED BY     : DTACO                
*****************************************************************************************************************/

CREATE TABLE "datos".Movimiento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    idCuenta UUID NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    saldo DECIMAL(10,2) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_Datos_Cuenta_Movimiento_idCuenta FOREIGN KEY (idCuenta) REFERENCES "datos".Cuenta(id)
);

--CREATE INDEX idx_fecha_movimiento ON "datos".Movimiento(fecha);


/*****************************************************************************************************************
 NAME           : Insert
 CREATED BY     : DTACO                
*****************************************************************************************************************/

INSERT INTO "datos".persona (id, nombre, genero, edad, identificacion, direccion, telefono) VALUES
('d6b4a7e5-3e99-4c6c-97a7-123456789abc', 'Juan Pérez', 'M', 30, '1234567890', 'Av. Principal 123', '0987654321'),
('b5c1f8e4-6c88-40d8-b3a8-987654321def', 'María López', 'F', 25, '0987654321', 'Calle Secundaria 456', '0912345678');


INSERT INTO "datos".cuenta (id, numerocuenta, tipo, saldo, estado, idpersona) VALUES
('a3b4c5d6-e7f8-9012-3456-789abcdef012', '000123456789', 'AHORRO', 5000.00, true, 'd6b4a7e5-3e99-4c6c-97a7-123456789abc');
INSERT INTO "datos".cuenta ( numerocuenta, tipo, saldo, estado, idpersona) VALUES
('000987654321', 'CORRIENTE', 3000.00, true, 'b5c1f8e4-6c88-40d8-b3a8-987654321def');


INSERT INTO "datos".movimiento (id, tipo, valor, saldo, fecha, idcuenta) VALUES
('abcdef12-3456-7890-abcd-ef1234567890', 'DEPOSITO', 1000.00, 6000.00, '2025-05-11 08:30:00', 'a3b4c5d6-e7f8-9012-3456-789abcdef012'),
('12345678-90ab-cdef-1234-567890abcdef', 'RETIRO', 500.00, 2500.00, '2025-05-11 09:15:00', 'a3b4c5d6-e7f8-9012-3456-789abcdef012');
