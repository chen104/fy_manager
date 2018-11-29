 #入库数量 减去应付数量 #检查通过数量 减去 应付数量
#sql("rollbackWaitCheck")
	update fy_business_order o
	INNER JOIN fy_check_collect fc on o.id = fc.order_id
	INNER JOIN fy_check_collect  fc1 on fc1.order_id = o.id
	set o.has_in_quantity = o.has_in_quantity - fc1.inhouse_quantity,
	fc.inhouse_quantity = 0
	where fc.id  in %s
#end