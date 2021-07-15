package it.univaq.disim.mwt.justplay.business.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class JDBCVideogiocoServiceImpl implements VideogiocoService {

	private static final String FIND_VIDEOGIOCHI = "SELECT vg.* FROM videogiochi vg order by vg.titolo LIMIT ?,?";
	private static final String FIND_VIDEOGIOCHI_PROFILE = "SELECT vg.* FROM videogiochi vg order by vg.titolo";
	private static final String FIND_VIDEOGIOCO_BY_PK = "SELECT vg.* FROM videogiochi vg WHERE vg.id = ?";
	private static final String DELETE_VIDEOGIOCO = "DELETE FROM videogiochi WHERE id=?";
	private static final String INSERT_VIDEOGIOCO_DESIDERATO = "INSERT INTO videogiochi_desiderati (fk_videogioco, fk_utente) VALUES (?,?)";
	private static final String INSERT_VIDEOGIOCO_GIOCATO = "INSERT INTO videogiochi_giocati (fk_videogioco, fk_utente) VALUES (?,?)";
	private static final String INSERT_VIDEOGIOCO_IN_VENDITA = "INSERT INTO videogiochi_in_vendita (fk_videogioco, fk_utente, prezzo, provincia) VALUES (?,?,?,?)";
	private static final String DELETE_VIDEOGIOCO_DESIDERATO = "DELETE FROM videogiochi_desiderati WHERE fk_videogioco = ? AND fk_utente = ?";
	private static final String DELETE_VIDEOGIOCO_GIOCATO = "DELETE FROM videogiochi_giocati WHERE fk_videogioco = ? AND fk_utente = ?";
	private static final String DELETE_VIDEOGIOCO_IN_VENDITA = "DELETE FROM videogiochi_in_vendita WHERE fk_videogioco = ? AND fk_utente = ?";
	private static final String CHECK_IF_VIDEOGIOCO_IS_DESIDERATO = "SELECT vg.* FROM videogiochi_desiderati vg WHERE vg.fk_utente = ? AND vg.fk_videogioco = ?";
	private static final String CHECK_IF_VIDEOGIOCO_IS_GIOCATO = "SELECT vg.* FROM videogiochi_giocati vg WHERE vg.fk_utente = ? AND vg.fk_videogioco = ?";
	private static final String CHECK_IF_VIDEOGIOCO_IS_IN_VENDITA = "SELECT vg.* FROM videogiochi_in_vendita vg WHERE vg.fk_utente = ? AND vg.fk_videogioco = ?";
	private static final String FIND_VIDEOGIOCHI_DESIDERATI = "SELECT vg.* FROM videogiochi_desiderati vg WHERE vg.fk_utente = ?";
	private static final String FIND_VIDEOGIOCHI_GIOCATI = "SELECT vg.* FROM videogiochi_giocati vg WHERE vg.fk_utente = ?";
	private static final String FIND_VIDEOGIOCHI_IN_VENDITA = "SELECT vg.* FROM videogiochi_in_vendita vg WHERE vg.fk_utente = ?";

	@Autowired
	private DataSource dataSource;

	/* VIDEOGIOCO */
	@Override
	public ResponseEntity<List<Videogioco>> findAllVideogiochiPaginated() throws BusinessException {

		String baseSearch = "SELECT * FROM videogiochi";

		List<Videogioco> videogiochi = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(baseSearch);) {
			while (rs.next()) {
				Videogioco videogioco = new Videogioco();
				videogioco.setId(rs.getLong("id"));
				videogioco.setTitolo(rs.getString("titolo"));
				videogioco.setPiattaforma(rs.getString("piattaforma"));
				videogioco.setAnnoDiUscita(rs.getInt("annoDiUscita"));
				videogioco.setDescrizione(rs.getString("descrizione"));
				videogioco.setPs4Url(rs.getString("ps4Url"));
				videogioco.setXboxUrl(rs.getString("xboxUrl"));
				videogioco.setPcUrl(rs.getString("pcUrl"));
				videogioco.setImageUrl(rs.getString("imageUrl"));
				videogiochi.add(videogioco);
			}
		} catch (SQLException e) {
			log.error("findAllVideogiochiPaginated", e);
			throw new BusinessException("", e);
		}

		return ResponseEntity.ok(videogiochi);

	}

	@Override
	public List<Videogioco> findAll(int index) throws BusinessException {
		List<Videogioco> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI);) {
			(st).setLong(1, index - 3);
			(st).setLong(2, index);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Videogioco videogioco = new Videogioco();
					videogioco.setId(rs.getLong("id"));
					videogioco.setTitolo(rs.getString("titolo"));
					videogioco.setDescrizione(rs.getString("descrizione"));
					videogioco.setAnnoDiUscita(rs.getInt("annoDiUscita"));
					videogioco.setPiattaforma(rs.getString("piattaforma"));
					videogioco.setPs4Url(rs.getString("ps4Url"));
					videogioco.setXboxUrl(rs.getString("xboxUrl"));
					videogioco.setPcUrl(rs.getString("pcUrl"));
					videogioco.setImageUrl(rs.getString("imageUrl"));
					result.add(videogioco);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	@Override
	public List<Videogioco> findAllProfile() throws BusinessException {
		List<Videogioco> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(FIND_VIDEOGIOCHI_PROFILE);) {
			while (rs.next()) {
				Videogioco videogioco = new Videogioco();
				videogioco.setId(rs.getLong("id"));
				videogioco.setTitolo(rs.getString("titolo"));
				videogioco.setDescrizione(rs.getString("descrizione"));
				videogioco.setAnnoDiUscita(rs.getInt("annoDiUscita"));
				videogioco.setPiattaforma(rs.getString("piattaforma"));
				videogioco.setPs4Url(rs.getString("ps4Url"));
				videogioco.setXboxUrl(rs.getString("xboxUrl"));
				videogioco.setPcUrl(rs.getString("pcUrl"));
				videogioco.setImageUrl(rs.getString("imageUrl"));
				result.add(videogioco);
			}
		} catch (SQLException e) {
			log.error("findAllProfile", e);
			throw new BusinessException("findAllProfile", e);
		}
		return result;
	}

	@Override
	public List<Long> getWishlist(Long idUtente) throws BusinessException {
		List<Long> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI_DESIDERATI);) {
			(st).setLong(1, idUtente);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Long videogiocoDesiderato = rs.getLong("fk_videogioco");
					result.add(videogiocoDesiderato);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	@Override
	public List<Long> getPlayedlist(Long idUtente) throws BusinessException {
		List<Long> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI_GIOCATI);) {
			(st).setLong(1, idUtente);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Long videogiocoGiocato = rs.getLong("fk_videogioco");
					result.add(videogiocoGiocato);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	@Override
	public List<Long> getSellinglist(Long idUtente) throws BusinessException {
		List<Long> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI_IN_VENDITA);) {
			(st).setLong(1, idUtente);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Long videogiocoGiocato = rs.getLong("fk_videogioco");
					result.add(videogiocoGiocato);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	// @Override
	// public ResponseEntity<Videogioco> findVideogiocoByID(Long id) throws
	// BusinessException {
	// Videogioco result = null;
	// try (Connection con = dataSource.getConnection(); PreparedStatement st =
	// con.prepareStatement(FIND_VIDEOGIOCO_BY_PK);) {
	// st.setLong(1, id);
	// try (ResultSet rs = st.executeQuery();) {
	// if (rs.next()) {
	// result = new Videogioco();
	// result.setId(rs.getLong("id"));
	// result.setTitolo(rs.getString("titolo"));
	// result.setPiattaforma(rs.getString("piattaforma"));
	// result.setAnnoDiUscita(rs.getInt("annoDiUscita"));
	// }
	// }
	// } catch (SQLException e) {
	// log.error("findVideogiocoByID", e);
	// throw new BusinessException("findVideogiocoByID", e);
	// }
	// return ResponseEntity.ok(result);
	// }

	@Override
	public Videogioco findVideogiocoByID(Long id) throws BusinessException {
		Videogioco result = null;
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCO_BY_PK);) {
			st.setLong(1, id);
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					result = new Videogioco();
					result.setId(rs.getLong("id"));
					result.setTitolo(rs.getString("titolo"));
					result.setDescrizione(rs.getString("descrizione"));
					result.setAnnoDiUscita(rs.getInt("annoDiUscita"));
					result.setPs4Url(rs.getString("ps4Url"));
					result.setXboxUrl(rs.getString("xboxUrl"));
					result.setPcUrl(rs.getString("pcUrl"));
					result.setImageUrl(rs.getString("imageUrl"));
				}
			}
		} catch (SQLException e) {
			log.error("findVideogiocoByID", e);
			throw new BusinessException("findVideogiocoByID", e);
		}
		return result;
	}

	@Override
	public void addGameToWishlist(Long idVideogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_DESIDERATO);) {
			st.setLong(1, idVideogioco);
			st.setLong(2, idUtente);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("addGameToWishlist", e);
			throw new BusinessException("addGameToWishlist", e);
		}
	}

	@Override
	public void addGameToPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_GIOCATO);) {
			st.setLong(1, idVideogioco);
			st.setLong(2, idUtente);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("addGameToPlayedlist", e);
			throw new BusinessException("addGameToPlayedlist", e);
		}
	}

	@Override
	public void addGameToSellinglist(Long idVideogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_IN_VENDITA);) {
			st.setLong(1, idVideogioco);
			st.setLong(2, idUtente);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("addGameToSellingList", e);
			throw new BusinessException("addGameToSellingList", e);
		}
	}

	@Override
	public void removeGameFromWishlist(Long idVideogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(DELETE_VIDEOGIOCO_DESIDERATO);) {
			st.setLong(1, idVideogioco);
			st.setLong(2, idUtente);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("deleteVideogiocoDesiderato", e);
			throw new BusinessException("deleteVideogiocoDesiderato", e);
		}
	}

	@Override
	public void removeGameFromPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(DELETE_VIDEOGIOCO_GIOCATO);) {
			st.setLong(1, idVideogioco);
			st.setLong(2, idUtente);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("deleteVideogiocoGiocato", e);
			throw new BusinessException("deleteVideogiocoGiocato", e);
		}
	}

	@Override
	public void removeGameFromSellinglist(Long idVideogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(DELETE_VIDEOGIOCO_IN_VENDITA);) {
			st.setLong(1, idVideogioco);
			st.setLong(2, idUtente);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("removeGameFromSellingList", e);
			throw new BusinessException("removeGameFromSellingList", e);
		}
	}

	// @Override
	// public boolean checkIfGameIsDesidered(Long idUtente, Long idVideogioco)
	// throws BusinessException {
	// boolean result = false;
	// try (Connection con = dataSource.getConnection(); PreparedStatement st =
	// con.prepareStatement(CHECK_IF_VIDEOGIOCO_IS_DESIDERATO);) {
	// st.setLong(1, idUtente);
	// st.setLong(2, idVideogioco);
	// try (ResultSet rs = st.executeQuery();) {
	// if (rs.next()) {
	// result = true;
	// }
	// }
	// } catch (SQLException e) {
	// log.error("checkIfGameIsDesidered", e);
	// throw new BusinessException("checkIfGameIsDesidered", e);
	// }
	// return result;
	// }

	@Override
	public void deleteVideogioco(Videogioco videogioco) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(DELETE_VIDEOGIOCO);) {
			st.setLong(1, videogioco.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("deleteVideogioco", e);
			throw new BusinessException("deleteVideogioco", e);
		}
	}

	@Override
	public void addVideogiocoInVendita(Videogioco videogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_IN_VENDITA);) {
			st.setLong(1, idUtente);
			st.setLong(1, videogioco.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("addVideogiocoInVendita", e);
			throw new BusinessException("addVideogiocoInVendita", e);
		}
	}

	@Override
	public void addVideogiocoDesiderato(Videogioco videogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_DESIDERATO);) {
			st.setLong(1, idUtente);
			st.setLong(1, videogioco.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("addVideogiocoInVendita", e);
			throw new BusinessException("addVideogiocoInVendita", e);
		}
	}

	@Override
	public void addVideogiocoGiocato(Videogioco videogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_GIOCATO);) {
			st.setLong(1, idUtente);
			st.setLong(1, videogioco.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("addVideogiocoInVendita", e);
			throw new BusinessException("addVideogiocoInVendita", e);
		}
	}

	@Override
	public void addGameToSellinglistProva(VideogiocoInVendita videogiocoInVendita) throws BusinessException {
		// TODO Auto-generated method stub

	}
}
