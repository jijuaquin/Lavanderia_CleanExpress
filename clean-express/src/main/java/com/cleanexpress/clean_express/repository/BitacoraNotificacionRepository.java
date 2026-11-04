package com.cleanexpress.clean_express.repository;

import com.cleanexpress.clean_express.model.BitacoraNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraNotificacionRepository extends JpaRepository<BitacoraNotificacion, Long> {
}