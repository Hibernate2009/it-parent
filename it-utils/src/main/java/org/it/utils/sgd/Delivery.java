package org.it.utils.sgd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Logger;

import org.it.utils.exception.ExceptionUtil;
import org.it.utils.jms.JmsSender;
import org.it.utils.properties.PropertiesService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class Delivery {
	private static Logger log = Logger.getLogger(Delivery.class.getName());
	static boolean isRunning = false;
	private JdbcTemplate jdbcTemplate;
	Timer timer;
	private PropertiesService propertiesService;
	private JmsSender jmsSender;

	private String select_postgres;
	private String tableName;
	private String type;

	private Delivery(PropertiesService propertiesService, JdbcTemplate jdbcTemplate, JmsSender jmsSender, String tableName, String type) {
		this.tableName = tableName;
		this.type = type;
		this.select_postgres = "select id, queue from " + this.tableName + " where status!=? and status!=? and date_event<=? and type=? limit ?";

		this.propertiesService = propertiesService;
		this.jdbcTemplate = jdbcTemplate;
		this.jmsSender = jmsSender;

		timer = new Timer();
		timer.schedule(new Hm(), 1000, 1000);
	}

	public void stop() {
		timer.cancel();
	}

	private List<String[]> getItems() {
		// TODO Auto-generated method stub
		List<String[]> query = null;
		long startTime = System.currentTimeMillis();

		try {
			String sql = "";
			Object[] args = null;

			sql = select_postgres;
			args = new Object[] { "SEND", "FINAL", new Date(), type, Integer.parseInt(propertiesService.get("NumberOfPackages")) };

			query = jdbcTemplate.query(sql, args, new RowMapper<String[]>() {
				@Override
				public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
					String data[] = new String[2];
					data[0] = rs.getString("id");
					data[1] = rs.getString("queue");
					return data;
				}
			});
		} catch (Exception e) {
			log.info("Delivery getItems exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
		} finally {
			//log.info(Delivery.class.getName() + ".getItems time is:" + (System.currentTimeMillis() - startTime));
		}

		return query;
	}

	public void save(String context, String queue, UUID id) {
		try {
			jdbcTemplate.update("insert into " + tableName + " (id,context,queue,status,date_event,type) values (?,?,?,?,?,?)", new Object[] { id.toString(), context, queue, "NEW", new Date(), type });
		} catch (Exception e) {
			log.info("Delivery save exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		try {
			jdbcTemplate.update("delete from " + tableName + " where id=?", new Object[] { id });
		} catch (Exception e) {
			log.info("Delivery remove exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

	public String get(String id) {
		String res = null;
		try {
			List<String> query = jdbcTemplate.query("select context from " + tableName + " where id=?", new Object[] { id }, new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					// TODO Auto-generated method stub
					return rs.getString("context");
				}
			});
			if (!query.isEmpty()) {
				res = query.get(0);
			}
		} catch (Exception e) {
			log.info("Delivery get exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
		}

		return res;
	}

	public void update(String id, String context, Date date, String status) {
		try {
			jdbcTemplate.update("UPDATE " + tableName + " SET context=?, date_event=?, status=? WHERE id=?", new Object[] { context, date, status, id });
		} catch (Exception e) {
			log.info("Delivery update exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

	public void update(String id, Date date, String status) {
		// TODO Auto-generated method stub
		try {
			jdbcTemplate.update("UPDATE " + tableName + " SET date_event=?, status=? WHERE id=?", new Object[] { date, status, id });
		} catch (Exception e) {
			log.info("Delivery update exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
		}

	}

	public void updateStatus(String id, String status) {
		try {
			if ("FINAL".equals(status)) {
				remove(id);
			} else {
				jdbcTemplate.update("UPDATE " + tableName + " SET status=? WHERE id=?", new Object[] { status, id });
			}

		} catch (Exception e) {
			log.info("Delivery updateStatus exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

	final class Hm extends TimerTask {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (isRunning == false) {
				isRunning = true;
				try {
					List<String[]> itemList = getItems();
					if (itemList != null) {
						for (String[] items : itemList) {
							String queue = items[1];
							String id = items[0];
							updateStatus(id, "SEND");
							jmsSender.send(queue, id);
						}
					}
				} catch (Exception e) {
					log.info("TimerTask run exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
				} finally {
					isRunning = false;
				}
			} else {
				log.info("Wait end TimerTask");
			}
		}
	}

}
