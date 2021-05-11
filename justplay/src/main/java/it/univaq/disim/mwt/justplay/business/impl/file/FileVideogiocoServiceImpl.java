package it.univaq.disim.mwt.justplay.business.impl.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.RequestGrid;
import it.univaq.disim.mwt.justplay.business.ResponseGrid;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

public class FileVideogiocoServiceImpl implements VideogiocoService {
	
	@Override
	public List<Videogioco> findAllVideogiochi() throws BusinessException {
		List<Videogioco> result = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File("videogiochi.txt")))) {
			String linea = null;
			while ((linea = reader.readLine()) != null) {
				String[] elements = linea.split(",");
				Videogioco videogioco = new Videogioco();
				videogioco.setId(Long.parseLong(elements[0]));
				videogioco.setTitolo(elements[1]);
				result.add(videogioco);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("errore nell'accesso al file nel metodo findAllVideogiochi", e);
		}
		return result;
	}

	@Override
	public List<Videogioco> findAllVideogiochiPaginated(RequestGrid requestGrid) throws BusinessException {
		return null;
	}

	@Override
	public Videogioco findVideogiocoByID(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVideogioco(Videogioco videogioco) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addVideogiocoInVendita(Videogioco videogioco, Long idUtente) throws BusinessException {
		
	}

	@Override
	public void addVideogiocoDesiderato(Videogioco videogioco, Long idUtente) throws BusinessException {
		
	}
}
