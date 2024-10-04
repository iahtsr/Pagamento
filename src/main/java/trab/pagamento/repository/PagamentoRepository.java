package trab.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trab.pagamento.model.Pagamento;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    // Busca todos os pagamentos cujo valor seja maior ou igual ao valor passado
    List<Pagamento> findByValorGreaterThanEqual(double valor);

    // Busca todos os pagamentos realizados no ano passado como parâmetro
    List<Pagamento> findByAnoIs(short ano);

    // Busca todos os pagamentos que pertencem ao jogador cujo código corresponde ao passado por parâmetro
    List<Pagamento> findByCod_JogadorContaining(int cod_jogador);
}
