#撤回库存，针对自产
#sql("rollbackProductStorage")
	update fy_business_order o 
	INNER JOIN fy_check_collect fc on o.id = fc.order_id
	INNER JOIN fy_business_order o1 on o.id =o1.id
	set 
	o.storage_quantity = 0,#库存清零
	o.has_in_quantity = o.has_in_quantity - o1.storage_quantity,#已入库
	fc.pass_quantity = fc.pass_quantity - o1.storage_quantity, #已检测合格的 减去撤回库存
	fc.unpass_quantity = 0,
	fc.check_result =null
	where  o.id IN  %s

#end


