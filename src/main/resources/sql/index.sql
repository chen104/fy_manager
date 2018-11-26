#获得订单预警数量
#sql("getOrderWarnCount")
	select count(1) from fy_business_order o  
	LEFT JOIN  out_view ov on ov.order_id = o.id 
	WHERE DATEDIFF(delivery_date , NOW()) < 3 
	AND   DATEDIFF(delivery_date , NOW()) > 0 
	AND o.quantity <> IFNULL(v_out_quantity,0)
#end


#获得订单拖期数量
#sql("getOrderDelayCount")
	select count(1) from fy_business_order o  
	LEFT JOIN  out_view ov on ov.order_id = o.id 
	WHERE  DATEDIFF(delivery_date , NOW()) < 0 
	AND o.quantity <> IFNULL(v_out_quantity,0)
#end



#获得委外预警数量
#sql("getCommisionWarnCount")
	select count(1) from fy_business_order o  
		Inner join fy_business_purchase p on o.id = p.order_id
		LEFT JOIN fy_check_collect fc on o.id = fc.order_id
		where o.quantity <> IFNULL(pass_quantity,0)
		AND DATEDIFF(purchase_delivery_date , NOW()) < 3 
		AND   DATEDIFF(purchase_delivery_date , NOW()) > 0 
#end


#获得委外拖期数量
#sql("getCommisionDelayCount")
		select count(1) from fy_business_order o  
		Inner join fy_business_purchase p on o.id = p.order_id
		LEFT JOIN fy_check_collect fc on o.id = fc.order_id
		where o.quantity <> IFNULL(pass_quantity,0) 
		AND DATEDIFF(purchase_delivery_date , NOW()) < 0
		 
#end



#获得自产预警数量
#sql("getProductWarnCount")
	select count(1) from fy_business_order o  
	LEFT  JOIN fy_check_collect fc on o.id = fc.order_id
	where   o.dis_to = 0 AND o.order_status=12 
	AND o.quantity <> IFNULL(pass_quantity,0)
	AND DATEDIFF(o.plan_finsh_time , NOW()) < 3 
		AND   DATEDIFF(o.plan_finsh_time , NOW()) > 0 
#end


#获得自产拖期数量
#sql("getProductDelayCount")
		select count(1) from fy_business_order o  
		LEFT JOIN fy_check_collect fc on o.id = fc.order_id
		where dis_to = 0 AND order_status=12
		AND o.quantity <> IFNULL(pass_quantity,0)
		AND DATEDIFF(plan_finsh_time , NOW()) < 0
		
		 
#end
