-- Tabla CLIENTE
CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(100),
    direccion TEXT
);

-- Tabla PEDIDO
CREATE TABLE pedido (
    id_pedido SERIAL PRIMARY KEY,
    id_cliente INT NOT NULL,
    fecha_recepcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_entrega_estimada DATE,
    estado VARCHAR(30) NOT NULL, -- Recibido, En Proceso, Listo, Entregado
    total NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

-- Tabla PRENDA
CREATE TABLE prenda (
    id_prenda SERIAL PRIMARY KEY,
    id_pedido INT NOT NULL,
    tipo_prenda VARCHAR(50) NOT NULL, -- Pantalón, Camisa, Vestido, etc.
    color VARCHAR(30),
    descripcion_detalles TEXT,
    precio_unitario NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE
);

-- Tabla PAGO
CREATE TABLE pago (
    id_pago SERIAL PRIMARY KEY,
    id_pedido INT NOT NULL,
    monto_pagado NUMERIC(10,2) NOT NULL,
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    metodo_pago VARCHAR(30) NOT NULL, -- Efectivo, Tarjeta, Transferencia
    CONSTRAINT fk_pedido_pago FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
);

-- Tabla BITACORA_NOTIFICACION
CREATE TABLE bitacora_notificacion (
    id_notificacion SERIAL PRIMARY KEY,
    id_pedido INT NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_envio VARCHAR(20) NOT NULL, -- Envíado, Fallido
    CONSTRAINT fk_pedido_notif FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
);
