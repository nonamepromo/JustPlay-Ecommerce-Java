package it.univaq.disim.mwt.justplay.business;

import it.univaq.disim.mwt.justplay.domain.Commento;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface CommentoService{

    Commento findById(Long id) throws BusinessException;

    void addCommento(Videogioco videogioco, Utente utente, String contenuto) throws BusinessException;

    void updateCommento(Commento commento) throws BusinessException;

}