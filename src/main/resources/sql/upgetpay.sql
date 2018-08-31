### 用于 upgetpay
#sql("updateorder")
update fy_business_order o INNER JOIN   
(SELECT  sum(hang_quantity) hquantity , sum(hang_amount) hang_amount, delivery_no delivery_no
  FROM fy_upload_getpay  where delivery_no in  %s  GROUP BY  delivery_no  ) upgetpay
on o.delivery_no = upgetpay.delivery_no
set o.hang_quantity = hquantity,
hang_account=upgetpay.hang_amount,
o.unhang_quantity = o.quantity - hquantity
#end

#sql("updateHangStatus")
	update fy_business_order o INNER JOIN
	(select max(hang_date) hang_date,invoice_stat ,delivery_no
	from  fy_upload_getpay group by delivery_no ) u on u.delivery_no = o.delivery_no
	set o.hang_status = u.invoice_stat ,o.hang_time=u.hang_date
	where o.hang_status <>  u.invoice_stat ;
#end