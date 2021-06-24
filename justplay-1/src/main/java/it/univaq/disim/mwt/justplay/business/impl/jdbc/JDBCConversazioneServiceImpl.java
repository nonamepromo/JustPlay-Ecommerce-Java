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

@Service
@Transactional
@Slf4j
public class JDBCConversazioneServiceImpl implements ConversazioneService {
    private static final String FIND_CONVERSAZIONI_BY_FK_UTENTE = "SELECT * FROM conversazioni WHERE (fk_utente1 = ? OR fk_utente2 = ?) order by data";
	private static final String UPDATE_CONVERSAZIONE = "UPDATE conversazioni SET chat=?, data=? WHERE (fk_utente1 = ? AND fk_utente2 = ?)  OR (fk_utente1 = ? AND fk_utente2 = ?)";
	private static final String CREATE_CONVERSAZIONE = "INSERT INTO conversazioni (fk_utente1, fk_utente2, chat, data) VALUES (?,?,?,?)";
	private static final String FIND_CONVERSAZIONE_BY_FK_UTENTI = "SELECT c.* FROM conversazioni c WHERE (c.fk_utente1 = ? AND c.fk_utente2 = ?) OR (c.fk_utente1 = ? AND c.fk_utente2 = ?)";
	
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
				conversazione.setFk_utente1(rs.getLong("fk_utente1"));
				conversazione.setFk_utente2(rs.getLong("fk_utente2"));
				conversazione.setChat(rs.getString("chat"));
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
	public Conversazione findConversazioneByFkUtenti(Long fk_utente1, Long fk_utente2) throws BusinessException {
    	Conversazione result = null;
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(FIND_CONVERSAZIONE_BY_FK_UTENTI);) {
			st.setLong(1, fk_utente1);
			st.setLong(2, fk_utente2);
			st.setLong(3, fk_utente2);
			st.setLong(4, fk_utente1);
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					result = new Conversazione();
					result.setFk_utente1(rs.getLong("fk_utente1"));
					result.setFk_utente2(rs.getLong("fk_utente2"));
					result.setChat(rs.getString("chat"));
					result.setData(rs.getDate("data"));
				}
			}
		} catch (SQLException e) {
			log.error("findConversazioneByFkUtenti", e);
			throw new BusinessException("findConversazioneByFkUtenti", e);
		}
		return result;
	}

    @Override
	public void createConversazione(Long fk_utente1, Long fk_utente2, String firstMessage) throws BusinessException {
    	java.util.Date date = Calendar.getInstance().getTime();
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(CREATE_CONVERSAZIONE);) {
			st.setLong(1, fk_utente1);
			st.setLong(2, fk_utente2);
			st.setString(3, firstMessage);
			st.setDate(4, (Date) date);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("createConversazione", e);
			throw new BusinessException("createConversazione", e);
		}
	}
    
    @Override
	public void updateConversazione(Long fk_utente1, Long fk_utente2, String chat) throws BusinessException {
    	
    	java.util.Date date = Calendar.getInstance().getTime();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(UPDATE_CONVERSAZIONE);) {
			st.setString(1, chat);
			st.setDate(2, (Date) date);
			st.setLong(3, fk_utente1);
			st.setLong(4, fk_utente2);
			st.setLong(5, fk_utente2);
			st.setLong(6, fk_utente1);
			st.executeUpdate();

		} catch (SQLException e) {
			log.error("updateConversazione", e);
			throw new BusinessException("updateConversazione", e);
		}

	}

}