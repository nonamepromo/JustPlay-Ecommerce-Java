package it.univaq.disim.mwt.justplay.business;

import it.univaq.disim.mwt.justplay.domain.Commento;

public interface CommentoService{

    void addCommento(Commento commento) throws BusinessException;
    
    void deleteCommento(Long idCommento) throws BusinessException;

}