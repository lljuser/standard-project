local _M={
	configMo=  require("cnabs.config"),
	redisMo = require "cnabs.redis",
	ipCache = ngx.shared.lua_cache_ipblacklist,
	luaCache= ngx.shared.lua_cache,
	fakeuserCache = ngx.shared.lua_cache_fakeuserlist
}

 

function _M:ip_getclientip()
	local clientIP = ngx.req.get_headers()["X-Real-IP"]
	if clientIP == nil then
		clientIP = ngx.req.get_headers()["x_forwarded_for"]
	end
	if clientIP == nil then
		clientIP = ngx.var.remote_addr
	end	 
	
	return clientIP
end
	
function _M:ip_checkblacklist(clientIP) 
	----check cache
	self:ip_cacheblacklist()	
	
	if self.ipCache:get(clientIP) then 		 
		return true,"ip match: true"
	else
		return false,"ip match:false"
	end
end


function _M:ip_cacheblacklist()
	--get ip list from mem 
	--cache is init ,if not ,load data from redis
	local _isInit = self.luaCache:get(self.configMo.ipblacklist.cache_init)	 
	if not _isInit then 
	    -- create redis
	    local _redis,err = self.redisMo:new(self.configMo.redis) 
		 
		---- get redis ipblacklist 	
		local blackiplist,err = _redis:smembers(self.configMo.ipblacklist.cache_key) 
      
		if blackiplist then 
			--clear cache
			self.ipCache:flush_all()
			for index,sip in pairs(blackiplist) do				 
				self.ipCache:set(sip,true)
			end 			 
		end  
		
		--set cache init status:true
		self.luaCache:set(self.configMo.ipblacklist.cache_init,true,self.configMo.ipblacklist.cache_init_timeout)
	end  
end

function _M:ip_refreshblacklist()  
	self.ipCache:flush_all()
	self.luaCache:set(self.configMo.ipblacklist.cache_init,false)	

	---init cache
	self:ip_cacheblacklist()
    return true,"ok"
end

 

function _M:ip_getblacklist() 	 
    return self.ipCache:get_keys()
end

function _M:fakeuser_checklist(user)  
	----check cache 
	self:fakeuser_cachelist()	 
	
	if self.fakeuserCache:get(user) then 		 
		return true,"user match: true"
	else
		return false,"user match:false"
	end
end


function _M:fakeuser_cachelist()
	--get ip list from mem 
	--cache is init ,if not ,load data from redis
	local _isInit = self.luaCache:get(self.configMo.fakeuserlist.cache_init) 
	if not _isInit then 
	    -- create redis
	    local _redis,err = self.redisMo:new(self.configMo.redis)  
		---- get list 	
		local list,err = _redis:smembers(self.configMo.fakeuserlist.cache_key)  
		if list then 
			--clear cache
			self.fakeuserCache:flush_all()
			for index,sip in pairs(list) do				 
				self.fakeuserCache:set(sip,true) 
			end 			 
		end   
	end  
	
	--set cache init status:true
	self.luaCache:set(self.configMo.fakeuserlist.cache_init,true,self.configMo.fakeuserlist.cache_init_timeout)
end

function _M:fakeuser_refreshlist()  
	self.fakeuserCache:flush_all()
	self.luaCache:set(self.configMo.fakeuserlist.cache_init,false)	

	---init cache
	self:fakeuser_cachelist()
    return true,"ok"
end
 

function _M:fakeuser_getlist() 	 
    return self.fakeuserCache:get_keys()
end


function _M:fakeuser_addlist(users)  
	local _redis,err = self.redisMo:new(self.configMo.redis)
	for k, v in pairs(users) do
		local ok,err = _redis:sadd(self.configMo.fakeuserlist.cache_key,v)    
	end	
	 
	--refresh list
	self:fakeuser_refreshlist()
    return true,"ok"
end


function _M.split(input, delimiter)
    input = tostring(input)
    delimiter = tostring(delimiter)
    if (delimiter=='') then return false end
    local pos,arr = 0, {}
    -- for each divider found
    for st,sp in function() return string.find(input, delimiter, pos, true) end do
        table.insert(arr, string.sub(input, pos, st - 1))
        pos = sp + 1
    end
    table.insert(arr, string.sub(input, pos))
    return arr
end

return _M