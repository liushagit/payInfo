/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.orange.platform.bill.common.domain.LinkInfo;
import com.payinfo.net.database.ConnectionResource;
import com.payinfo.net.database.IJuiceDBHandler;

/**
 * 辅助DAO
 * 
 * @author weimiplayer.com
 *
 * 2012年12月20日
 */
public class CommonHelperDAO extends ConnectionResource {
	private static IJuiceDBHandler<LinkInfo> HANDLER_LINK = new IJuiceDBHandler<LinkInfo>() {
		@Override
		public LinkInfo handler(ResultSet rs) throws SQLException {
			LinkInfo info = new LinkInfo();
			info.setLinkId(rs.getString("link_id"));
			info.setExt(rs.getString("ext"));
			info.setSp(rs.getString("sp"));
			info.setImsi(rs.getString("imsi"));
			info.setIp(rs.getString("ip"));
			info.setFee(rs.getInt("fee"));
			return info;
		}
	};
	
	
	public void addLinkInfo(LinkInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_link_info(link_id, ext, sp, imsi, ip, fee, create_time, update_time) ")
			.append(" values(?,?,?,?,?,?,now(), now())");
		saveOrUpdate(sql.toString(), info.getLinkId(), info.getExt(), info.getSp(), info.getImsi(),
				info.getIp(), info.getFee());
	}
	
	public LinkInfo queryLinkInfo(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_link_info where link_id=? order by id desc limit 1");
		return queryForObject(sql.toString(), HANDLER_LINK, linkId);
	}
}
