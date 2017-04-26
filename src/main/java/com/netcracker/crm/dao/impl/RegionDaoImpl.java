package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static com.netcracker.crm.dao.impl.sql.RegionSqlQuery.*;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
@Repository
public class RegionDaoImpl implements RegionDao {
    private static final Logger log = LoggerFactory.getLogger(RegionDaoImpl.class);

    @Autowired
    private DiscountDao discountDao;

    private SimpleJdbcInsert regionInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public long create(Region region) {
        if (region.getId() != null) {
            return -1L;
        }
        Long discountId = getDiscountId(region.getDiscount());
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_REGION_NAME, region.getName())
                .addValue(PARAM_REGION_DISCOUNT, discountId);
        Long id = regionInsert.executeAndReturnKey(params).longValue();
        log.info("Region with id: " + id + " is successfully created.");
        return id;
    }


    @Override
    public long update(Region region) {
        Long regionId = region.getId();
        if (regionId == null) {
            return -1L;
        }
        Long discountId = getDiscountId(region.getDiscount());
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_REGION_ID, regionId)
                .addValue(PARAM_REGION_NAME, region.getName())
                .addValue(PARAM_REGION_DISCOUNT, discountId);
        int affectedRows = namedJdbcTemplate.update(SQL_UPDATE_REGION, params);
        if (affectedRows == 0) {
            log.error("Region has not been updated");
            return -1L;
        } else {
            log.info("Region with id " + regionId + " was successfully updated");
            return affectedRows;
        }
    }

    @Override
    public long delete(Long id) {
        if (id < 1) {
            return -1L;
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PARAM_REGION_ID, id);
        int deletedRows = namedJdbcTemplate.update(SQL_DELETE_REGION, params);
        if (deletedRows == 0) {
            log.error("Region has not been deleted");
            return -1L;
        } else {
            log.info("Region with id " + id + " was successfully deleted");
            return deletedRows;
        }
    }

    @Override
    public long delete(Region region) {
        Long regionId = region.getId();
        if (regionId == null) {
            return -1L;
        } else {
            return delete(regionId);
        }
    }

    @Override
    public Region findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_REGION_ID, id);
        return namedJdbcTemplate.queryForObject(SQL_FIND_REGION_BY_ID, params, new RegionRowMapper());
    }

    @Override
    public List<Region> findByName(String name) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_REGION_NAME, "%" + name + "%");
        return namedJdbcTemplate.query(SQL_FIND_REGION_BY_NAME, params, new RegionRowMapper());
    }

    @Override
    public long getCount() {
        return namedJdbcTemplate.getJdbcOperations().queryForObject(SQL_GET_REGION_COUNT, Long.class);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        regionInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns(PARAM_REGION_ID)
                .withTableName(PARAM_REGION_TABLE);
    }

    private Long getDiscountId(Discount discount) {
        Long discountId = discount.getId();
        if (discountId != null) {
            return discountId;
        } else {
            return discountDao.create(discount);
        }
    }

    private static final class RegionRowMapper implements RowMapper<Region> {
        @Override
        public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
            log.debug("Start mapping data");
            Region region = new Region();
            region.setId(rs.getLong(PARAM_REGION_ID));
            region.setName(rs.getString(PARAM_REGION_NAME));

            Long discountId = rs.getLong(PARAM_REGION_DISC_ID);
            if (discountId > 0) {
                Discount discount = new Discount();
                discount.setId(discountId);
                discount.setTitle(rs.getString(PARAM_REGION_DISC_TITLE));
                discount.setPercentage(rs.getDouble(PARAM_REGION_DISC_PERC));
                discount.setDescription(rs.getString(PARAM_REGION_DISC_DESC));
                Timestamp dateFromDB = rs.getTimestamp(PARAM_REGION_DISC_START);
                if (dateFromDB != null) {
                    discount.setDateStart(dateFromDB.toLocalDateTime().toLocalDate());
                }
                dateFromDB = rs.getTimestamp(PARAM_REGION_DISC_FINISH);
                if (dateFromDB != null) {
                    discount.setDateFinish(dateFromDB.toLocalDateTime().toLocalDate());
                }
                region.setDiscount(discount);
            }
            log.debug("End mapping data");
            return region;
        }
    }
}
