ALTER TABLE fy_business_order  ADD  COLUMN create_time datetime;
ALTER TABLE fy_business_order  ADD  COLUMN create_by int(11);
ALTER TABLE fy_business_order  ADD  COLUMN update_time datetime;
ALTER TABLE fy_business_order  ADD  COLUMN update_by int(11);


ALTER TABLE fy_business_purchase  ADD  COLUMN create_time datetime;
ALTER TABLE fy_business_purchase  ADD  COLUMN create_by int(11);
ALTER TABLE fy_business_purchase  ADD  COLUMN update_time datetime;
ALTER TABLE fy_business_purchase  ADD  COLUMN update_by  int(11);

ALTER TABLE fy_business_in_warehouse  ADD  COLUMN create_time datetime;
ALTER TABLE fy_business_in_warehouse  ADD  COLUMN create_by int(11);
ALTER TABLE fy_business_in_warehouse  ADD  COLUMN update_time datetime; 
ALTER TABLE fy_business_in_warehouse  ADD  COLUMN update_by int(11);


ALTER TABLE fy_check_collect  ADD  COLUMN create_time datetime;
ALTER TABLE fy_check_collect  ADD  COLUMN create_by int(11);
ALTER TABLE fy_check_collect  ADD  COLUMN update_time  datetime;
ALTER TABLE fy_check_collect  ADD  COLUMN update_by  int(11);

ALTER TABLE fy_exception_record  ADD  COLUMN create_time datetime;
ALTER TABLE fy_exception_record  ADD  COLUMN create_by int(11);
ALTER TABLE fy_exception_record  ADD  COLUMN update_time datetime;
ALTER TABLE fy_exception_record  ADD  COLUMN update_by   int(11);