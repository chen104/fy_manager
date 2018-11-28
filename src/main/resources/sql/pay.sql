 #入库数量 减去应付数量 #检查通过数量 减去 应付数量
#sql("rollbackUpdate")
	update fy_business_order o
	INNER JOIN fy_check_collect fc on o.id = fc.order_id
	INNER JOIN fy_business_pay p on p.order_id = o.id
	set o.has_in_quantity = o.has_in_quantity - p.pay_quantity,
	fc.pass_quantity = fc.pass_quantity - p.pay_quantity 
	where p.id in %s
#end