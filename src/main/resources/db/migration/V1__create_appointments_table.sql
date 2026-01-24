-- ===============================
-- Tabla de Citas (Appointments)
-- ===============================
CREATE TABLE appointments (
    -- Identificadores (UUID como BINARY(16))
                              id BINARY(16) NOT NULL,
                              business_id BINARY(16) NOT NULL,
                              client_id BINARY(16) NOT NULL,
                              professional_id BINARY(16) NOT NULL,
                              service_id BINARY(16) NOT NULL,

    -- Información de la cita
                              start_time DATETIME(6) NOT NULL,
                              duration_minutes INT NOT NULL,
                              status VARCHAR(20) NOT NULL,

    -- Auditoría
                              created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                              updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    -- Constraints
                              PRIMARY KEY (id),

                              CONSTRAINT chk_duration CHECK (duration_minutes > 0 AND duration_minutes <= 480),
                              CONSTRAINT chk_status CHECK (status IN ('AGENDADO', 'CONFIRMADO', 'CANCELADO', 'COMPLETADO'))

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===============================
-- Índices para optimizar consultas
-- ===============================
CREATE INDEX idx_professional_start ON appointments(professional_id, start_time);
CREATE INDEX idx_client ON appointments(client_id);
CREATE INDEX idx_business ON appointments(business_id);
CREATE INDEX idx_status ON appointments(status);
CREATE INDEX idx_start_time ON appointments(start_time);

-- Índice compuesto para búsquedas de disponibilidad
CREATE INDEX idx_professional_status_time ON appointments(professional_id, status, start_time);
