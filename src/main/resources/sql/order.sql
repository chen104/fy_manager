### 用于 order 
#sql("getUndistributAcount")
	
#end

#sql("updateWWhang")
	update fy_business_order o INNER JOIN 
	(select sum(IFNULL(discount_account,0)) discount_account,order_id from fy_business_purchase  where order_id = ? GROUP BY order_id) p 
	on o.id= p.order_id
	set o.ww_unhang_amount = p.discount_account

#end

#sql("updateWWhangAmount")
	update  fy_business_order o INNER JOIN
	( SELECT sum(should_pay) should_pay,order_id FROM fy_business_pay WHERE order_id  in %s GROUP BY order_id  ) p
	on o.id= p.order_id
	INNER JOIN
	(select sum(discount_account) discount_account,order_id from fy_business_purchase where order_id in %s GROUP BY order_id) c
	on o.id= c.order_id
	set o.ww_hang_amount = p.should_pay,o.ww_unhang_amount = c.discount_account - p.should_pay
	where o.order_id in %s
#end


#sql("updateWWhangQuantity")
update  fy_business_order o INNER JOIN
(SELECT  sum(real_in_quantity) real_in_quantity,order_id from fy_business_in_warehouse   where order_id  in %s and is_create_pay = 1  order by order_id) i
 on o.id= i.order_id
set o.ww_quantity = real_in_quantity ,o.ww_unquantity =o.quantity - real_in_quantity
#end

