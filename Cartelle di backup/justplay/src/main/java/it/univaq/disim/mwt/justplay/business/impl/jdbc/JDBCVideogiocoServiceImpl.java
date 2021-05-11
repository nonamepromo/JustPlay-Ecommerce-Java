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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.RequestGrid;
import it.univaq.disim.mwt.justplay.business.ResponseGrid;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class JDBCVideogiocoServiceImpl implements VideogiocoService {


	private static final String FIND_VIDEOGIOCHI = "SELECT vg.* FROM videogiochi vg order by vg.titolo";
	private static final String FIND_VIDEOGIOCO_BY_PK = "SELECT vg.* FROM videogiochi vg WHERE vg.id = ?";
	private static final String DELETE_VIDEOGIOCO = "DELETE FROM videogiochi WHERE id=?";
	private static final String INSERT_VIDEOGIOCO_IN_VENDITA = "INSERT INTO videogiochi_in_vendita (fk_videogioco, fk_utente) VALUES (?,?)";
	private static final String INSERT_VIDEOGIOCO_DESIDERATO = "INSERT INTO videogiochi_desiderato (fk_videogioco, fk_utente) VALUES (?,?)";

	@Autowired
	private DataSource dataSource;
	
	/* VIDEOGIOCO */
	@Override
	public ResponseGrid<Videogioco> findAllVideogiochiPaginated(RequestGrid requestGrid) throws BusinessException {
		String sortCol = requestGrid.getSortCol();
		if ("id".equals(requestGrid.getSortCol())) {
			sortCol = "vg.id";
		}
		String orderBy = (!"".equals(sortCol) && !"".equals(requestGrid.getSortDir())) ? " ORDER BY " + sortCol + " " + requestGrid.getSortDir() : "";
		String baseSearch = "SELECT vg.* " + "FROM videogiochi vg " + ((!"".equals(requestGrid.getSearch().getValue())) ? " WHERE vg.titolo like '" + ConversionUtility.addPercentSuffix(requestGrid.getSearch().getValue()) + "'" : "");

		String sql = baseSearch + orderBy + " LIMIT " + requestGrid.getStart() + ", " + requestGrid.getLength();
		String countSql = "SELECT count(*) FROM (" + baseSearch + ") as t1";
		long records = 0;

		List<Videogioco> videogiochi = new ArrayList<>();
		try (Connection con = dataSource.getConnection(); Statement st = con.createStatement(); ResultSet rsCount = st.executeQuery(countSql);) {
			// COUNT ELEMENTS
			if (rsCount.next()) {
				records = rsCount.getLong(1);
			}

			// EXECUTE SQL LIMITED
			try (ResultSet rs = st.executeQuery(sql);) {
				while (rs.next()) {
					Videogioco videogioco = new Videogioco();
					videogioco.setId(rs.getLong("id"));
					videogioco.setTitolo(rs.getString("titolo"));
					videogiochi.add(videogioco);
				}
			}

		} catch (SQLException e) {
			log.error("findAllVideogiochiPaginated", e);
			throw new BusinessException("", e);
		}
		return new ResponseGrid<Videogioco>(requestGrid.getDraw(), records, records, videogiochi);

	}

	@Override
	public List<Videogioco> findAllVideogiochi() throws BusinessException {
		List<Videogioco> result = new ArrayList<>();
		try (Connection con = dataSource.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(FIND_VIDEOGIOCHI);) {
			while (rs.next()) {
				Videogioco videogioco = new Videogioco();
				videogioco.setId(rs.getLong("id"));
				videogioco.setTitolo(rs.getString("titolo"));
				result.add(videogioco);
			}
		} catch (SQLException e) {
			log.error("findAllVideogiochi", e);
			throw new BusinessException("findAllVideogiochi", e);
		}
		return result;
	}

	@Override
	public Videogioco findVideogiocoByID(Long id) throws BusinessException {
		Videogioco result = null;
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(FIND_VIDEOGIOCO_BY_PK);) {
			st.setLong(1, id);
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					result = new Videogioco();
					result.setId(rs.getLong("id"));
					result.setTitolo(rs.getString("titolo"));
				}
			}
		} catch (SQLException e) {
			log.error("findVideogiocoByID", e);
			throw new BusinessException("findVideogiocoByID", e);
		}
		return result;
	}

	@Override
	public void deleteVideogioco(Videogioco videogioco) throws BusinessException {
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(DELETE_VIDEOGIOCO);) {
			st.setLong(1, videogioco.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("deleteVideogioco", e);
			throw new BusinessException("deleteVideogioco", e);
		}
	}

	@Override
	public void addVideogiocoInVendita(Videogioco videogioco, Long idUtente) throws BusinessException {
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_IN_VENDITA);) {
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
		try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(INSERT_VIDEOGIOCO_DESIDERATO);) {
			st.setLong(1, idUtente);
			st.setLong(1, videogioco.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("addVideogiocoInVendita", e);
			throw new BusinessException("addVideogiocoInVendita", e);
		}
	}
}