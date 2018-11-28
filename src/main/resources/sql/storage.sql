#撤回库存，针对自产
#sql("rollbackProductStorage")
	update fy_business_order o 
	INNER JOIN fy_check_collect fc on o.id = fc.id
	set fc.pass_quantity = fc.pass_quantity - o.storage_quantity,
	fc.unpass_quantity = fc.unpass_quantity +   o.storage_quantity
	where dis_to = 0  AND o.id IN ?

#end


