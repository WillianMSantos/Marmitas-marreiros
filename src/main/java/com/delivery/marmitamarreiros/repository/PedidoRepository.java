package com.delivery.marmitamarreiros.repository;

import com.delivery.marmitamarreiros.model.Cliente;
import com.delivery.marmitamarreiros.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByClient(Cliente cliente);

    @Query("select p from Pedido p left join fetch p.itens where p.id = :id")
    Optional<Pedido> findbyIdFetchItens(@Param("id") Integer id);
}
