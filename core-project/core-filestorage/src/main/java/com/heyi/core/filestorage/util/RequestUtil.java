package com.heyi.core.filestorage.util;

import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @ClassName RequestUtil
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/8/27$ 3:45 PM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/27$ 3:45 PM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
public class RequestUtil {

    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader( "X-Forwarded-For" );
        if (ObjectUtils.isEmpty( ip ) || "unknown".equalsIgnoreCase( ip )) {
            ip = request.getHeader( "Proxy-Client-IP" );
        }
        if (ObjectUtils.isEmpty( ip ) || "unknown".equalsIgnoreCase( ip )) {
            ip = request.getHeader( "WL-Proxy-Client-IP" );
        }
        if (ObjectUtils.isEmpty( ip ) || "unknown".equalsIgnoreCase( ip )) {
            ip = request.getHeader( "HTTP_CLIENT_IP" );
        }
        if (ObjectUtils.isEmpty( ip ) || "unknown".equalsIgnoreCase( ip )) {
            ip = request.getHeader( "HTTP_X_FORWARDED_FOR" );
        }
        if (ObjectUtils.isEmpty( ip ) || "unknown".equalsIgnoreCase( ip )) {
            ip = request.getRemoteAddr();
            if (ip == null || ip.equals( "0:0:0:0:0:0:0:1" )) {
                // todo 本地部署直接返回localhost
                //ip = getHostIp();
                ip = "127.0.0.1";
            }
        }
        if (!ObjectUtils.isEmpty( ip )) {
            ip = ip + ":" + request.getServerPort();
        }
        return ip;
    }


    public static String getHostIp() {
        String sIP = "";
        InetAddress ip = null;
        try {
            boolean bFindIP = false;
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                if (bFindIP)
                    break;
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().matches( "(\\d{1,3}\\.){3}\\d{1,3}" )) {
                        bFindIP = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != ip)
            sIP = ip.getHostAddress();
        return sIP;

    }
}

