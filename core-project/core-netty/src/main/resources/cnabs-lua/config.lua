local _CONF = {} 

local redis={
	host = '127.0.0.1',	
	port = 7500,
	db = 12,
	password='ABSLink2017',
} 

local ipblacklist={	 
    cache_key='cnabs_ipblacklist', 
	cache_init='cnabs_ipblacklist_isinit',
	cache_init_timeout = 3600	
}
 
local fakeuserlist={
	cache_key='cnabs_fakeuserlist',
	cache_init='cnabs_fakeuserlist_init',
	cache_init_timeout = 36000
}
 
_CONF.redis = redis
_CONF.ipblacklist = ipblacklist
_CONF.fakeuserlist = fakeuserlist

return _CONF