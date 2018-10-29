local _common=  require("cnabs.common")
 
----ipblacklist stop test 				
if ngx.re.match(ngx.var.uri,"/openresty/ipblacklist/stop") then	 
	local clientIP = _common.ip_getclientip()
	local ok,err = _common:ip_checkblacklist(clientIP) 
	if ok then
		local htmlBody="<html><body>"..
			"<div style='text-align:center;margin-top:200px;'>"..
			"<h2>CNABS 官网通知[测试接口] </h2>"..
			"<h4>您的IP地址请求异常, 系统已限制您的访问</h4>"..	
			"<h5>如有疑问, 请你联系管理人员 feedback@cn-abs.com</h5>"..	
			"</div></body></html>" 
	
		ngx.header.content_type="text/html;charset=utf-8"	 
		ngx.print(htmlBody)
		ngx.exit(ngx.HTTP_OK)
	else
		ngx.say("allow this ip access")
		ngx.exit(ngx.HTTP_OK)
	end
	
end
 