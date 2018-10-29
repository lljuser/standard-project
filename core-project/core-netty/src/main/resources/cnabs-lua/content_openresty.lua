local _common=  require("cnabs.common")
local _date = os.date("%Y%m%d%H%M")

----ipblack refresh
if ngx.var.uri == "/openresty/ipblacklist/refresh/".._date then	
	local ok,err = _common:ip_refreshblacklist()
	if ok then
		ngx.say("refresh ip black list ok")
		ngx.exit(ngx.HTTP_OK)
	end
end

----ipblack getlist
if ngx.var.uri == "/openresty/ipblacklist/getlist/".._date then	
	local ipblacklist,err = _common:ip_getblacklist()	
	if  not err then 
		ngx.say("ip black list:")
		for index,sip in pairs(ipblacklist) do
			ngx.say(sip) 
		end  
	else
		ngx.say("get ip black list error:",err)
	end
	ngx.exit(ngx.HTTP_OK) 
end


---time test
if ngx.var.uri == "/openresty/time" then
    ngx.say("time:",ngx.time())
    ngx.say("now:",ngx.now())
	ngx.say("today:",ngx.today())  
    ngx.say("localtime:",ngx.localtime())
    ngx.say("utctime:",ngx.utctime()) 
    ngx.say("date format %Y%m%d%H%M:",os.date("%Y%m%d%H%M")) 
	ngx.exit(ngx.HTTP_OK) 
end 
 
---cjson test
if ngx.var.uri == "/openresty/cjson/encode" then
    local cjson = require "cjson" 
	----ngx.unescape_uri|ngx.escape_uri china word convert 
	local data ={
		name= ngx.unescape_uri(ngx.var.arg_name) or "llj",
		age= ngx.var.arg_age or 30,
		address= ngx.unescape_uri(ngx.var.arg_address) or "shanghai china"
	}
	
	ngx.header.content_type="application/json;charset=utf-8"
	local str = cjson.encode(data)
	ngx.say(str)
	ngx.exit(ngx.HTTP_OK) 
end 

if ngx.var.uri == "/openresty/cjson/decode" then
	local cjson = require "cjson"
	-----setting custom args [query string]
    --local res = ngx.location.capture("/openresty/cjson/encode",{
	--	args = {name="llj1",age=40,address="query string set table field"}
	--})
	
	----setting parent request args to subrequest
	local res = ngx.location.capture("/openresty/cjson/encode",	{ 
		args = ngx.var.args 
	})
	-----[share_all_vars =true] or [copy_all_vars =true] is setting Nginx variables to subrequest 
	
	
	ngx.say(res.status)
	ngx.say("get data from api[string]:",res.body)  
	
	local data=cjson.decode(res.body)
	ngx.say("json->table.name:",data.name)
	ngx.say("json->table.age:",data.age)
	ngx.say("json->table.address:",data.address)
	ngx.exit(ngx.HTTP_OK) 
end 

----arry test
if ngx.var.uri == "/openresty/array" then
	ngx.say("Array Test")
    local host = { {"127.0.0.1",5601}, {"127.0.0.1",9200} } 
	local leng=0
    for k, v in pairs(host) do
		ngx.say("host:",v[1]," port:",v[2])
		leng=leng+1
	end	
	ngx.say("len1:",leng," len2:",table.maxn(host))
	ngx.exit(ngx.HTTP_OK) 
end 
 
----fakeuser refresh
if ngx.var.uri == "/openresty/fakeuserlist/refresh/".._date then	
	local ok,err = _common:fakeuser_refreshlist()
	if ok then
		ngx.say("refresh fake user list ok")
		ngx.exit(ngx.HTTP_OK)
	end
end

----fakeuser getlist
if ngx.var.uri == "/openresty/fakeuserlist/getlist/".._date then	
	local list,err = _common:fakeuser_getlist()	
	if  not err then 
		ngx.say("fake user list:")
		for index,sip in pairs(list) do
			ngx.say(sip) 
		end  
	else
		ngx.say("get fake user list error:",err)
	end
	ngx.exit(ngx.HTTP_OK) 
end
 
----fakeuser addlist 
if ngx.var.uri == "/openresty/fakeuserlist/add/".._date then	
	ngx.say("--------------") 
	ngx.req.read_body()
	local data = ngx.req.get_body_data()
	ngx.say(data)
	ngx.say("----12222111111----------") 
	if data then
		local users=_common.split(data,",")
		local ok,err = _common:fakeuser_addlist(users) 
		ngx.say('add success')
	end  
	
	ngx.exit(ngx.HTTP_OK) 
end
 
--not match uri 
ngx.redirect("/helpinfo/404.html")
 