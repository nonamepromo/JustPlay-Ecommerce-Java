package it.univaq.disim.mwt.justplay.business.impl.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;
import it.univaq.disim.mwt.justplay.presentation.Utility;

@Service
@Transactional
@Slf4j
public class JDBCConversazioneServiceImpl implements ConversazioneService {
    private static final String FIND_CONVERSAZIONI_BY_FK_UTENTE = "SELECT * FROM conversazioni WHERE (fk_utente1 = ? OR fk_utente2 = ?) order by data";
	private static final String UPDATE_CONVERSAZIONE = "UPDATE conversazioni data=? WHERE (fk_utente1 = ? AND fk_utente2 = ?)  OR (fk_utente1 = ? AND fk_utente2 = ?)";
	private static final String CREATE_MESSAGGIO = "INSERT INTO messaggi (fk_mittente, fk_conversazione, contenuto) VALUES (?,?,?)";
	private static final String FIND_MESSAGGI_BY_FK_CONVERSAZIONE = "SELECT c.* FROM messaggi c WHERE c.fk_conversazione = ?";
	private static final String FIND_NAME_UTENTE_BY_ID_CONVERSAZIONE = "SELECT nome_utente1, nome_utente2 FROM conversazioni WHERE id = ?";
	
    @Autowired
	private DataSource dataSource;

	@Override
	public List<Conversazione> findAllByFkUtente(Long fk_utente) throws BusinessException {
		List<Conversazione> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(FIND_CONVERSAZIONI_BY_FK_UTENTE);) {
			st.setLong(1, fk_utente);
			st.setLong(2, fk_utente);
			try (ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				Conversazione conversazione = new Conversazione();
				conversazione.setId(rs.getLong("id"));
				conversazione.setFk_utente1(rs.getLong("fk_utente1"));
				conversazione.setFk_utente2(rs.getLong("fk_utente2"));
				conversazione.setNome_utente1(rs.getString("nome_utente1"));
				conversazione.setNome_utente2(rs.getString("nome_utente2"));
				conversazione.setData(rs.getDate("data"));
				result.add(conversazione);
			}
		  }
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

    @Override
	public List<Messaggio> findMessaggiByFkConversazione(Long idConversazione) throws BusinessException {    	
		List<Messaggio> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(FIND_MESSAGGI_BY_FK_CONVERSAZIONE);) {
			st.setLong(1, idConversazione);
			try (ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				Messaggio messaggio = new Messaggio();
				messaggio.setFk_mittente(rs.getLong("fk_mittente"));
				messaggio.setFk_conversazione(rs.getLong("fk_conversazione"));
				messaggio.setContenuto(rs.getString("contenuto"));
				result.add(messaggio);
			}
		  }
		} catch (SQLException e) {
			log.error("findMessaggiByFkUtenti", e);
			throw new BusinessException("findMessaggiByFkUtenti", e);
		}
		return result;
	}

    @Override
	public void createMessaggio(Long fk_mittente, Long fk_conversazione, String contenuto) throws BusinessException {
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(CREATE_MESSAGGIO);) {
			st.setLong(1, fk_mittente);
			st.setLong(2, fk_conversazione);
			st.setString(3, contenuto);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("createMessaggio", e);
			throw new BusinessException("createMessaggio", e);
		}
	}

	@Override
	public String findNameByIdConversazione(Long idConversazione, Long idUtente) throws BusinessException {    	
		String nomeUtente = null;
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(FIND_NAME_UTENTE_BY_ID_CONVERSAZIONE);) {
			st.setLong(1, idConversazione);
			try (ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				nomeUtente = Utility.getUtente().getId() == idUtente ? rs.getString("nome_utente2") : rs.getString("nome_utente1");
			}
		  }
		} catch (SQLException e) {
			log.error("findNameByIdConversazione", e);
			throw new BusinessException("findNameByIdConversazione", e);
		}
		return nomeUtente;
	}
    
    @Override
	public void updateConversazione(Long fk_utente1, Long fk_utente2) throws BusinessException {
    	
    	java.util.Date date = Calendar.getInstance().getTime();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(UPDATE_CONVERSAZIONE);) {
			st.setDate(1, (Date) date);
			st.setLong(2, fk_utente1);
			st.setLong(3, fk_utente2);
			st.setLong(4, fk_utente2);
			st.setLong(5, fk_utente1);
			st.executeUpdate();

		} catch (SQLException e) {
			log.error("updateConversazione", e);
			throw new BusinessException("updateConversazione", e);
		}

	}

}