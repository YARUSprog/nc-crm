/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.*;
import com.netcracker.crm.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.netcracker.crm.dao.impl.sql.HistorySqlQuery.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

/**
 *
 * @author YARUS
 */
@Repository
public class HistoryDaoImpl implements HistoryDao {

    private static final Logger log = LoggerFactory.getLogger(HistoryDaoImpl.class);

    @Autowired
    private ComplaintDao complaintDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

//    @Autowired
//    private StatusDao statusDao;
    private SimpleJdbcInsert simpleInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_HISTORY_TABLE)
                .usingGeneratedKeyColumns(PARAM_HISTORY_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long create(History history) {
        if (history == null) {
            return -1L;
        }
        Long orderId = getOrderId(history.getOrder());
        Long complaintId = getComplaintId(history.getComplaint());
        Long productId = getProductId(history.getProduct());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_DATE_CHANGE_STATUS, history.getDateChangeStatus())
                .addValue(PARAM_HISTORY_DESC_CHANGE_STATUS, history.getDescChangeStatus())
                .addValue(PARAM_HISTORY_OLD_STATUS_ID, history.getOldStatus().getId())
                .addValue(PARAM_HISTORY_ORDER_ID, orderId)
                .addValue(PARAM_HISTORY_COMPLAINT_ID, complaintId)
                .addValue(PARAM_HISTORY_PRODUCT_ID, productId);

        KeyHolder keys = new GeneratedKeyHolder();
        int affectedRows = namedJdbcTemplate.update(SQL_CREATE_HISTORY, params, keys);

        Long newId = -1L;
        if (affectedRows > 0) {
            newId = (Long) keys.getKeys().get(PARAM_HISTORY_ID);
            history.setId(newId);
            log.info("History with id: " + newId + " is successfully created.");
            return newId;
        } else {
            log.error("History doesn't created.");
            return newId;
        }
    }

    private Long getOrderId(Order order) {
        if (order == null) {
            return null;
        }
        Long orderId = order.getId();
        if (orderId != null) {
            return orderId;
        }
        orderId = orderDao.create(order);
        return orderId;
    }

    private Long getComplaintId(Complaint complaint) {
        if (complaint == null) {
            return null;
        }
        Long complaintId = complaint.getId();
        if (complaintId != null) {
            return complaintId;
        }
        complaintId = complaintDao.create(complaint);
        return complaintId;
    }

    private Long getProductId(Product product) {
        if (product == null) {
            return null;
        }
        Long productId = product.getId();
        if (productId != null) {
            return productId;
        }
        productId = productDao.create(product);
        return productId;
    }

    @Override
    public Long update(History history) {
        if (history == null) {
            return -1L;
        }
        Long orderId = getOrderId(history.getOrder());
        Long complaintId = getComplaintId(history.getComplaint());
        Long productId = getProductId(history.getProduct());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_ID, history.getId())
                .addValue(PARAM_HISTORY_DATE_CHANGE_STATUS, history.getDateChangeStatus())
                .addValue(PARAM_HISTORY_DESC_CHANGE_STATUS, history.getDescChangeStatus())
                .addValue(PARAM_HISTORY_OLD_STATUS_ID, history.getOldStatus().getId())
                .addValue(PARAM_HISTORY_ORDER_ID, orderId)
                .addValue(PARAM_HISTORY_COMPLAINT_ID, complaintId)
                .addValue(PARAM_HISTORY_PRODUCT_ID, productId);

        KeyHolder keys = new GeneratedKeyHolder();
        long affectedRows = namedJdbcTemplate.update(SQL_UPDATE_HISTORY, params, keys);

        if (affectedRows > 0) {
            log.info("History with id: " + history.getId() + " is successfully updated.");
            return affectedRows;
        } else {
            log.error("History doesn't updated.");
            return affectedRows;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id == null || id <= 0) {
            return -1L;
        }
        SqlParameterSource params = new MapSqlParameterSource().addValue(PARAM_HISTORY_ID, id);

        long affectedRows = namedJdbcTemplate.update(SQL_DELETE_HISTORY, params);

        if (affectedRows > 0) {
            log.info("History with id: " + id + " is successfully deleted.");
            return affectedRows;
        } else {
            log.error("History doesn't deleted.");
            return affectedRows;
        }
    }

    @Override
    public Long delete(History object) {
        if (object == null || object.getId() == null || object.getId() <= 0) {
            return -1L;
        }
        SqlParameterSource params = new MapSqlParameterSource().addValue(PARAM_HISTORY_ID, object.getId());

        long affectedRows = namedJdbcTemplate.update(SQL_DELETE_HISTORY, params);

        if (affectedRows > 0) {
            log.info("History with id: " + object.getId() + " is successfully deleted.");
            return affectedRows;
        } else {
            log.error("History doesn't deleted.");
            return affectedRows;
        }
    }

    @Override
    public History findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_ID, id);
        List<History> allHistory = namedJdbcTemplate.query(SQL_FIND_HISTORY_BY_ID, params, new HistoryWithDetailExtractor());
        return allHistory.get(0);
    }

    @Override
    public List<History> findAllByDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_DATE_CHANGE_STATUS, date);

        List<History> allHistory = namedJdbcTemplate.query(SQL_FIND_ALL_HISTORY_BY_DATE, params, new HistoryWithDetailExtractor());
        return allHistory;
    }

    @Override
    public List<History> findAllByOrderId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_ORDER_ID, id);
        List<History> allHistory = namedJdbcTemplate.query(SQL_FIND_ALL_HISTORY_BY_ORDER_ID, params, new HistoryWithDetailExtractor());
        return allHistory;
    }

    @Override
    public List<History> findAllByComplaintId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_COMPLAINT_ID, id);
        List<History> allHistory = namedJdbcTemplate.query(SQL_FIND_ALL_HISTORY_BY_COMPLAINT_ID, params, new HistoryWithDetailExtractor());
        return allHistory;
    }

    @Override
    public List<History> findAllByProductId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_PRODUCT_ID, id);
        List<History> allHistory = namedJdbcTemplate.query(SQL_FIND_ALL_HISTORY_BY_PRODUCT_ID, params, new HistoryWithDetailExtractor());
        return allHistory;
    }

    private final class HistoryWithDetailExtractor implements ResultSetExtractor<List<History>> {

        @Override
        public List<History> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<History> allHistory = new ArrayList<>();
            while (rs.next()) {
                History history = new History();
                history.setId(rs.getLong(PARAM_HISTORY_ID));
                history.setDateChangeStatus(rs.getDate(PARAM_HISTORY_DATE_CHANGE_STATUS).toLocalDate());
                history.setDescChangeStatus(rs.getString(PARAM_HISTORY_DESC_CHANGE_STATUS));

                long statusId = rs.getLong(PARAM_HISTORY_OLD_STATUS_ID);
                if (statusId > 0) {
                    Status orderStatus = null;
                    Status complaintStatus = null;
                    Status productStatus = null;

                    Order testOrder = orderDao.findById(statusId);
                    Complaint testComplaint = complaintDao.findById(statusId);
                    Product testProduct = productDao.findById(statusId);

                    if (testOrder != null && testOrder.getStatus() != null && testOrder.getStatus().getName() != null) {
                        orderStatus = OrderStatus.valueOf(testOrder.getStatus().getName());
                    }
                    if (testComplaint != null && testComplaint.getStatus() != null && testComplaint.getStatus().getName() != null) {
                        complaintStatus = ComplaintStatus.valueOf(testComplaint.getStatus().getName());
                    }
                    if (testProduct != null && testProduct.getStatus() != null && testProduct.getStatus().getName() != null) {
                        productStatus = ProductStatus.valueOf(productDao.findById(statusId).getStatus().getName());
                    }

                    if (orderStatus != null) {
                        history.setOldStatus(orderStatus);
                    } else if (complaintStatus != null) {
                        history.setOldStatus(complaintStatus);
                    } else if (productStatus != null) {
                        history.setOldStatus(productStatus);
                    }
                }

                //Status status = statusDao.findById(statusId);
                //history.setOldStatus(status);              
                Long orderId = rs.getLong(PARAM_HISTORY_ORDER_ID);
                if (orderId > 0) {
                    Order order = orderDao.findById(orderId);
                    history.setOrder(order);
                }

                Long complaintId = rs.getLong(PARAM_HISTORY_COMPLAINT_ID);
                if (complaintId > 0) {
                    Complaint complaint = complaintDao.findById(complaintId);
                    history.setComplaint(complaint);
                }

                Long productId = rs.getLong(PARAM_HISTORY_PRODUCT_ID);
                if (productId > 0) {
                    Product product = productDao.findById(productId);
                    history.setProduct(product);
                }
                allHistory.add(history);
            }
            return allHistory;
        }
    }
