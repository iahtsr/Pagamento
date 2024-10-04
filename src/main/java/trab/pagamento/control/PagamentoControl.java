package trab.pagamento.control;

import trab.pagamento.repository.PagamentoRepository;
import trab.pagamento.model.Pagamento;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localost:8080")
// Define este controlador para lidar com as solicitações que seguem o padrão /api
@RestController
@RequestMapping("/api")
public class PagamentoController {
    
    // Injeta automaticamente o repositório do Jogador
    @Autowired
    JogadorRepository repJ;

    // Injeta automaticamente o repositório do Pagamento
    @Autowired
    PagamentoRepository repP;

    // Endpoint GET para listar todos os pagamentos
    // Os parâmetros opcionais 'ano' e 'valor' podem ser usados para filtrar os resultados
    @GetMapping("/pagamentos")
    public ResponseEntity<List<Pagamento>> getAllPagamentos(@RequestParam(required=false, defaultValue = "0") short ano, 
                @RequestParam(required=false, defaultValue = "0") double valor) { 
        try {
            List<Pagamento> lp = new ArrayList<Pagamento>();
            
            // Se nenhum filtro for aplicado, retorna todos os pagamentos
            if (ano == 0 && valor == 0) {
                repP.findAll().forEach(lp::add);
            }
            // Se o ano for fornecido, filtra por ano
            else if (ano != 0) {
                repP.findByAnoIs(ano).forEach(lp::add);
            }
            // Se o valor for fornecido, filtra por pagamentos com valor maior ou igual ao fornecido
            else if (valor != 0) {
                repP.findByValorGreaterThanEqual(valor).forEach(lp::add);
            }

            // Se a lista estiver vazia, retorna o status NO_CONTENT
            if (lp.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            // Caso contrário, retorna a lista de pagamentos e o status OK
            return new ResponseEntity<>(lp, HttpStatus.OK);
        } catch (Exception e) {
            // Em caso de erro, retorna INTERNAL_SERVER_ERROR
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint POST para criar um novo pagamento vinculado a um jogador específico
    @PostMapping("/pagamentos/add/{cod_jogador}")
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento pag, @PathVariable("cod_jogador") int cod_jogador) {
        try {
            // Busca o jogador pelo código fornecido
            Optional<Jogador> jogador = repJ.findById(cod_jogador);

            // Se o jogador for encontrado, cria o pagamento e o salva no banco de dados
            if (jogador.isPresent()) {
                Pagamento _p = repP.save(new Pagamento(pag.getAno(), pag.getMes(), pag.getValor(), jogador.get()));
                return new ResponseEntity<>(_p, HttpStatus.CREATED);
            } else {
                // Se o jogador não for encontrado, retorna NOT_FOUND
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Em caso de erro, retorna INTERNAL_SERVER_ERROR
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Endpoint GET para buscar um pagamento por ID
    @GetMapping("/pagamentos/{cod_pagamento}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable("cod_pagamento") int cod_pagamento) {
        Optional<Pagamento> data = repP.findById(cod_pagamento);

        // Se o pagamento for encontrado, retorna o pagamento e o status OK
        if (data.isPresent()) {
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        } else {
            // Se não for encontrado, retorna NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint PUT para atualizar um pagamento existente pelo ID
    @PutMapping("/pagamentos/{cod_pagamento}")
    public ResponseEntity<Pagamento> updatePagamento(@PathVariable("cod_pagamento") int cod_pagamento, @RequestBody Pagamento p) {
        Optional<Pagamento> data = repP.findById(cod_pagamento);
    
        // Se o pagamento for encontrado, atualiza os atributos e salva
        if (data.isPresent()) {
            Pagamento pag = data.get();
            pag.setAno(p.getAno());
            pag.setMes(p.getMes());
            pag.setValor(p.getValor());
    
            return new ResponseEntity<>(repP.save(pag), HttpStatus.OK);
        } else {
            // Se o pagamento não for encontrado, retorna NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint DELETE para excluir um pagamento pelo ID
    @DeleteMapping("/pagamentos/{cod_pagamento}")
    public ResponseEntity<HttpStatus> deletePagamento(@PathVariable("cod_pagamento") int cod_pagamento) {
        try {
            // Exclui o pagamento pelo ID
            repP.deleteById(cod_pagamento);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Em caso de erro, retorna INTERNAL_SERVER_ERROR
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint DELETE para excluir todos os pagamentos
    @DeleteMapping("/pagamentos")
    public ResponseEntity<HttpStatus> deleteAllPagamentos() {
        try {
            // Exclui todos os pagamentos
            repP.deleteAll();
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Em caso de erro, retorna INTERNAL_SERVER_ERROR
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
