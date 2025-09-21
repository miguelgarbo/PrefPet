package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Notificacao;
import com.uni.PrefPet.service.AplicacaoVacinaService;
import com.uni.PrefPet.service.NotificacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@CrossOrigin("*")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private AplicacaoVacinaService aplicacaoVacinaService;

//crud

    @PostMapping
    public ResponseEntity<Notificacao> save(@RequestBody @Valid Notificacao notificacao) {
        var result = notificacaoService.save(notificacao);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> findById(@PathVariable Long id) {
        var result = notificacaoService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificacaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacao> update(@PathVariable Long id, @RequestBody Notificacao notificacao) {
        var updatedNot = notificacaoService.update(id, notificacao);
        return new ResponseEntity<>(updatedNot, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Notificacao>> findAll() {
        var result = notificacaoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findByTutorId")
    public ResponseEntity<List<Notificacao>> findByTutorId(@RequestParam Long id) {
        var result = notificacaoService.findByTutorId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//regras de  negocio

    @GetMapping("/gerar")
    public String gerarNotificacoes() {
        var aplicacoes = aplicacaoVacinaService.findAll();

        for (var aplicacao : aplicacoes) {
            Notificacao notificacao = notificacaoService.gerarNotificacaoDataValidadeVacina(aplicacao);
            if (notificacao != null) {
                notificacaoService.save(notificacao);
            }
        }

        return "Notificações geradas com sucesso!";
    }


    @PostMapping("/gerarConvite")
    public ResponseEntity<Notificacao> gerarConvite(@RequestParam Long tutorDestinatario_id,
                                                    @RequestParam Long tutorRemetente_id,
                                                    @RequestParam Long animal_id) {

        Notificacao convite = notificacaoService.gerarConvite(tutorDestinatario_id, tutorRemetente_id, animal_id);
        return new ResponseEntity<>(convite, HttpStatus.CREATED);
    }

    @PostMapping("/conviteAceito/{notificacaoId}")
    public ResponseEntity<String> conviteAceito(@PathVariable Long notificacaoId) {
        notificacaoService.conviteAceito(notificacaoId);
        return new ResponseEntity<>("Convite aceito e tutor atualizado com sucesso!", HttpStatus.OK);
    }
}
