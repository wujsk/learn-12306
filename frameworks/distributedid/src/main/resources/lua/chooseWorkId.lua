local hashKey = "snowflake_work_id_key"
local workIdKey = "workId"
local dataCenterIdKey = "dataCenterId"

if (redis.call('exists', hashKey) == 0) then
    redis.call('hincrby', hashKey, workIdKey, 0)
    redis.call('hincrby', hashKey, dataCenterIdKey, 0)
    return {0, 0}
end

local workId = tonumber(redis.call('hget', hashKey, workIdKey))
local dataCenterId = tonumber(redis.call('hget', hashKey, dataCenterIdKey))

local max = 31;
local resultWorkId = 0
local resultDataCenterId = 0

if (workId == max and dataCenterId == max) then
    redis.call('hset', hashKey, workIdKey, 0)
    redis.call('hset', hashKey, dataCenterIdKey, 0)
elseif workId ~= max then
    resultWorkId = redis.call('hincrby', hashKey, workIdKey, 1)
    resultDataCenterId = dataCenterId
elseif dataCenterIdKey ~= max then
    resultWorkId = 0
    resultDataCenterId = redis.call('hincrby', hashKey, dataCenterIdKey, 1)
    redis.call('hset', hashKey, workIdKey, 0)
end

return { resultWorkId, resultDataCenterId }
    