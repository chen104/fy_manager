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


###查询订单年度合计
#sql("findTotal")
	select SUM(amount) amount,EXTRACT(YEAR_MONTH FROM order_date) order_date from   fy_business_order 
	 where EXTRACT(YEAR FROM order_date) = ?
	GROUP BY EXTRACT(YEAR_MONTH FROM order_date)
#end

###更新挂账信息 参数是送货单号，in的方式,需要使用格式化 is_setlled = 1 AND
#sql("updateGetpayInfo")
	update fy_business_order o INNER  JOIN (
	select sum(hang_amount) hang_amount ,max(hang_date) hang_date ,delivery_no ,sum(quantity) quantity
	from fy_upload_getpay where   
	 delivery_no IN %s GROUP BY delivery_no
	) gp on o.delivery_no = gp.delivery_no 
	set o.hang_time = gp.hang_date ,###挂账时间
	o.hang_quantity = gp.quantity ,###挂账数量
	o.unhang_quantity = o.quantity - gp.quantity, ###未挂账数量
	o.hang_account = gp.hang_amount###挂账金额
	where o.delivery_no in %s
#end

###更新挂账信息 参数payId，in的方式 is_setlled = 1 AND
###挂账时间
###挂账数量
 ###未挂账数量
###挂账金额
#sql("updatepayInfo")
	update fy_business_order o INNER  JOIN 
	(SELECT sum(should_pay) should_pay,sum(pay_quantity) pay_quantity,MAX(hang_date) hang_date,order_id
	from  fy_business_pay  where  order_id in %s group by order_id) p
	on o.id = p.order_id
	set o.hang_time = p.hang_date ,
		o.hang_quantity = p.pay_quantity ,
		o.unhang_quantity = o.quantity - p.pay_quantity ,
		o.hang_account = p.should_pay
	where o.id in %s
#end


###更新挂账信息 参数是订单Id，in的方式
#出库数量
 #出库时间
#sql("updateOutInfoQuantity")
	 update fy_business_order o INNER JOIN
	(select sum(out_quantity) out_quantity ,sum(out_time) out_time,order_id from fy_business_out_warehouse
	where order_id in %s GROUP BY order_id) oh
	on o.id = oh.order_id
	set o.out_quantity = oh.out_quantity, 
	o.out_house_date = oh.out_time
#end

###更新挂账信息 参数是 ，in的方式,字符串格式化传递参数。String.format
#sql("updateOutInfoTransport_no")
	update fy_business_order o INNER JOIN
	(select transport_company,order_id,transport_no from  fy_business_out_warehouse where
	id in (
		select max(id) from fy_business_out_warehouse where order_id in %s GROUP BY order_id
	)) oh 
	on o.id= oh.order_id 
	set o.transport_no = oh.transport_no,
	o.transport_company= oh.transport_company
	where o.id in %s
#end

###更新撤回出库时更新库存 参数是 ，出库in的方式,字符串格式化传递参数。String.format
#sql("updateOutStorage")
update fy_business_order o INNER JOIN
	(select sum(out_quantity) out_quantity ,  order_id from fy_business_out_warehouse
	where id in %s GROUP BY order_id) oh
	on o.id = oh.order_id
	set o.storage_quantity = o.storage_quantity + oh.out_quantity
#end


###更新撤回出库时没有出库单 参数订单in的方式,字符串格式化传递参数。String.format
#sql("updateOrderOutIsNull")
update fy_business_order SET 
out_quantity = 0,
transport_company  = null,
transport_no   = null,
out_house_date  = null
where id in  %s
#end

####更新入库信息
#sql("updateOrderInhouseQuantity")
	update fy_business_order o LEFT JOIN in_house ih on o.id =ih.order_id 
	set o.out_quantity  = IFNULL( ih.pass_quantity,0),
	 o.inhouse_date= ih.in_time
	where o.id in %s
#end


