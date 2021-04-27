package it.univaq.disim.mwt.justplay.business.impl.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.domain.Utente;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class JDBCUtenteServiceImpl implements UtenteService {
	
	private static final String FIND_USERNAME = "SELECT * FROM utenti u WHERE u.username = ?";
	private static final String UPDATE_PROFILE = "UPDATE utenti SET email=?, codice_fiscale=? , data_nascita=?, telefono=? WHERE id=?";
	
	@Autowired
	private DataSource dataSource;

	@Override
	public Utente findUtenteByUsername(String username) throws BusinessException {
		Utente utente = null;
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(FIND_USERNAME);) {
			st.setString(1, username);
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					utente = new Utente();
					utente.setId(rs.getLong("id"));
					utente.setUsername(username);
					utente.setPassword(rs.getString("password"));
					utente.setCognome(rs.getString("cognome"));
					utente.setNome(rs.getString("nome"));
				} else {
					log.error("errore nella find dell'utente" + username);
					throw new BusinessException();
				}

			}
		} catch (SQLException e) {
			log.error("findUtenteByUsername", e);
			throw new BusinessException("findUtenteByUsername", e);
		}
		return utente;
	}

	@Override
	public void updateProfilo(Utente nuovoProfilo) throws BusinessException {

		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(UPDATE_PROFILE);) {
			st.setLong(5, nuovoProfilo.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			log.error("updateProfilo", e);
			throw new BusinessException("updateProfilo", e);
		}

	}

}
