### 用于 order 
#sql("getUndistribut")
	select actionKey 'uri' from account a
	 INNER JOIN account_role ar on a.id=ar.accountId
	INNER JOIN role r on ar.roleId = r.id
	inner join role_permission  rp on r.id = rp.roleId
	INNER JOIN permission p on p.id = rp.permissionId
	where a.id= ?
#end