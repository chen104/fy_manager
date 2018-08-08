package com.chen.fy.service;

import com.chen.fy.model.Person;
import com.jfinal.plugin.activerecord.Page;

public class PersonService {
	public static final PersonService me = new PersonService();
	public static final Person dao = new Person().dao();

	public Person findById(int id) {

		return dao.findById(id);

	}

	public Page<Person> paginate(int pageNum) {

		return dao.paginate(pageNum, 10,
				"select p.* ,d.part_name department,CURDATE() CURDATE, DATEDIFF(CURDATE(),entry_date) dateNumber",
				"from fy_base_person p left join fy_base_department d on p.department_id = d.id order by p.id desc");
	}

	public Page<Person> paginate(int pageNum, String keyWord) {

		return dao.paginate(pageNum, 10,
				"select p.* ,d.part_name department,CURDATE() CURDATE, DATEDIFF(CURDATE(),entry_date) dateNumber",
				"from fy_base_person p left join fy_base_department d on p.department_id = d.id where p.name  like ?  order by p.id desc",
				"%" + keyWord + "%");
	}

	public Page<Person> paginate(int pageNum, int pageSize) {

		return dao.paginate(pageNum, pageSize, "select *", "from fy_base_person order by id desc");
	}

	public Page<Person> paginate(int pageNum, String keyWord, int pageSize) {

		return dao.paginate(pageNum, pageSize, "select *", "from fy_base_person  where name  like ?  order by id desc",
				"%" + keyWord + "%");
	}

}
