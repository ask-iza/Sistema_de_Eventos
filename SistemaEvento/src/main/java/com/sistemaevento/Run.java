import com.sistemaevento.service.EventoService;
import com.sistemaevento.service.PalestranteService;
import com.sistemaevento.service.ParticipanteService;
import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.tabelas.Palestrante;

public class Run {
    public static void main(String[] args) {
        PalestranteService palestranteService = new PalestranteService();
        EventoService eventoService = new EventoService();
        ParticipanteService participanteService = new ParticipanteService();

        // Cadastrando palestrante
        Palestrante p = new Palestrante();
        p.setNome("Dra. Ana");
        p.setCurriculo("PhD em IA");
        p.setArea_atuacao("Tecnologia");
        palestranteService.cadastrarPalestrante(p);

        // Criando evento
        Evento evento = new Evento();
        evento.setNome("TechConf");
        evento.setDescricao("Conferência sobre tecnologia");
        evento.setData("2025-06-10");
        evento.setLocal("Auditório Central");
        evento.setCapacidade(300);

        // Cadastrando e inscrevendo participante
        /*Participante part = new Participante();
        part.setNome("Carlos Silva");
        part.setEmail("carlos@email.com");
        participanteService.cadastrarParticipante(part);
        participanteService.inscrever(1, 12);*/
    }
}
