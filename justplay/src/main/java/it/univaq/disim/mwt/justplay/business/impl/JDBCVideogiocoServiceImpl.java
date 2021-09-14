package it.univaq.disim.mwt.justplay.business.impl;

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
public class JDBCVideogiocoServiceImpl {

	private static final String GET_VIDEOGIOCHI_COUNT = "SELECT COUNT(*) FROM videogiochi";
	private static String GET_VIDEOGIOCHI_COUNT_BY_PLATFORM = null;
	private static final String FIND_VIDEOGIOCHI = "SELECT vg.* FROM videogiochi vg order by vg.titolo LIMIT ?,?";
	private static String FIND_VIDEOGIOCHI_BY_PLATFORM = null;
	private static final String FIND_VIDEOGIOCHI_PROFILE = "SELECT vg.* FROM videogiochi vg order by vg.titolo";
	private static final String FIND_VIDEOGIOCHI_VENDITA = "SELECT vg.* FROM videogiochi_in_vendita vg where vg.fkVideogioco = ? order by vg.prezzo";
	private static final String FIND_VIDEOGIOCO_BY_PK = "SELECT vg.* FROM videogiochi vg WHERE vg.id = ?";
	private static final String DELETE_VIDEOGIOCO = "DELETE FROM videogiochi WHERE id=?";
	private static final String INSERT_VIDEOGIOCO_DESIDERATO = "INSERT INTO videogiochi_desiderati (fkVideogioco, fkUtente) VALUES (?,?)";
	private static final String INSERT_VIDEOGIOCO_GIOCATO = "INSERT INTO videogiochi_giocati (fkVideogioco, fkUtente) VALUES (?,?)";
	private static final String INSERT_VIDEOGIOCO_IN_VENDITA = "INSERT INTO videogiochi_in_vendita (fkVideogioco, fkUtente, prezzo, prezzo_spedizione, provincia, piattaforma) VALUES (?,?,?,?,?,?)";
	private static final String DELETE_VIDEOGIOCO_DESIDERATO = "DELETE FROM videogiochi_desiderati WHERE fkVideogioco = ? AND fkUtente = ?";
	private static final String DELETE_VIDEOGIOCO_GIOCATO = "DELETE FROM videogiochi_giocati WHERE fkVideogioco = ? AND fkUtente = ?";
	private static final String DELETE_VIDEOGIOCO_IN_VENDITA = "DELETE FROM videogiochi_in_vendita WHERE fkVideogioco = ? AND fkUtente = ?";
	private static final String CHECK_IF_VIDEOGIOCO_IS_DESIDERATO = "SELECT vg.* FROM videogiochi_desiderati vg WHERE vg.fkUtente = ? AND vg.fkVideogioco = ?";
	private static final String CHECK_IF_VIDEOGIOCO_IS_GIOCATO = "SELECT vg.* FROM videogiochi_giocati vg WHERE vg.fkUtente = ? AND vg.fkVideogioco = ?";
	private static final String CHECK_IF_VIDEOGIOCO_IS_IN_VENDITA = "SELECT vg.* FROM videogiochi_in_vendita vg WHERE vg.fkUtente = ? AND vg.fkVideogioco = ?";
	private static final String FIND_VIDEOGIOCHI_DESIDERATI = "SELECT vg.* FROM videogiochi_desiderati vg WHERE vg.fkUtente = ?";
	private static final String FIND_VIDEOGIOCHI_GIOCATI = "SELECT vg.* FROM videogiochi_giocati vg WHERE vg.fkUtente = ?";
	private static final String FIND_VIDEOGIOCHI_IN_VENDITA = "SELECT vg.* FROM videogiochi_in_vendita vg WHERE vg.fkVideogioco = ?";

	@Autowired
	private DataSource dataSource;

	/* VIDEOGIOCO */
	// @Override
	// public ResponseEntity<List<Videogioco>> findAllVideogiochiPaginated() throws
	// BusinessException {

	// String baseSearch = "SELECT * FROM videogiochi";

	// List<Videogioco> videogiochi = new ArrayList<>();
	// try (Connection con = dataSource.getConnection();
	// Statement st = con.createStatement();
	// ResultSet rs = st.executeQuery(baseSearch);) {
	// while (rs.next()) {
	// Videogioco videogioco = new Videogioco();
	// videogioco.setId(rs.getLong("id"));
	// videogioco.setTitolo(rs.getString("titolo"));
	// videogioco.setPiattaforma(rs.getString("piattaforma"));
	// videogioco.setAnnoDiUscita(rs.getInt("annoDiUscita"));
	// videogioco.setDescrizione(rs.getString("descrizione"));
	// videogioco.setPs4Url(rs.getString("ps4_url"));
	// videogioco.setXboxUrl(rs.getString("xbox_url"));
	// videogioco.setPcUrl(rs.getString("pc_url"));
	// videogioco.setImageUrl(rs.getString("image_url"));
	// videogiochi.add(videogioco);
	// }
	// } catch (SQLException e) {
	// log.error("findAllVideogiochiPaginated", e);
	// throw new BusinessException("", e);
	// }

