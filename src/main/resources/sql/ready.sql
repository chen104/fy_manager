### 用于 order 

###查找单个备货
#sql("select")
	select r.* , cate.`name` category_name,cu.name customer_name ,person.`name` planer_name,su.`name` supplier_name ,unit.`name` unit_name 
		from fy_business_ready  r 
		LEFT JOIN fy_base_customer cu on r.customer = cu.id  
		LEFT JOIN fy_base_category cate on cate.id= r.category_id  
		LEFT JOIN fy_base_person person on person.id=r.planer_id  
		left join fy_base_supplier su on su.id= r.supplier_id  
		LEFT JOIN fy_base_unit unit on unit.id = r.unit
		where r.id= ?
#end

###分页查找备货采购


