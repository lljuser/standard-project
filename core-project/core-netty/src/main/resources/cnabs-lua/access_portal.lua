local _common=  require("cnabs.common")

----upgrade system
--local htmlBody="<html><body>"..
--    "<div style='text-align:center;margin-top:200px;'>"..
--	"<h2>CNABS system upgrading...</h2>"..
--	"<h4>will open at 2017.12.26 12:00</h4>"..	 
--	"</div></body></html>"
--ngx.header.content_type="text/html;charset=utf-8"
--ngx.print(htmlBody)
--ngx.exit(ngx.HTTP_OK)

--ngx.redirect("/helpinfo/upgrade.html")	 
--ngx.exit(ngx.HTTP_OK)


----ip black list 
local clientIP = _common.ip_getclientip()
local ok,err = _common:ip_checkblacklist(clientIP) 
if ok then	 
	ngx.redirect("/helpinfo/ipblacklist.html")	 
end
	 