	// return ResponseEntity.ok(videogiochi);

	// }

	
	public int getVideogiochiCount(String platform) throws BusinessException {
		int result = 0;
		switch(platform) {
			case "all": GET_VIDEOGIOCHI_COUNT_BY_PLATFORM = GET_VIDEOGIOCHI_COUNT;
			break;
			case "ps4": GET_VIDEOGIOCHI_COUNT_BY_PLATFORM = "SELECT COUNT(*) FROM videogiochi WHERE ps4_url IS NOT NULL";
			break;
			case "xbox": GET_VIDEOGIOCHI_COUNT_BY_PLATFORM = "SELECT COUNT(*) FROM videogiochi WHERE xbox_url IS NOT NULL";
			break;
			case "pc": GET_VIDEOGIOCHI_COUNT_BY_PLATFORM = "SELECT COUNT(*) FROM videogiochi WHERE pc_url IS NOT NULL";
			break;
		} 
		try (Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(GET_VIDEOGIOCHI_COUNT_BY_PLATFORM)) {
					while (rs.next()) {
						result = rs.getInt("COUNT(*)");
					}
		} catch (SQLException e) {
			log.error("findAllProfile", e);
			throw new BusinessException("findAllProfile", e);
		}
		return result;
	}


	
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
					// videogioco.setPs4Url(rs.getString("ps4_url"));
					// videogioco.setXboxUrl(rs.getString("xbox_url"));
					// videogioco.setPcUrl(rs.getString("pc_url"));
					// videogioco.setImageUrl(rs.getString("image_url"));
					result.add(videogioco);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	
	public List<Videogioco> findByPlatform(String platform, int index) throws BusinessException {
		List<Videogioco> result = new ArrayList<>();
		switch(platform) {
			case "all": FIND_VIDEOGIOCHI_BY_PLATFORM = FIND_VIDEOGIOCHI;
			break;
			case "ps4": FIND_VIDEOGIOCHI_BY_PLATFORM = "SELECT vg.* FROM videogiochi vg where vg.ps4_url IS NOT NULL order by vg.titolo LIMIT ?,?";
			break;
			case "xbox": FIND_VIDEOGIOCHI_BY_PLATFORM = "SELECT vg.* FROM videogiochi vg where vg.xbox_url IS NOT NULL order by vg.titolo LIMIT ?,?";
			break;
			case "pc": FIND_VIDEOGIOCHI_BY_PLATFORM = "SELECT vg.* FROM videogiochi vg where vg.pc_url IS NOT NULL order by vg.titolo LIMIT ?,?";
			break;
		} 
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI_BY_PLATFORM);) {
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
					// videogioco.setPs4Url(rs.getString("ps4_url"));
					// videogioco.setXboxUrl(rs.getString("xbox_url"));
					// videogioco.setPcUrl(rs.getString("pc_url"));
					// videogioco.setImageUrl(rs.getString("image_url"));
					result.add(videogioco);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	
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
				// videogioco.setPs4Url(rs.getString("ps4_url"));
				// videogioco.setXboxUrl(rs.getString("xbox_url"));
				// videogioco.setPcUrl(rs.getString("pc_url"));
				// videogioco.setImageUrl(rs.getString("image_url"));
				result.add(videogioco);
			}
		} catch (SQLException e) {
			log.error("findAllProfile", e);
			throw new BusinessException("findAllProfile", e);
		}
		return result;
	}

	
	// public List<VideogiocoInVendita> findAllVendita(Long idVideogioco) throws BusinessException {
	// 	List<VideogiocoInVendita> result = new ArrayList<>();
	// 	try (Connection con = dataSource.getConnection();
	// 			PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI_IN_VENDITA);) {
	// 		(st).setLong(1, idVideogioco);
	// 		try (ResultSet rs = st.executeQuery();) {
	// 			while (rs.next()) {
	// 				VideogiocoInVendita videogiocoInVendita = new VideogiocoInVendita();
	// 				videogiocoInVendita.setFk_utente(rs.getLong("fkUtente"));
	// 				videogiocoInVendita.setFk_videogioco(rs.getLong("fkVideogioco"));
	// 				videogiocoInVendita.setPrezzo(rs.getInt("prezzo"));
	// 				videogiocoInVendita.setPrezzo_spedizione(rs.getInt("prezzo_spedizione"));
	// 				videogiocoInVendita.setProvincia(rs.getString("provincia"));
	// 				videogiocoInVendita.setPiattaforma(rs.getString("piattaforma"));
	// 				result.add(videogiocoInVendita);
	// 			}
	// 		}
	// 	} catch (SQLException e) {
	// 		log.error("findAllVendita", e);
	// 		throw new BusinessException("findAllVendita", e);
	// 	}
	// 	return result;
	// }

	
	public List<Long> getWishlist(Long idUtente) throws BusinessException {
		List<Long> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI_DESIDERATI);) {
			(st).setLong(1, idUtente);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Long videogiocoDesiderato = rs.getLong("fkVideogioco");
					result.add(videogiocoDesiderato);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	
	public List<Long> getPlayedlist(Long idUtente) throws BusinessException {
		List<Long> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI_GIOCATI);) {
			(st).setLong(1, idUtente);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Long videogiocoGiocato = rs.getLong("fkVideogioco");
					result.add(videogiocoGiocato);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	
	public List<Long> getSellinglist(Long idVideogioco) throws BusinessException {
		List<Long> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCHI_IN_VENDITA);) {
			(st).setLong(1, idVideogioco);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Long videogiocoInVendita = rs.getLong("fkVideogioco");
					result.add(videogiocoInVendita);
				}
			}
		} catch (SQLException e) {
			log.error("findAll", e);
			throw new BusinessException("findAll", e);
		}
		return result;
	}

	// 
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
					// result.setPs4_Url(rs.getString("ps4_url"));
					// result.setXboxUrl(rs.getString("xbox_url"));
					// result.setPcUrl(rs.getString("pc_url"));
					// result.setImageUrl(rs.getString("image_url"));
				}
			}
		} catch (SQLException e) {
			log.error("findVideogiocoByID", e);
			throw new BusinessException("findVideogiocoByID", e);
		}
		return result;
	}

	
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

	
	public void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Long idVideogioco, Long idUtente)
			throws BusinessException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_IN_VENDITA);) {
			st.setLong(1, idVideogioco);
			st.setLong(2, idUtente);
			st.setInt(3, videogiocoInVendita.getPrezzo());
			st.setInt(4, videogiocoInVendita.getPrezzo_spedizione());
			st.setString(5, videogiocoInVendita.getProvincia());
			st.setString(6, videogiocoInVendita.getPiattaforma());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("addGameToSellinglistProva", e);
			throw new BusinessException("addGameToSellinglistProva", e);
		}
	}

	
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

	// 
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

	/*
	 *  public void deleteVideogioco(Videogioco videogioco) throws
	 * BusinessException { try (Connection con = dataSource.getConnection();
	 * PreparedStatement st = con.prepareStatement(DELETE_VIDEOGIOCO);) {
	 * st.setLong(1, videogioco.getId()); st.executeUpdate(); } catch (SQLException
	 * e) { log.error("deleteVideogioco", e); throw new
	 * BusinessException("deleteVideogioco", e); } }
	 * 
	 *  public void addVideogiocoInVendita(Videogioco videogioco, Long
	 * idUtente, Long prezzo, String provincia) throws BusinessException { try
	 * (Connection con = dataSource.getConnection(); PreparedStatement st =
	 * con.prepareStatement(INSERT_VIDEOGIOCO_IN_VENDITA);) { st.setLong(1,
	 * idUtente); st.setLong(2, videogioco.getId()); st.executeUpdate(); } catch
	 * (SQLException e) { log.error("addVideogiocoInVendita", e); throw new
	 * BusinessException("addVideogiocoInVendita", e); } }
	 * 
	 *  public void addVideogiocoDesiderato(Videogioco videogioco, Long
	 * idUtente) throws BusinessException { try (Connection con =
	 * dataSource.getConnection(); PreparedStatement st =
	 * con.prepareStatement(INSERT_VIDEOGIOCO_DESIDERATO);) { st.setLong(1,
	 * idUtente); st.setLong(1, videogioco.getId()); st.executeUpdate(); } catch
	 * (SQLException e) { log.error("addVideogiocoInVendita", e); throw new
	 * BusinessException("addVideogiocoInVendita", e); } }
	 * 
	 *  public void addVideogiocoGiocato(Videogioco videogioco, Long
	 * idUtente) throws BusinessException { try (Connection con =
	 * dataSource.getConnection(); PreparedStatement st =
	 * con.prepareStatement(INSERT_VIDEOGIOCO_GIOCATO);) { st.setLong(1, idUtente);
	 * st.setLong(1, videogioco.getId()); st.executeUpdate(); } catch (SQLException
	 * e) { log.error("addVideogiocoInVendita", e); throw new
	 * BusinessException("addVideogiocoInVendita", e); } }
	 */
}
