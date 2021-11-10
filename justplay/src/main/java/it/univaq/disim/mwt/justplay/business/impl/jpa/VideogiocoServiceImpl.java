package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoRepository;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

@Service
@Transactional
public class VideogiocoServiceImpl implements VideogiocoService {

	@Autowired
	private VideogiocoRepository videogiocoRepository;
    
    @Override
    public Videogioco findById(Long id) throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Videogioco> findAll(Pageable pageable) throws BusinessException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Videogioco> listaVideogiochi = new ArrayList<Videogioco>();
        long videogiochiSize = videogiocoRepository.count();

        // if (videogiochiSize < startItem) {
        //     listaVideogiochi = Collections.emptyList();
        // } else {
        //     int toIndex = Math.min(startItem + pageSize, listaVideogiochi.size());
        //     listaVideogiochi = books.subList(startItem, toIndex);
        // }

        Page<Videogioco> videogiochiPage = new PageImpl<Videogioco>(listaVideogiochi, PageRequest.of(currentPage, pageSize), videogiochiSize);

        return videogiochiPage;
    }

    @Override
    public Page<Videogioco> searchVideogioco(String search, int numeroPagine, int sizePagina) throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VideogiocoPiaciuto findLikedGame(Utente utente, Videogioco videogioco) throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VideogiocoInVendita> findAllVendita(Videogioco videogioco) throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Videogioco videogioco, Utente utente)
            throws BusinessException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addGameToLikedlist(Videogioco videogioco, boolean piaciuto) throws BusinessException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeGameFromSellinglist(Videogioco videogioco, Utente utente, VideogiocoInVendita videogiocoInVendita)
            throws BusinessException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeGameFromLikedlist(Utente utente, Videogioco videogioco) throws BusinessException {
        // TODO Auto-generated method stub
        
    }

}
