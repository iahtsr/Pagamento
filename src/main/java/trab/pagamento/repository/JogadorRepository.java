package trab.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trab.pagamento.model.Jogador;
import java.util.List;

public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    // Busca todos os jogadores cujo nome contém a string passada como parâmetro
    List<Jogador> findByNomeContaining(String nome);

    // Busca todos os jogadores cujo email contém a string passada como parâmetro
    List<Jogador> findByEmailContaining(String email);
}