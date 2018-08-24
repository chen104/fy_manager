### 用于 PermissionDirective 判断 account 是否拥有指定的 permission
#sql("hasPermission")
	select ar.accountId from (
		select rp.roleId from role_permission rp
		inner join permission p on rp.permissionId = p.id
		where p.actionKey = #para(actionKey)
	)
	as t inner join account_role ar on t.roleId = ar.roleId
	where ar.accountId = #para(accountId)
#end

#sql("getPermission")
	select p.key 'key' from account a
	 INNER JOIN account_role ar on a.id=ar.accountId
	INNER JOIN role r on ar.roleId = r.id
	inner join role_permission  rp on r.id = rp.roleId
	INNER JOIN permission p on p.id = rp.permissionId
	where a.id= ?
#end

#sql("getUrlPermission")
	select actionKey 'uri' from account a
	 INNER JOIN account_role ar on a.id=ar.accountId
	INNER JOIN role r on ar.roleId = r.id
	inner join role_permission  rp on r.id = rp.roleId
	INNER JOIN permission p on p.id = rp.permissionId
	where a.id= ?
#end


#sql("listRoleColPermission")
		select *   from  col_permision cp  
		LEFT JOIN 
		(SELECT *,'checked'  from role_col  where roleId= ?  ) rc 
		on cp.id = rc.column_id
		order by ctable,id ASC
#end


#sql("listColPermission")
	select ckey,ctable from  col_permision cp inner JOIN
	( SELECT DISTINCT column_id   from role_col rc 
		  INNER JOIN account_role  ar 
		  where rc.roleId = ar.roleId and ar.accountId = ?
	) ap on ap.column_id = cp.id
	order by ctable,id ASC
#end