//    private final class HistoryWithDetailExtractor implements ResultSetExtractor<History> {
//
//        @Override
//        public History extractData(ResultSet rs) throws SQLException, DataAccessException {
//            History history = null;
//            while (rs.next()) {
//                history = new History();
//                history.setId(rs.getLong(PARAM_HISTORY_ID));
//                history.setDateChangeStatus(rs.getDate(PARAM_HISTORY_DATE_CHANGE_STATUS).toLocalDate());
//                history.setDescChangeStatus(rs.getString(PARAM_HISTORY_DESC_CHANGE_STATUS));
//
//                long statusId = rs.getLong(PARAM_HISTORY_OLD_STATUS_ID);
//                if (statusId > 0) {
//                    Status orderStatus = null;
//                    Status complaintStatus = null;
//                    Status productStatus = null;
//                    
//                    Order testOrder = orderDao.findById(statusId);                    
//                    Complaint testComplaint = complaintDao.findById(statusId);
//                    Product testProduct = productDao.findById(statusId);
//                    
//                    if(testOrder != null && testOrder.getStatus() != null && testOrder.getStatus().getName() != null)
//                        orderStatus = OrderStatus.valueOf(testOrder.getStatus().getName());
//                    if(testComplaint != null && testComplaint.getStatus() != null && testComplaint.getStatus().getName() != null)
//                        complaintStatus = ComplaintStatus.valueOf(testComplaint.getStatus().getName());
//                    if(testProduct != null && testProduct.getStatus() != null && testProduct.getStatus().getName() != null)
//                        productStatus = ProductStatus.valueOf(productDao.findById(statusId).getStatus().getName());
//
//                    if (orderStatus != null) {
//                        history.setOldStatus(orderStatus);
//                    } else if (complaintStatus != null) {
//                        history.setOldStatus(complaintStatus);
//                    } else if (productStatus != null) {
//                        history.setOldStatus(productStatus);
//                    }
//                }
//
//                //Status status = statusDao.findById(statusId);
//                //history.setOldStatus(status);              
//                Long orderId = rs.getLong(PARAM_HISTORY_ORDER_ID);
//                if (orderId > 0) {
//                    Order order = orderDao.findById(orderId);
//                    history.setOrder(order);
//                }
//
//                Long complaintId = rs.getLong(PARAM_HISTORY_COMPLAINT_ID);
//                if (complaintId > 0) {
//                    Complaint complaint = complaintDao.findById(complaintId);
//                    history.setComplaint(complaint);
//                }
//
//                Long productId = rs.getLong(PARAM_HISTORY_PRODUCT_ID);
//                if (productId > 0) {
//                    Product product = productDao.findById(productId);
//                    history.setProduct(product);
//                }
//
//            }
//            return history;
//        }
//    }
}
