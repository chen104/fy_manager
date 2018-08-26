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