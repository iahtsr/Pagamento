package trab.pagamento.control;

import trab.pagamento.repository.JogadorRepository;
import trab.pagamento.model.Jogador;

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
// Define este controlador para lidar com as requisições que seguem o padrão /api
@RestController
@RequestMapping("/api")
public class JogadorController {

    // Injeta automaticamente o repositório do Jogador no controlador
    @Autowired
    JogadorRepository rep;

    // Endpoint GET para listar todos os jogadores
    // Parâmetros opcionais 'nome' e 'email' podem ser usados para filtrar os resultados
    @GetMapping("/jogadores")
    public ResponseEntity<List<Jogador>> getAllJogadores(@RequestParam(required=false) String nome, 
                @RequestParam(required=false) String email) {
        try {
            List<Jogador> lj = new ArrayList<Jogador>();
            
            // Se nenhum filtro for aplicado, retorna todos os jogadores
            if (nome == null && email == null) {
                rep.findAll().forEach(lj::add);
            }
            // Se o nome for fornecido, filtra por nome
            else if (nome != null) {
                rep.findByNomeContaining(nome).forEach(lj::add);
            }
            // Se o email for fornecido, filtra por email
            else if (email != null) {
                rep.findByEmailContaining(email).forEach(lj::add);
            }

            // Se a busca não retornar resultados, retorna NO_CONTENT
            if (lj.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } 
            // Caso contrário, retorna a lista de jogadores e o status OK
            return new ResponseEntity<>(lj, HttpStatus.OK);
        } catch (Exception e) {
            // Em caso de erro, retorna INTERNAL_SERVER_ERROR
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint POST para criar um novo jogador
    @PostMapping("/jogadores")
    public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jog) {
        try {
            // Cria um objeto Jogador e o salva no banco de dados
            Jogador _j = rep.save(new Jogador(jog.getNome(), jog.getEmail(), jog.getDatanasc()));

            // Retorna o jogador criado e o status CREATED
            return new ResponseEntity<>(_j, HttpStatus.CREATED);
        } catch (Exception e) {
            // Em caso de erro, retorna INTERNAL_SERVER_ERROR
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Endpoint GET para buscar um jogador por ID
    @GetMapping("/jogadores/{cod_jogador}")
    public ResponseEntity<Jogador> getJogadorById(@PathVariable("cod_jogador") int cod_jogador) {
        // Busca o jogador pelo ID
        Optional<Jogador> data = rep.findById(cod_jogador);

        // Se o jogador for encontrado, retorna o jogador e o status OK
        if (data.isPresent()) {
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        } 
        // Se não for encontrado, retorna NOT_FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint PUT para atualizar um jogador existente pelo ID
    @PutMapping("/jogadores/{cod_jogador}")
    public ResponseEntity<Jogador> updateJogador(@PathVariable("cod_jogador") int cod_jogador, @RequestBody Jogador j) {
        // Busca o jogador pelo ID
        Optional<Jogador> data = rep.findById(cod_jogador);
    
        // Se o jogador for encontrado, atualiza os atributos e salva as mudanças
        if (data.isPresent()) {
            Jogador jog = data.get();
            jog.setNome(j.getNome());
            jog.setEmail(j.getEmail());
            jog.setDatanasc(j.getDatanasc());

            return new ResponseEntity<>(rep.save(jog), HttpStatus.OK);
        } 
        // Se não for encontrado, retorna NOT_FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint DELETE para excluir um jogador pelo ID
    @DeleteMapping("/jogadores/{cod_jogador}")
    public ResponseEntity<HttpStatus> deleteJogador(@PathVariable("cod_jogador") int cod_jogador) {
        try {
            // Exclui o jogador pelo ID
            rep.deleteById(cod_jogador);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Em caso de erro, retorna INTERNAL_SERVER_ERROR
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint DELETE para excluir todos os jogadores
    @DeleteMapping("/jogadores")
    public ResponseEntity<HttpStatus> deleteAllJogadores() {
        try {
            // Exclui todos os jogadores
            rep.deleteAll();
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Em caso de erro, retorna INTERNAL_SERVER_ERROR
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
